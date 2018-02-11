package com.yijia360.pay.admin.config.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import com.yijia360.pay.admin.config.redis.properties.SessionAuthRedisProperties;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by wangzhen on 2017/3/29.
 */
@EnableConfigurationProperties({SessionAuthRedisProperties.class})
@EnableRedisHttpSession
public class RedisSession{

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setCookieName("yijia360.admin.sessionId");
        defaultCookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer;
    }
	
	@Autowired
	private SessionAuthRedisProperties prop;
	
	@Primary
	@Bean("connectionFactory")
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
}