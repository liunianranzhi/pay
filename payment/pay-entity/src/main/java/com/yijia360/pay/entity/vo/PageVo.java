package com.yijia360.pay.entity.vo;
/**
 * @author wangzhen@inongfeng.com
 * @ctime 2016年11月30日下午2:41:22
 */
public class PageVo {
	
	private int pageNo;
	private int pageSize;
	private int count;
	private int totalPage;
	private Object result;
	
	public PageVo(int pageNo,int pageSize,int count,Object result){
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.count =count;
		this.result = result;
		this.totalPage = count/pageSize+ (count%pageSize== 0?0:1);
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
