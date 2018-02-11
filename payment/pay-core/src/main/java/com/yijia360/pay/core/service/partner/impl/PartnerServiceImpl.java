package com.yijia360.pay.core.service.partner.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.yijia360.pay.core.dao.partner.PartnerDao;
import com.yijia360.pay.core.service.partner.PartnerService;
import com.yijia360.pay.entity.cache.CacheName;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.vo.partner.PartnerVo;

@CacheConfig(cacheNames=CacheName.partner)
@Service
public class PartnerServiceImpl implements PartnerService{

	@Autowired
	private PartnerDao partnerDao;
	
	@Cacheable(unless="#result == null")
	@Override
	public PartnerVo infoByPartnerId(Integer partnerId) {
		Partner partner = new Partner();
		partner.setPartnerId(partnerId);
		PartnerVo partnerVo = partnerDao.get(partner);
		return partnerVo;
	}

}
