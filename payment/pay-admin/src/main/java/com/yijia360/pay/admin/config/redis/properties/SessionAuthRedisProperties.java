package com.yijia360.pay.admin.config.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月21日下午1:43:35
 */
@ConfigurationProperties(prefix = "auth.spring.redis")
public class SessionAuthRedisProperties extends BaseRedisProperties{
	
}
