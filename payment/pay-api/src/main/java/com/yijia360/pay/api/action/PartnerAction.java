package com.yijia360.pay.api.action;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.entity.po.partner.Partner;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:25:29
 */
@RequestMapping(PayApi.Partner)
@RestController
public class PartnerAction extends BaseAction{
	
	
	@PostMapping("/info")
	public Result info(Partner partner){
		
		Result result = new Result();
		
		return buildSuccessResult(result, "");
	}
}