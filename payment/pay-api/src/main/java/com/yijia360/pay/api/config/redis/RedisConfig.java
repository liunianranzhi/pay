package com.yijia360.pay.api.config.redis;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.parser.ParserConfig;
import com.yijia360.pay.entity.cache.CacheName;

/**
 * Created by wangzhen on 2017/3/29.
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean
	@SuppressWarnings("rawtypes")
	public RedisSerializer fastJson2JsonRedisSerializer() {
		return new FastJsonRedisSerializer();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
	    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
	    redisTemplate.setConnectionFactory(cf);  
		redisTemplate.setDefaultSerializer(fastJson2JsonRedisSerializer());
		ParserConfig.getGlobalInstance().addAccept("com.yijia360.pay.");
	    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();  
		redisTemplate.setKeySerializer(stringRedisSerializer);  
		redisTemplate.setHashKeySerializer(stringRedisSerializer); 
		return redisTemplate;
	}

	@Bean
	public CacheManager cacheManager(RedisTemplate<?, ?> redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		Map<String,Long> expiresMap = new HashMap<String,Long>();
		
		expiresMap.put(CacheName.order,3600L);//1小时
		
		expiresMap.put(CacheName.partner,7200L);//2小时

		expiresMap.put(CacheName.partnerApp,7200L);//2小时

		cacheManager.setExpires(expiresMap);
		
		return cacheManager;
	}
	
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                String serviceName = target.getClass().getName();
                sb.append(serviceName.substring(serviceName.lastIndexOf(".")+1, serviceName.length()));
                sb.append(".");
                sb.append(method.getName());
                for (Object obj : params) {
                	sb.append(".");
                    sb.append(obj.toString());
                }
                logger.info("[Cache Key : {} ]",sb.toString());
                return sb.toString();
            }
        };
    }
}