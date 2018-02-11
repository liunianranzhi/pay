package com.yijia360.pay.entity.vo.partner;

public class PayModeConfVo {
	
	private Integer partnerId;
	private Long appId;
	private String payModeName;
	private Integer payModeCode;
	private String confPartnerId;
	private String confPrivateKey;
	private String confAppId;
	private String confRsaPrivateKey;
	
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getPayModeName() {
		return payModeName;
	}
	public void setPayModeName(String payModeName) {
		this.payModeName = payModeName;
	}
	public Integer getPayModeCode() {
		return payModeCode;
	}
	public void setPayModeCode(Integer payModeCode) {
		this.payModeCode = payModeCode;
	}
	public String getConfPartnerId() {
		return confPartnerId;
	}
	public void setConfPartnerId(String confPartnerId) {
		this.confPartnerId = confPartnerId;
	}
	public String getConfPrivateKey() {
		return confPrivateKey;
	}
	public void setConfPrivateKey(String confPrivateKey) {
		this.confPrivateKey = confPrivateKey;
	}
	public String getConfAppId() {
		return confAppId;
	}
	public void setConfAppId(String confAppId) {
		this.confAppId = confAppId;
	}
	public String getConfRsaPrivateKey() {
		return confRsaPrivateKey;
	}
	public void setConfRsaPrivateKey(String confRsaPrivateKey) {
		this.confRsaPrivateKey = confRsaPrivateKey;
	}
	
}
