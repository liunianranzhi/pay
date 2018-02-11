package com.yijia360.pay.core.service.partner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yijia360.pay.core.dao.partner.PartnerAppDao;
import com.yijia360.pay.core.service.partner.PartnerAppService;
import com.yijia360.pay.entity.cache.CacheName;
import com.yijia360.pay.entity.po.partner.PartnerApp;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;

@CacheConfig(cacheNames=CacheName.partnerApp)
@Service
public class PartnerAppServiceImpl implements PartnerAppService{
	
	@Autowired
	private PartnerAppDao partnerAppDao;

	@Cacheable(unless="#result == null")
	@Override
	public PartnerAppVo infoById(Integer partnerId, Long appId) {
		PartnerApp partnerApp = new PartnerApp();
		partnerApp.setAppId(appId);
		partnerApp.setPartnerId(partnerId);
		PartnerAppVo partnerAppVo = partnerAppDao.get(partnerApp);
		return partnerAppVo;
	}

}
