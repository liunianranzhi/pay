package com.yijia360.pay.core.service.rest;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月17日下午2:27:07
 */
@Component
@EnableConfigurationProperties(RestTemplateConfig.class)
public class RestTemplateService{
	
	@Autowired
	RestTemplateConfig restTemplateConfig;
	
	@Bean
    public SimpleClientHttpRequestFactory httpClientFactory() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(restTemplateConfig.getReadTimeout());
        httpRequestFactory.setConnectTimeout(restTemplateConfig.getConnectTimeout());
        return httpRequestFactory;
    }

    @Bean
    public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory) {
        RestTemplate restTemplate = new RestTemplate(httpClientFactory);
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter(Charset.defaultCharset()));
        messageConverters.add(new FormHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
