package com.yijia360.pay.core.service.partner;

import org.springframework.stereotype.Service;

import com.yijia360.pay.entity.vo.partner.PartnerVo;


/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日下午3:17:51
 */
@Service
public interface PartnerService {
	
	/**
	 * 获取商户基本信息
	 * @param partnerAppPo
	 * @return
	 */
	PartnerVo infoByPartnerId(Integer partnerId);
	
	
	
}
