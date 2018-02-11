package com.yijia360.pay.entity.po;
/**
 * @author wangzhen@inongfeng.com
 * @ctime 2016年12月1日下午4:37:22
 */
public enum CommonEnum {

	effective(1,"有效"),
	
	invalid(0,"无效");

    private final int code;  
    private final String message;  

    CommonEnum(int code,String message){
		this.code = code;
		this.message=message;
	}
	
    public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
