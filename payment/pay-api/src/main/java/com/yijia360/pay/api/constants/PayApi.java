package com.yijia360.pay.api.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:02:59
 */
public class PayApi {
	
	public static final String Domain ="/pay/api";
	
	//商户
	public static final String Partner = Domain+"/partner";
	//商户应用
	public static final String PartnerApp = Domain+"/partnerApp";

	//订单
	public static final String Order = Domain+"/order";

	//订单
	public static final String WeiXin = Domain+"/wx";

	public static final String PayNotify = Domain+"/payNotify";
	
	public static final String PayTestNotify = Domain+"/payTestNotify";
	
	public static List<String> ApiList = new ArrayList<String>();

	static{
		ApiList.add(Partner);
		ApiList.add(PartnerApp);
		ApiList.add(Order);
		ApiList.add(PayNotify);
		ApiList.add(WeiXin);
		ApiList.add(PayTestNotify);
	}
}