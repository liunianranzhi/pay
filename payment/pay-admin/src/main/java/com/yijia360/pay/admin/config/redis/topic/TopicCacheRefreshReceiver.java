package com.yijia360.pay.admin.config.redis.topic;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TopicCacheRefreshReceiver {
	
    private static final Logger logger = LoggerFactory.getLogger(TopicCacheRefreshReceiver.class);

	@Autowired
	@Qualifier("bizStringRedisTemplate")
    private RedisTemplate<String,String> bizStringRedisTemplate;
	
    private CountDownLatch latch;
    
    @Autowired
    public TopicCacheRefreshReceiver(CountDownLatch latch) {
        this.latch = latch;
    }
    
    public void receiveMessage(String message) {
    	bizStringRedisTemplate.delete(message);
    	logger.info("TopicCacheReloadReceiver CacheKey:{}",message);
    	latch.countDown();
    }
}