package com.yijia360.pay.entity.po.partner;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;


@Component
public class Partner extends BaseEntity{

	private Integer partnerId;
	private String partnerName;
	private String partnerMail;
	private String operator;
	private String mobile;
	private Integer state;
	private Long cTime;
	
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public String getPartnerMail() {
		return partnerMail;
	}
	public void setPartnerMail(String partnerMail) {
		this.partnerMail = partnerMail;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getcTime() {
		return cTime;
	}
	public void setcTime(Long cTime) {
		this.cTime = cTime;
	}
	
}
