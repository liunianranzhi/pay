package com.yijia360.pay.admin.service.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.partner.PayModeConfDao;
import com.yijia360.pay.admin.service.cache.CacheService;
import com.yijia360.pay.entity.cache.CacheKey;
import com.yijia360.pay.entity.po.partner.PayModeConf;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

@Service
public class PayModeConfService {

	@Autowired
	private PayModeConfDao payModeConfDao;
	
	@Autowired
	private CacheService cacheService;
	
	public PayModeConfVo info(PayModeConf payModeConf){
		return payModeConfDao.get(payModeConf);
	}
	
	public boolean save(PayModeConf payModeConf){
		boolean result = payModeConfDao.save(payModeConf) == 1 ? true : false;
		return result;
	}
	
	public boolean update(PayModeConf payModeConf){
		boolean result = payModeConfDao.update(payModeConf) == 1 ? true : false;
		if(result){
			cacheService.refreshCache(String.format(CacheKey.service_PayModeConfServiceImpl_InfoById, 
					payModeConf.getPartnerId(),payModeConf.getAppId(),payModeConf.getPayModeCode()));
		}
		return result;
	}
	
}
