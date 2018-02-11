package com.yijia360.pay.entity.enums;


public enum PayMode {
	
	alipayApp(1,"支付宝app"),
	alipayWap(2,"支付宝wap"),
	alipayPc(3,"支付宝pc"),
	alipayQr(4,"支付宝qr"),
	wxApp(5,"微信app"),
	wxPub(6,"微信公众号"),
	wxPubQr(7,"微信公众号qr");

	private Integer code;
	private String msg;
	private PayMode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public static String getPayService(Integer key){
		if( key >= 1 && key <=4){
			return "aliPayService";
		}
		if( key >= 5 && key <=7){
			return "wxPayService";
		}
		return "";
	}
	
	public static PayMode getInfo(Integer key){
		PayMode[] array = PayMode.values();
		for(PayMode pm:array){
			if(pm.getCode().equals(key)){
				return pm;
			}
		}
		return null;
	}
}