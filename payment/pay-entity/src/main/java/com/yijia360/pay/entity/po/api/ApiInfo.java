package com.yijia360.pay.entity.po.api;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;



/**
 * 
 * @author yangjian
 * @date 2017-03-17
 */
@Component
public class ApiInfo extends BaseEntity{

	private Integer apiId;
	private String apiName;
	private String apiAddr;
	private String apiDesc;
	private String apiMethod;
	private String detail;
	private String resultDesc;
	private String argsList;
	private Long utime;
	public Integer getApiId() {
		return apiId;
	}
	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiAddr() {
		return apiAddr;
	}
	public void setApiAddr(String apiAddr) {
		this.apiAddr = apiAddr;
	}
	public String getApiDesc() {
		return apiDesc;
	}
	public void setApiDesc(String apiDesc) {
		this.apiDesc = apiDesc;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getApiMethod() {
		return apiMethod;
	}
	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	public Long getUtime() {
		return utime;
	}
	public void setUtime(Long utime) {
		this.utime = utime;
	}
	public String getArgsList() {
		return argsList;
	}
	public void setArgsList(String argsList) {
		this.argsList = argsList;
	}
	
}
