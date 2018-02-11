package com.yijia360.pay.admin.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yijia360.pay.admin.commom.Constants;
import com.yijia360.pay.admin.exception.LoginAuthException;



/**
 * @author wangzhen@inongfeng.com
 * @ctime 2016年12月2日下午3:08:43
 */
@Component
public class UserSecurityInterceptor implements HandlerInterceptor{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${auth.login.uri}")
	String AuthAdminPlatformUri;
	
	@Override
	public void afterCompletion(HttpServletRequest req,HttpServletResponse resp, Object arg2, Exception arg3)throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
		
		
		HttpSession session = request.getSession();
		if(StringUtils.isEmpty(session.getAttribute(Constants.SessionUserInfo))){
			throw new LoginAuthException(Constants.E_LoginError_OR_TimeOut);
//			return false;
		}else{
			
			if(StringUtils.isEmpty(session.getAttribute("AuthAdminPlatformUri"))){
				request.setAttribute("AuthAdminPlatformUri", AuthAdminPlatformUri);
			}
			return true;	
		}
	}

}
