package com.yijia360.pay.core.service.pay.wxpay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.cache.CacheService;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.core.service.order.OrderLogService;
import com.yijia360.pay.core.service.order.OrderNotifyService;
import com.yijia360.pay.core.service.order.OrderService;
import com.yijia360.pay.core.service.pay.TradeService;
import com.yijia360.pay.core.service.pay.payModeConf.PayModeConfService;
import com.yijia360.pay.core.service.pay.wxpay.util.WxPayUtil;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月30日下午2:00:10
 */
@Service
public class WxPayService extends BaseService implements TradeService{
	
	
	String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
    @Value("${pay.wx.notify}")
	String payNotify;

    
	@Autowired
	private RestTemplate restTemplate;
    
	@Autowired
	PayModeConfService payModeConfService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderLogService orderLogService;
	
	@Autowired  
	OrderNotifyService orderNotifyService;
	
	@Autowired  
	CacheService cacheService;

	public ServiceResult parsePayResult(String payResult){
		JSONObject jsonResult = JSONObject.parseObject(payResult);
		if(jsonResult.getString("return_code").equals("SUCCESS") && jsonResult.getString("result_code").equals("SUCCESS")){
			return buildServiceResult(true, payResult,"");
		}
		logger.info("[微信支付失败]:{}",jsonResult);
		String errCodeDes = jsonResult.getString("err_code_des");
		return buildServiceResult(false,"","微信支付失败"+(StringUtils.isEmpty(errCodeDes)?"":errCodeDes));
	}
	
	
	@Override
	public ServiceResult trade(Order order) {
		PayModeConfVo payModeConf = payModeConfService.InfoById(order.getPartnerId(), order.getAppId(), order.getPayMode());
		if(ObjectUtils.isEmpty(payModeConf)){
			return buildServiceResult(false, "请配置该支付方式");
		}
		//公众号ID
		String wxAppId = payModeConf.getConfAppId();
		//商户ID
		String mch_id =  payModeConf.getConfPartnerId();
		//API私钥
		String paternerKey = payModeConf.getConfPrivateKey();

		JSONObject attach = new JSONObject();
		attach.put("payMode",order.getPayMode());
		attach.put("orderId", order.getOrderId());

		Map<String,String> parasMap = new HashMap<String, String>();
		parasMap.put("appid", wxAppId);
		parasMap.put("attach", attach.toJSONString());
		parasMap.put("mch_id",mch_id);
		parasMap.put("notify_url", payNotify);
		parasMap.put("paternerKey",paternerKey);
		parasMap.put("total_fee", order.getTotalFee().toString());
		parasMap.put("out_trade_no", order.getOutTradeNo());
		parasMap.put("body", order.getSubject());
		parasMap.put("spbill_create_ip", order.getCreateIp());
		
		//二维码支付 pc端
		if(order.getPayMode().equals(PayMode.wxPubQr.getCode())){
			String payResult = pushOrder(parasMap);
			return parsePayResult(payResult);
		}
		
		//公众号支付
		if(order.getPayMode().equals(PayMode.wxPub.getCode())){
			if(StringUtils.isEmpty(order.getWxOpenId())){
				return buildServiceResult(false, "请检查微信wxOpenId");
			}
			parasMap.put("openid", order.getWxOpenId());
			return pushOrderByH5(parasMap);
		}
		
		if(order.getPayMode().equals(PayMode.wxApp.getCode())){
			return pushOrderByApp(parasMap);
		}
		return null;
	}


	
	/**
	 * 公众号扫码支付-下单
	 * @param params
	 * @return
	 */
	public  String pushOrder(Map<String, String> params) {
		Map<String, String> reqMap  = new HashMap<String, String>();
		reqMap.put("appid", params.get("appid"));
		reqMap.put("attach", params.get("attach"));
		reqMap.put("mch_id",params.get("mch_id"));
		reqMap.put("notify_url",params.get("notify_url"));
		reqMap.put("trade_type", "NATIVE");
		reqMap.put("total_fee", params.get("total_fee"));
		reqMap.put("out_trade_no", params.get("out_trade_no"));
		reqMap.put("body", params.get("body"));
		reqMap.put("spbill_create_ip", params.get("spbill_create_ip"));
		reqMap.put("nonce_str",WxPayUtil.getRandom(32));
		String sign = WxPayUtil.createSign(reqMap,params.get("paternerKey"));
		reqMap.put("sign", sign);
		String xmlData =  restTemplate.postForObject(payUrl, WxPayUtil.toXml(reqMap), String.class);
		Map<String, String> m = WxPayUtil.xmlToMap(xmlData);
		return  JSONObject.toJSONString(m);
	}
	
	
	/**
	 * 公众号JSSDK支付-下单
	 * @param params
	 * @return
	 */
	public  ServiceResult pushOrderByH5(Map<String, String> params) {
		Map<String, String> reqMap  = new HashMap<String, String>();
		reqMap.put("appid", params.get("appid"));
		reqMap.put("attach", params.get("attach"));
		reqMap.put("mch_id", params.get("mch_id"));
		reqMap.put("notify_url", params.get("notify_url"));
		reqMap.put("trade_type", "JSAPI");
		reqMap.put("total_fee", params.get("total_fee"));
		reqMap.put("out_trade_no", params.get("out_trade_no"));
		reqMap.put("body", params.get("body"));
		reqMap.put("spbill_create_ip", params.get("spbill_create_ip"));
		reqMap.put("openid", params.get("openid"));
		reqMap.put("nonce_str", WxPayUtil.getRandom(32));
		String sign = WxPayUtil.createSign(reqMap, params.get("paternerKey"));
		reqMap.put("sign", sign);
		String xmlData =  restTemplate.postForObject(payUrl, WxPayUtil.toXml(reqMap), String.class);
		Map<String, String>  resultMap = WxPayUtil.xmlToMap(xmlData);
		ServiceResult serviceResult = parsePayResult(JSONObject.toJSONString(resultMap));
		if(!serviceResult.getStatus()){
			return serviceResult;
		}
		Map<String, String> signMap = new HashMap<String, String>(); 
		signMap.put("appId", resultMap.get("appid"));
		signMap.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
		signMap.put("nonceStr", resultMap.get("nonce_str"));
		signMap.put("package", "prepay_id="+resultMap.get("prepay_id"));
		signMap.put("signType", "MD5");
		String sign_ = WxPayUtil.createSign(signMap, params.get("paternerKey"));
		signMap.put("paySign", sign_);
		return buildServiceResult(true, signMap, "请求支付成功");
	}

	/**
	 * app微信支付
	 * @param params
	 * @return
	 */
	public  ServiceResult pushOrderByApp(Map<String, String> params) {
		Map<String, String> reqMap  = new HashMap<String, String>();
		reqMap.put("appid", params.get("appid"));
		reqMap.put("attach", params.get("attach"));
		reqMap.put("mch_id", params.get("mch_id"));
		reqMap.put("notify_url", params.get("notify_url"));
		reqMap.put("trade_type", "APP");
		reqMap.put("total_fee", params.get("total_fee"));
		reqMap.put("out_trade_no", params.get("out_trade_no"));
		reqMap.put("body", params.get("body"));
		reqMap.put("spbill_create_ip", params.get("spbill_create_ip"));
		reqMap.put("nonce_str", WxPayUtil.getRandom(32));
		String sign = WxPayUtil.createSign(reqMap, params.get("paternerKey"));
		reqMap.put("sign", sign);
		String xmlData =  restTemplate.postForObject(payUrl, WxPayUtil.toXml(reqMap), String.class);
		
		Map<String, String>  resultMap = WxPayUtil.xmlToMap(xmlData);
		ServiceResult serviceResult = parsePayResult(JSONObject.toJSONString(resultMap));
		if(!serviceResult.getStatus()){
			return serviceResult;
		}
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("partnerid", resultMap.get("mch_id"));
		signMap.put("prepayid", resultMap.get("prepay_id"));
		signMap.put("appid", resultMap.get("appid"));
		signMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
		signMap.put("noncestr", resultMap.get("nonce_str"));
		signMap.put("package", "Sign=WXPay");
		String sign_ = WxPayUtil.createSign(signMap, params.get("paternerKey"));
		signMap.put("sign", sign_);
		return buildServiceResult(true, signMap, "请求支付成功");
	}


	public boolean verifyNotifyResult(Map<String, String> parasMap) {
		String returnCode  = parasMap.get("return_code");
		String resultCode =  parasMap.get("result_code");
		String totalFee     = parasMap.get("total_fee");
		String transId      = parasMap.get("transaction_id");
//		String timeEnd      = parasMap.get("time_end");		
		String outOrderId      = parasMap.get("out_trade_no");
		String attr = parasMap.get("attach");
		JSONObject attachObject = JSONObject.parseObject(attr);
		Long orderId = attachObject.getLong("orderId");
		Integer payModeCode = attachObject.getInteger("payMode");
		OrderVo orderInfo = orderService.info(orderId);
		//订单不存在
		if(StringUtils.isEmpty(orderInfo)){
			logger.error("微信回调失败,订单不存在,orderID:{}",orderId);
			return false;
		}	
		
		//订单为支付完成状态，则需再次通知商户方
		if(orderInfo.getStatus().equals(OrderStatus.paySuccess.getCode())){
			logger.error("微信重复回调,订单支付成功状态,再次通知商户方");
			return orderNotifyService.notifyPartner(orderId).getStatus();
		}

		
		//重复回调，不处理
		if(!orderInfo.getStatus().equals(OrderStatus.inPay.getCode())){
			logger.error("微信重复回调，订单非待支付状态,不处理");
			return false;
		}
		
		
		PayModeConfVo payModeConf  = payModeConfService.InfoById(orderInfo.getPartnerId(), orderInfo.getAppId(), payModeCode);
		
		if(ObjectUtils.isEmpty(payModeConf)){
			logger.error("微信回调调整,支付方式不存在：paymode:{}",payModeCode);
			return false;
		}
		
		if(!WxPayUtil.verifyNotify(parasMap, payModeConf.getConfPrivateKey())){
			logger.error("微信回调结果验证不通过,{}",JSONObject.toJSONString(parasMap));
			return false;
		}
		
		//支付失败
		if(!returnCode.equals("SUCCESS") && !resultCode.equals("SUCCESS")){
			logger.error("微信回调失败,{}",JSONObject.toJSONString(parasMap));
			orderLogService.saveOrderLog(orderId, "微信支付通知", "支付失败："+parasMap.get("return_msg"));
			return true;
		}
		//待支付状态
		if(orderInfo.getStatus().equals(OrderStatus.inPay.getCode())){
			ServiceResult serviceResult = orderService.update(OrderStatus.paySuccess,orderId,payModeCode,transId);
			if(!serviceResult.getStatus()){
				logger.error("微信回调，更新订单失败:{}",serviceResult.getMsg());
				return serviceResult.getStatus();
			}
			orderLogService.saveOrderLog(orderId, "微信支付通知", "支付成功");
			orderLogService.saveOrderLog(orderId, "微信支付凭证", JSONObject.toJSONString(parasMap));
			
			JSONObject jsonPriceAndMode = new JSONObject();
			BigDecimal _totalFee = new BigDecimal(totalFee);   
			BigDecimal rate = new BigDecimal("0.01");
			jsonPriceAndMode.put("payTotalPrice", _totalFee.multiply(rate).stripTrailingZeros().toPlainString());
			jsonPriceAndMode.put("payMode", payModeCode);
			jsonPriceAndMode.put("appId", orderInfo.getAppId());
			jsonPriceAndMode.put("partnerId", orderInfo.getPartnerId());
			//统计发送通知
			cacheService.addBasicDataStatistical(jsonPriceAndMode.toJSONString());
			logger.info("[微信支付回调_统计埋点]:{}",jsonPriceAndMode.toJSONString());
			return orderNotifyService.notifyPartner(orderId).getStatus();
		}
		return false;
	}
}
