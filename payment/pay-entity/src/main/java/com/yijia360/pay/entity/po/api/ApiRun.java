package com.yijia360.pay.entity.po.api;

import org.springframework.stereotype.Component;

@Component
public class ApiRun {
	
	private String apiAddr;
	private String apiMethod;
	private String argsList;
	public String getApiAddr() {
		return apiAddr;
	}
	public void setApiAddr(String apiAddr) {
		this.apiAddr = apiAddr;
	}
	public String getApiMethod() {
		return apiMethod;
	}
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	public String getArgsList() {
		return argsList;
	}
	public void setArgsList(String argsList) {
		this.argsList = argsList;
	}
	
}
