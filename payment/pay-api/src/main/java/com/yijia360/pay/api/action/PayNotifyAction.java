package com.yijia360.pay.api.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.core.service.pay.PayService;
import com.yijia360.pay.core.service.pay.alipay.AliPayService;
import com.yijia360.pay.core.service.pay.wxpay.WxPayService;
import com.yijia360.pay.core.service.pay.wxpay.util.WxPayUtil;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:25:29
 */
@RequestMapping(PayApi.PayNotify)
@RestController
public class PayNotifyAction extends BaseAction{
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private WxPayService wxPayService;
	
	@Autowired
	private AliPayService aliPayService;
	
	
	@PostMapping("/wxPayCallBack")
	public String wxPayCallBack(HttpServletRequest request){

		String notifyXMLResult = WxPayUtil.readData(request);

		logger.info("wxPayCallbak: {}",notifyXMLResult);
		
		if(StringUtils.isEmpty(notifyXMLResult)){
			return "";
		}

		Map<String, String> params = WxPayUtil.xmlToMap(notifyXMLResult);

		Map<String,String> resultMap = new HashMap<String, String>();
		
		boolean verifyNotifyResult = wxPayService.verifyNotifyResult(params);
		if(verifyNotifyResult){
			resultMap.put("return_code", "SUCCESS");
			resultMap.put("return_msg", "OK");
		}
		return WxPayUtil.toXml(resultMap);
	}
	
	
	@PostMapping("/aliPayCallBack")
	public String aliPayCallBack(HttpServletRequest request){
		
		Map<String,String[]> map = request.getParameterMap();
		
		logger.info("aliPayCallBack: {}",JSONObject.toJSONString(map));
		
		if(ObjectUtils.isEmpty(map)){
			return "";
		}
		
		if(aliPayService.verifyNotifyResult(map)){
			return "success";
		}
		return "";
	}	
}