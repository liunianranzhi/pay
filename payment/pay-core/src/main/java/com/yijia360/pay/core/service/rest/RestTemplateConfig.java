package com.yijia360.pay.core.service.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月17日下午3:27:56
 */
@ConfigurationProperties(prefix = "pay.rest.connection")
public class RestTemplateConfig {
	private Integer connectionRequestTimeout;

	private Integer connectTimeout;
	
	private Integer readTimeout;

	public Integer getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}
}
