package com.yijia360.pay.admin.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yijia360.pay.entity.cache.CacheKey;

@Service
public class CacheService {

	@Autowired
	@Qualifier("bizStringRedisTemplate")
	RedisTemplate<String,String> bizStringRedisTemplate;
	
	public void refreshCache(String key){
		bizStringRedisTemplate.convertAndSend(CacheKey.TopicRefreshCache, key);
	}
	
	
	public void addBasicDataStatistical(String payResult){
		bizStringRedisTemplate.convertAndSend(CacheKey.TopicAddBasicDataStatistical, payResult);
	}
}
