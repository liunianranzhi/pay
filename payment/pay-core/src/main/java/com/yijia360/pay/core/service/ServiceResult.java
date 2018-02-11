package com.yijia360.pay.core.service;

import org.springframework.stereotype.Component;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:43:56
 */
@Component
public class ServiceResult {

    /**
     * 操作状态
     */
    private boolean status =false;


    /**
     *操作返回结果
     */
    private Object result;

    
    private String msg;

	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public boolean getStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Object getResult() {
		return result;
	}


	public void setResult(Object result) {
		this.result = result;
	}
}
