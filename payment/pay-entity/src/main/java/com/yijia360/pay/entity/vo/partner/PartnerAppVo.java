package com.yijia360.pay.entity.vo.partner;

public class PartnerAppVo {
	
	private Integer partnerId;
	private String appName;
	private Long appId;
	private String appSecret;
	private String notify;
	private Integer isIpLimit;
	private String ip;
	private Integer state;
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public Integer getIsIpLimit() {
		return isIpLimit;
	}
	public void setIsIpLimit(Integer isIpLimit) {
		this.isIpLimit = isIpLimit;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
