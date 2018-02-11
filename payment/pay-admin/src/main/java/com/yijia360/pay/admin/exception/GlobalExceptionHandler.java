package com.yijia360.pay.admin.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yijia360.pay.admin.commom.Constants;


/**
 * @author wangzhen@inongfeng.com
 * @ctime 2016年12月2日下午3:23:17
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${auth.login.uri}")
	String AuthAdminPlatformUri;

	
    @ExceptionHandler(value = BaseException.class)
    public Object baseErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	if(e.getMessage().equals(Constants.E_LoginError_OR_TimeOut)){
    		if(logger.isDebugEnabled()){
    			e.printStackTrace();
                logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {} ,登录失败或超时", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
    		}
    		req.setAttribute("AuthAdminPlatformUri", AuthAdminPlatformUri);
            return "login";
    	}else{
            logger.error("---BaseException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());
    	}
        return e.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if(logger.isDebugEnabled()){
        	logger.error("---DefaultException Handler---Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());	
        }
        return e.getMessage();
    }
}
