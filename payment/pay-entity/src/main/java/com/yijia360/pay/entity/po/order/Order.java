package com.yijia360.pay.entity.po.order;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;

@Component
public class Order extends BaseEntity{
	
	private Long orderId;
	private Long appId;
	private Integer partnerId;
	private String subject;
	private String detail;
	private String outTradeNo;
	private String notifyUrl;
	private Integer totalFee;
	private Timestamp cTime;
	private Integer status;
	private String statusMsg;
	private String createIp;
	private Integer payMode;
	private String payInfo;
	private String tradeNo;
	private String attr;
	private Integer paid;
	private String wxOpenId;
	
	private Timestamp startTime;
	private Timestamp endTime;
	
	
	private String appLancher;
	private String paySuccessUrl;
	private String payErrorUrl;
	
	public String getPaySuccessUrl() {
		return paySuccessUrl;
	}
	public void setPaySuccessUrl(String paySuccessUrl) {
		this.paySuccessUrl = paySuccessUrl;
	}
	public String getPayErrorUrl() {
		return payErrorUrl;
	}
	public void setPayErrorUrl(String payErrorUrl) {
		this.payErrorUrl = payErrorUrl;
	}
	public String getAppLancher() {
		return appLancher;
	}
	public void setAppLancher(String appLancher) {
		this.appLancher = appLancher;
	}
	public String getWxOpenId() {
		return wxOpenId;
	}
	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	public Integer getPaid() {
		return paid;
	}
	public void setPaid(Integer paid) {
		this.paid = paid;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public Integer getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public String getPayInfo() {
		return payInfo;
	}
	public void setPayInfo(String payInfo) {
		this.payInfo = payInfo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getCreateIp() {
		return createIp;
	}
	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}
	public Timestamp getcTime() {
		return cTime;
	}
	public void setcTime(Timestamp cTime) {
		this.cTime = cTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
}
