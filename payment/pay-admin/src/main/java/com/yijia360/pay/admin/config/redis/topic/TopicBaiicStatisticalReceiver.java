package com.yijia360.pay.admin.config.redis.topic;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yijia360.pay.admin.service.statistical.BasicDataSeivice;
import com.yijia360.pay.entity.po.paystatistics.BasicDataStatistical;

@Component
public class TopicBaiicStatisticalReceiver {

	private static final Logger logger = LoggerFactory.getLogger(TopicBaiicStatisticalReceiver.class);
	 
    private CountDownLatch latch;
    
    @Autowired
    public TopicBaiicStatisticalReceiver(CountDownLatch latch) {
        this.latch = latch;
    }
    
    @Autowired
	private BasicDataSeivice basicDataSeivice;
    
    public void receiveBasicData(String jsonMsg) {
    	//json转换成对象
    	BasicDataStatistical basic=JSON.parseObject(jsonMsg, BasicDataStatistical.class);
    	basicDataSeivice.save(basic);
    	logger.info("TopicBaiicStatisticalReceiver basicDataKey:{}",jsonMsg);
        latch.countDown();
    }
}
