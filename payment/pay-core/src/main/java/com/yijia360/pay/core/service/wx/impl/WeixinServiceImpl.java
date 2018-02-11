package com.yijia360.pay.core.service.wx.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.core.service.wx.WeixinService;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月13日上午11:39:44
 */
@Service
public class WeixinServiceImpl extends BaseService implements WeixinService{

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ServiceResult snsOAuth2(String wxCode,String wxAppId,String wxAppSecret) {
		ResponseEntity<String> result = restTemplate.getForEntity("https://api.weixin.qq.com/sns/oauth2/access_token?appid={wxAppId}&secret={wxAppSecret}&code={wxCode}&grant_type=authorization_code", String.class, wxAppId,wxAppSecret,wxCode);
		if(result.getStatusCodeValue() == HttpStatus.OK.value()){
			return buildServiceResult(true, result.getBody(), "");
		}else{
			return buildServiceResult(false, "获取失败");
		}
	}
}
