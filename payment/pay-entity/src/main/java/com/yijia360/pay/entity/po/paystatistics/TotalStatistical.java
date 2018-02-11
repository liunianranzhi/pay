package com.yijia360.pay.entity.po.paystatistics;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;

@Component
public class TotalStatistical extends BaseEntity {

	private Integer payMode;
	private BigDecimal dailyTotalPrice;
	private Integer dailyTotalCount;
	private BigDecimal historyTotalPrice;
	private Integer historyTotalCount;
	private Integer cTime;
	
	private int pageSize = 7;
	private int start = 0;
	private int pageNo = 1;
	
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public BigDecimal getDailyTotalPrice() {
		return dailyTotalPrice;
	}
	public void setDailyTotalPrice(BigDecimal dailyTotalPrice) {
		this.dailyTotalPrice = dailyTotalPrice;
	}
	public Integer getDailyTotalCount() {
		return dailyTotalCount;
	}
	public void setDailyTotalCount(Integer dailyTotalCount) {
		this.dailyTotalCount = dailyTotalCount;
	}
	public BigDecimal getHistoryTotalPrice() {
		return historyTotalPrice;
	}
	public void setHistoryTotalPrice(BigDecimal historyTotalPrice) {
		this.historyTotalPrice = historyTotalPrice;
	}
	public Integer getHistoryTotalCount() {
		return historyTotalCount;
	}
	public void setHistoryTotalCount(Integer historyTotalCount) {
		this.historyTotalCount = historyTotalCount;
	}
	public Integer getcTime() {
		return cTime;
	}
	public void setcTime(Integer cTime) {
		this.cTime = cTime;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPageNo(int pageNo) {
		this.start = pageNo < 1?0:(pageNo-1)*pageSize;
		this.pageNo =pageNo < 1?1:pageNo;
	}
	public int getStart() {
		return start;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setStart(int start) {
		this.start = start;
	}
	
}
