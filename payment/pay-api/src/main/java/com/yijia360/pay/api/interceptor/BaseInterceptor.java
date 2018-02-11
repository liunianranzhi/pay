package com.yijia360.pay.api.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.api.action.Result;
import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.core.service.partner.PartnerAppService;
import com.yijia360.pay.core.service.partner.PartnerService;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;
import com.yijia360.pay.entity.vo.partner.PartnerVo;
import com.yijia360.pay.utils.CommonUtils;
import com.yijia360.pay.utils.PayUtils;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日下午2:04:44
 */
@Component
public class BaseInterceptor  extends HandlerInterceptorAdapter{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	PartnerAppService partnerAppService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(response.getStatus() != 200){
			resp(response);
			return false;
		}
		String URI = request.getRequestURI();
		int index = URI.lastIndexOf("/");
		String api = URI.substring(0, index);
		int apiIndex = PayApi.ApiList.indexOf(api);
		if(apiIndex < 0){
			resp(response,Result.code_not_found, "非法请求!");
			return false;
		}
		return verifySign(request,response);
	}
	
	
	public boolean verifySign(HttpServletRequest request,HttpServletResponse response){
		Map<String,String[]> map = request.getParameterMap();
		Map<String,String> parasMap = new HashMap<String, String>();
		for (String k:map.keySet()) {
			parasMap.put(k,map.get(k)[0]);
		}
		String _appId = parasMap.get("appId");
		if(StringUtils.isEmpty(_appId)){
			resp(response, Result.code_error, "appId不存在");
			return false;
		}
		if(!CommonUtils.isAppId(_appId)){
			resp(response, Result.code_error, "appId错误");
			return false;
		}
		String _partnerId = parasMap.get("partnerId");
		if(StringUtils.isEmpty(_partnerId)){
			resp(response, Result.code_error, "partnerId不存在");
			return false;
		}
		if(!CommonUtils.isNumber(_partnerId)){
			resp(response, Result.code_error, "partnerId错误");
			return false;
		}
		String _sign = parasMap.get("sign");
		if(StringUtils.isEmpty(_sign)){
			resp(response, Result.code_error, "sign不存在");
			return false;
		}
		
		if(!CommonUtils.isSign(_sign)){
			resp(response, Result.code_error, "签名错误");
			return false;
		}

		Integer partnerId = Integer.valueOf(_partnerId);
		PartnerVo partner = partnerService.infoByPartnerId(partnerId);
		if(StringUtils.isEmpty(partner)){
			resp(response, Result.code_error, "商户不存在");
			return false;
		}
		if(!partner.getState().equals(1)){
			resp(response, Result.code_error, "商户已禁用");
			return false;
		}
		
		Long appId = Long.valueOf(_appId);
		
		PartnerAppVo partnerAppVo =  partnerAppService.infoById(partnerId, appId);
		
		if(StringUtils.isEmpty(partnerAppVo)){
			resp(response, Result.code_error, "商户appId错误");
			return false;
		}
		if(!partnerAppVo.getState().equals(1)){
			resp(response, Result.code_error, "商户应用已禁用");
			return false;
		}
		String sign = PayUtils.createSign(parasMap, partnerAppVo.getAppSecret());
		logger.info("{}",sign);
		if(!_sign.equals(sign)){
			resp(response, Result.code_error, "签名错误");
			return false;
		}
		return true;
	}
	
	public void resp(HttpServletResponse response){
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Result result = new Result();
		PrintWriter print = null;
		try {
			result.setCode(String.valueOf(response.getStatus()));
			result.setMsg(HttpStatus.valueOf(response.getStatus()).name());
			result.setResult("");
			print = response.getWriter();
			print.print(JSONObject.toJSONString(result));
			print.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			print.close();
		}
	}

	public void resp(HttpServletResponse response,String code,String msg){
		response.setContentType("text/plain;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Result result = new Result();
		PrintWriter print = null;
		try {
			result.setCode(code);
			result.setMsg(msg);
			result.setResult("");
			print = response.getWriter();
			print.print(JSONObject.toJSONString(result));
			print.flush();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}finally{
			print.close();
		}
	}
}
