package com.yijia360.pay.core.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.yijia360.pay.entity.cache.CacheKey;

@Service
public class CacheService {

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	public void refreshCache(String key){
		redisTemplate.convertAndSend(CacheKey.TopicRefreshCache, key);
	}
	
	public void addBasicDataStatistical(String payResult){
		redisTemplate.convertAndSend(CacheKey.TopicAddBasicDataStatistical, payResult);
	}
	
}
