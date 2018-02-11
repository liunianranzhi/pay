package com.yijia360.pay.core.service.partner;

import org.springframework.stereotype.Service;

import com.yijia360.pay.entity.vo.partner.PartnerAppVo;


/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日下午3:17:51
 */
@Service
public interface PartnerAppService {
	
	/**
	 * 获取商户应用信息
	 * @param partnerAppPo
	 * @return
	 */
	PartnerAppVo infoById(Integer partnerId,Long appId);
	
}
