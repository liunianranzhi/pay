package com.yijia360.pay.entity.po;

import org.springframework.stereotype.Component;

@Component
public class BaseEntity {
	
	private final int maxPageSize = 30;
	
	private int pageSize =15;
	
	//逻辑页码
	private int pageNo = 1;
	
	//sql页码
	private int start = 0;
	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = (pageSize > maxPageSize) || (pageSize < 1) ? maxPageSize:pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.start = pageNo < 1?0:(pageNo-1)*pageSize;
		this.pageNo =pageNo < 1?1:pageNo;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
}

