package com.yijia360.pay.entity.po.partner;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;


@Component
public class PartnerApp extends BaseEntity{

	private Integer partnerId;
	private String appName;
	private Long appId;
	private String salt;
	private String appSecret;
	private String notify;
	private Integer isIpLimit;
	private String ip;
	private Long cTime;
	private Integer state;
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
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
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
	public Long getcTime() {
		return cTime;
	}
	public void setcTime(Long cTime) {
		this.cTime = cTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
