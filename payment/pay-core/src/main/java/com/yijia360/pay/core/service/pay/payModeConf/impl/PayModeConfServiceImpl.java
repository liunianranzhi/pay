package com.yijia360.pay.core.service.pay.payModeConf.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yijia360.pay.core.dao.pay.PayModeConfDao;
import com.yijia360.pay.core.service.pay.payModeConf.PayModeConfService;
import com.yijia360.pay.entity.po.partner.PayModeConf;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;


@CacheConfig(cacheNames="payModeConf")
@Service
public class PayModeConfServiceImpl implements PayModeConfService{

	@Autowired
	PayModeConfDao payModeConfDao;
	
	@Cacheable(unless="#result == null")
	@Override
	public PayModeConfVo InfoById(Integer partnerId,Long appId,Integer payModeCode) {
		PayModeConf payModeConf = new PayModeConf();
		payModeConf.setAppId(appId);
		payModeConf.setPartnerId(partnerId);
		payModeConf.setPayModeCode(payModeCode);
		return payModeConfDao.get(payModeConf);
	}
}
