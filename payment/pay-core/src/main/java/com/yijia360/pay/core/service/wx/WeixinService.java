package com.yijia360.pay.core.service.wx;

import org.springframework.stereotype.Service;

import com.yijia360.pay.core.service.ServiceResult;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月13日上午11:38:05
 */
@Service
public interface WeixinService {
	
	
	ServiceResult snsOAuth2(String wxCode, String wxAppId, String wxAppSecret);

}
