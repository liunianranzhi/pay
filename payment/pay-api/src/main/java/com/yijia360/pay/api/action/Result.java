package com.yijia360.pay.api.action;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:16:22
 */
@Component
public class Result {
	/**
	 * 成功
	 */
	public static final String code_success = "200";
	
	/**
	 * 程序异常
	 */
	public static final String code_error = "500";
	
	/**
	 * 参数错误
	 */
	public static final String code_invalid_parameter = "400";
	
	/**
	 * 请求不存在
	 */
	public static final String code_not_found = "404";
	
	
	
 
	private String msg = "";
	
	private Object result = null;
	
	private Timestamp time = new Timestamp(System.currentTimeMillis());
	
	private String code = code_error;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
