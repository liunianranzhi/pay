package com.yijia360.pay.admin.service.partner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.partner.PartnerAppDao;
import com.yijia360.pay.admin.service.cache.CacheService;
import com.yijia360.pay.entity.cache.CacheKey;
import com.yijia360.pay.entity.po.partner.PartnerApp;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;

@Service
public class PartnerAppService {

	@Autowired
	private PartnerAppDao partnerAppDao;
	
	@Autowired
	private CacheService cacheService;
	
	public PartnerAppVo info(PartnerApp partnerApp){
		return partnerAppDao.get(partnerApp);
	}
	
	public PageVo page(PartnerApp partnerApp){
		int count = partnerAppDao.count(partnerApp);
		Object result = partnerAppDao.list(partnerApp);
		PageVo page = new PageVo(partnerApp.getPageNo(), partnerApp.getPageSize(), count, result);
		return page;
	}
	
	public boolean save(PartnerApp partnerApp){
		boolean result;
		result = partnerAppDao.save(partnerApp) == 1 ? true : false;
		return result;
	}
	
	public boolean update(PartnerApp partnerApp){
		boolean result;
		result = partnerAppDao.update(partnerApp) == 1 ? true : false;
		if(result){
			cacheService.refreshCache(String.format(CacheKey.service_PartnerAppServiceImpl_infoById, partnerApp.getPartnerId(),partnerApp.getAppId()));
		}
		return result;
	}
	
	public List<PartnerAppVo> getList(PartnerApp partnerApp){
		List<PartnerAppVo> partnerAppVos = partnerAppDao.getList(partnerApp);
		return partnerAppVos;
	}
}
