package com.yijia360.pay.admin.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author wangzhen@inongfeng.com
 * @ctime 2016年12月2日下午3:07:10
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{

	@Bean
	public UserSecurityInterceptor userSecurityInterceptor(){
		return new UserSecurityInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userSecurityInterceptor()).addPathPatterns("/**").excludePathPatterns("/Login");
		super.addInterceptors(registry);
	}

}
