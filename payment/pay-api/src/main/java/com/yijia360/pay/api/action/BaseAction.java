package com.yijia360.pay.api.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.yijia360.pay.core.service.ServiceResult;



/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:22:58
 */
@Controller
public class BaseAction {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 成功
	 */
	String code_success = Result.code_success;
	
	/**
	 * 失败
	 */
	String code_invalid_parameter = Result.code_invalid_parameter;
	
	/**
	 * 程序异常
	 */
	String code_exception = Result.code_error;
	
	/**
	 * 构建结果
	 * @param result
	 * @param serviceResult
	 * @param msg
	 */
	public Result buildResult(ServiceResult serviceResult){
		Result  result = new Result();
		result.setResult(serviceResult.getResult());
		result.setCode(serviceResult.getStatus()?code_success:code_invalid_parameter);
		result.setMsg(serviceResult.getMsg());
		return result;
	}
	
	
	public Result buildErrorResult(String msg){
		Result result = new Result();
		result.setResult(false);
		result.setCode(code_invalid_parameter);
		result.setMsg(msg);
		return result;
	}
	
	public Result buildSuccessResult(Result result,String msg){
		result.setResult(null);
		result.setCode(code_success);
		result.setMsg(msg);
		return result;
	}
	
	
	public  String getRemoteIp(HttpServletRequest request){
		  // 主站 和 移动端，请求时，附带IP, 其他各端，则从nginx请求IP
		  if(!StringUtils.isEmpty(request.getParameter("clientIP"))){
			  return request.getParameter("clientIP"); 
		  }
		  String ip = request.getHeader("x-forwarded-for"); 
		  if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("Proxy-Client-IP"); 
		  } 
		  if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			  ip = request.getHeader("WL-Proxy-Client-IP"); 
		  } 
		  if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		      ip = request.getRemoteAddr(); 
		  } 
		  return ip; 
	}
}
