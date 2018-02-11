package com.yijia360.pay.api.interceptor;

import com.yijia360.pay.api.constants.PayApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:19:14
 */
@EnableWebMvc
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {


	@Bean
	public BaseInterceptor baseInterceptor() {
		return new BaseInterceptor();
	}

	@Bean
	public PartnerInterceptor partnerInterceptor() {
		return new PartnerInterceptor();
	}


	/**
	 * 拦截器链
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		//请求接口拦截
		registry.addInterceptor(baseInterceptor()).addPathPatterns("/**").excludePathPatterns(PayApi.PayNotify+"/**",PayApi.PayTestNotify+"/**");

		//商户管理
		registry.addInterceptor(partnerInterceptor()).addPathPatterns(PayApi.Partner + "/**");
		
        super.addInterceptors(registry);
	}
}
