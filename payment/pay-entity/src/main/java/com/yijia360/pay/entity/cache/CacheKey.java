package com.yijia360.pay.entity.cache;



/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月7日下午1:30:07
 */
public class CacheKey {
	
	/**
	 * 刷新缓存
	 */
	public static final String TopicRefreshCache = "TopicRefreshCache";
	
	public static final String TopicAddBasicDataStatistical = "TopicAddBasicDataStatistical";
	
	
	/**
	 * 商户详情ById
	 * Key: PrtnerServiceImpl.infoByPartnerId.partnerId
	 */
	public static final String service_PartnerServiceImpl_infoByPartnerId = "PartnerServiceImpl.infoByPartnerId.%s";
	
	/**
	 * 商户APP
	 * Key: PartnerAppServiceImpl.infoById.partnerId.appId
	 */
	public static final String service_PartnerAppServiceImpl_infoById = "PartnerAppServiceImpl.infoById.%s.%s";
	
	/**
	 * 商户支付方式
	 * Key：  PayModeConfServiceImpl.InfoById.partnerId.appId.payModeCode
	 */
	public static final String service_PayModeConfServiceImpl_InfoById = "PayModeConfServiceImpl.InfoById.%s.%s.%s";
	
	
	/**
	 * 订单详情
	 * OrderServiceImpl.info.orderId
	 */
	public static final String service_OrderServiceImpl_Info = "OrderServiceImpl.info.%s";
	
	/**
	 * 订单详情
	 * OrderServiceImpl.Info.outTradeNo.appId.partnerId
	 */
	public static final String service_OrderServiceImpl_Info_outTradeNo_appId_partnerId = "OrderServiceImpl.info.%s.%s.%s";


	
	public static void main(String[] args) {
		Integer a = 1000;
		System.out.println(String.format(service_PartnerServiceImpl_infoByPartnerId, a));
		System.out.println(String.format(service_PartnerAppServiceImpl_infoById, a,9999191919199999l));
	}
}
