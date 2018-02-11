package com.yijia360.pay.admin.config.redis;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.alibaba.fastjson.parser.ParserConfig;
import com.yijia360.pay.admin.config.redis.properties.BizRedisProperties;
import com.yijia360.pay.admin.config.redis.topic.TopicBaiicStatisticalReceiver;
import com.yijia360.pay.admin.config.redis.topic.TopicCacheRefreshReceiver;
import com.yijia360.pay.entity.cache.CacheKey;

/**
 * Created by wangzhen on 2017/3/29.
 */
@Configuration
@EnableConfigurationProperties({BizRedisProperties.class})
public class RedisConfig{

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	BizRedisProperties prop;
	
	
	@Bean
	CountDownLatch latch(){
		return new CountDownLatch(1);
	}
	
	
	@Bean
	TopicBaiicStatisticalReceiver basicDataReceiver(CountDownLatch latch){
		return new TopicBaiicStatisticalReceiver(latch);
	}
	
	@Bean
	MessageListenerAdapter basicDataListenerAdapter(TopicBaiicStatisticalReceiver topicBaiicStatisticalReceiver){
		return new MessageListenerAdapter(topicBaiicStatisticalReceiver,"receiveBasicData");
	}
	
	@Bean
	TopicCacheRefreshReceiver topicCacheReloadReceiver(CountDownLatch latch){
		return new TopicCacheRefreshReceiver(latch);
	}
	
	@Bean
	MessageListenerAdapter messageListenerAdapter(TopicCacheRefreshReceiver topicCacheRefreshReceiver){
		return new MessageListenerAdapter(topicCacheRefreshReceiver,"receiveMessage");
	}
	
	
	@Bean
	public CacheManager cacheManager(@Qualifier("bizRedisTemplate")RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		
		return cacheManager;
	}
	
	
	@Bean("bizRedisConnectionFactory")
	RedisConnectionFactory redisConnectionFactory(){
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setDatabase(prop.getDatabase());
		jedisConnectionFactory.setHostName(prop.getHost());
		jedisConnectionFactory.setPassword(prop.getPassword());
		jedisConnectionFactory.setPort(prop.getPort());
		jedisConnectionFactory.setTimeout(prop.getTimeout());
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(prop.getPool().getMaxActive());
		poolConfig.setMaxTotal(prop.getPool().getMaxIdle());
		poolConfig.setMaxWaitMillis(prop.getPool().getMaxWait());
		poolConfig.setMinIdle(prop.getPool().getMinIdle());
		jedisConnectionFactory.setPoolConfig(poolConfig);
		return jedisConnectionFactory;
	}
	
	@Bean
    RedisMessageListenerContainer container(@Qualifier("bizRedisConnectionFactory")RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(messageListenerAdapter(topicCacheReloadReceiver(latch())),new PatternTopic(CacheKey.TopicRefreshCache));
        container.addMessageListener(basicDataListenerAdapter(basicDataReceiver(latch())),new PatternTopic(CacheKey.TopicAddBasicDataStatistical));
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

	@Bean("bizRedisTemplate")
	public RedisTemplate<String, Object> redisTemplate(@Qualifier("bizRedisConnectionFactory")RedisConnectionFactory redisConnectionFactory) {
	    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	    redisTemplate.setConnectionFactory(redisConnectionFactory);
		
		redisTemplate.setDefaultSerializer(new FastJsonRedisSerializer());
		ParserConfig.getGlobalInstance().addAccept("com.yijia360.");
	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();  
		redisTemplate.setKeySerializer(stringRedisSerializer);  
		redisTemplate.setHashKeySerializer(stringRedisSerializer); 
		return redisTemplate;
	}
	
	@Bean("bizStringRedisTemplate")
	public StringRedisTemplate StringRedisTemplate(@Qualifier("bizRedisConnectionFactory")RedisConnectionFactory redisConnectionFactory) {
	    StringRedisTemplate redisTemplate = new StringRedisTemplate(redisConnectionFactory);
		return redisTemplate;
	}
}