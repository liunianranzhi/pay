package com.yijia360.pay.admin.service.partner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.partner.PartnerDao;
import com.yijia360.pay.admin.service.cache.CacheService;
import com.yijia360.pay.entity.cache.CacheKey;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.partner.PartnerVo;

@Service
public class PartnerService {
	
	@Autowired
	private PartnerDao partnerDao;
	
	@Autowired
	private CacheService cacheService;
	
	public PartnerVo info(Partner partner){
		return partnerDao.get(partner);
	}
	
	public PageVo page(Partner partner){
		int count = partnerDao.count(partner);
		Object result = partnerDao.list(partner);
		PageVo page = new PageVo(partner.getPageNo(), partner.getPageSize(), count, result);
		return page;
	}
	
	public boolean save(Partner partner){
		boolean result;
		result = partnerDao.save(partner) == 1 ? true : false;
		return result;
	}
	
	public boolean update(Partner partner){
		boolean result;
		result = partnerDao.update(partner) == 1 ? true : false;
		if(result){
			cacheService.refreshCache(String.format(CacheKey.service_PartnerServiceImpl_infoByPartnerId, partner.getPartnerId()));
		}
		return result;
	}
	
	public List<PartnerVo> getList(Partner partner){
		return partnerDao.getList(partner);
	}
	
}
