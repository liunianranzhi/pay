package com.yijia360.pay.core.service.pay.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.cache.CacheService;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.core.service.order.OrderLogService;
import com.yijia360.pay.core.service.order.OrderNotifyService;
import com.yijia360.pay.core.service.order.OrderService;
import com.yijia360.pay.core.service.pay.TradeService;
import com.yijia360.pay.core.service.pay.alipay.util.AliPayCommon;
import com.yijia360.pay.core.service.pay.alipay.util.AlipayNotify;
import com.yijia360.pay.core.service.pay.alipay.util.AlipaySubmit;
import com.yijia360.pay.core.service.pay.payModeConf.PayModeConfService;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

@Service
public class AliPayService extends BaseService implements TradeService{

	
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
	
    @Value("${pay.ali.notify}")
	String payNotify;

	@Override
	public ServiceResult trade(Order order) {
		PayModeConfVo payModeConf = payModeConfService.InfoById(order.getPartnerId(), order.getAppId(), order.getPayMode());
		if(ObjectUtils.isEmpty(payModeConf)){
			logger.error("[请配置支付方式]: payModeCode:{},appId:{},partnerId:{}",order.getPayMode(),order.getAppId(),order.getPartnerId());
			return buildServiceResult(false, "请配置该支付方式");
		}
		
		Map<String, String> parasMap = new HashMap<String,String>();
	//以下为公共参数	
		//公众号ID
		String seller_email = payModeConf.getConfAppId();
		parasMap.put("seller_email", seller_email);
		//商户ID
		String partner =  payModeConf.getConfPartnerId();
		parasMap.put("partner", partner);
		//API私钥
		String key = payModeConf.getConfPrivateKey();
		parasMap.put("key", key);
		//RSAkey
		String resPrivateKey = payModeConf.getConfRsaPrivateKey();
		parasMap.put("privateKey", resPrivateKey);
		//支付宝服务器主动通知商户服务器里指定的页面http/https路径
		parasMap.put("notify_url", payNotify);
	//以下为业务参数
		//商户网站唯一订单号
		parasMap.put("out_trade_no", order.getOutTradeNo());
		//商品的标题/交易标题/订单标题/订单关键字等
		parasMap.put("subject", order.getSubject());
		//对一笔交易的具体描述信息
		parasMap.put("body", order.getDetail());
		//订单总金额，单位为元
		BigDecimal totalFee = new BigDecimal(order.getTotalFee());   
		BigDecimal rate = new BigDecimal("0.01");   
		//去掉尾部0
		parasMap.put("total_fee", totalFee.multiply(rate).stripTrailingZeros().toPlainString());
		//业务扩展参数--支付宝不支持特殊字符，进行转换 payModeEQ1  等价于 payMode=1
		String extra_common_param = "payModeEQ"+order.getPayMode()+"ANDorderIdEQ"+order.getOrderId();
		parasMap.put("extra_common_param", extra_common_param);
		
		if(payModeConf.getPayModeCode().equals(PayMode.alipayPc.getCode()) || payModeConf.getPayModeCode().equals(PayMode.alipayWap.getCode()) || payModeConf.getPayModeCode().equals(PayMode.alipayQr.getCode())){
			parasMap.put("return_url", order.getPaySuccessUrl());
			parasMap.put("error_notify_url", order.getPayErrorUrl());
		}
		
		if(payModeConf.getPayModeCode().equals(PayMode.alipayApp.getCode())){
			if(StringUtils.isEmpty(order.getAppLancher())){
				return buildServiceResult(false, "请检查appLancher");
			}
			parasMap.put("appLancher", order.getAppLancher());
		}
		String payResult = buildPayResult(parasMap,order);
		return buildServiceResult(!StringUtils.isEmpty(payResult),payResult, "");
	}
	
	/**
	 * 构建请求参数
	 * @param parasMap
	 * @param tradeType
	 * @return
	 */
	public String buildPayResult(Map<String, String> parasMap,Order order){
		String payResult = null;
		try {
			Map<String, String> buildParas =new HashMap<String,String>();
			String key = parasMap.get("key");
			if(order.getPayMode().equals(PayMode.alipayPc.getCode())){
				buildParas =buildPc(parasMap);
				payResult = AlipaySubmit.buildRequest(buildParas,"get","确认",key);
			}

			if(order.getPayMode().equals(PayMode.alipayQr.getCode()) || order.getPayMode().equals(PayMode.alipayWap.getCode())) {
				buildParas =buildH5(parasMap);
				payResult = AlipaySubmit.buildRequestUrl(buildParas, key);
			}
			if(order.getPayMode().equals(PayMode.alipayApp.getCode())) {
				String orderInfo = buildApp(parasMap);
				payResult = AlipaySubmit.buildAppResult(orderInfo, parasMap.get("privateKey"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payResult;
	}	
	
	
	public Map<String,String> buildPc(Map<String, String> parasMap){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		//接口名称
		sParaTemp.put("service", "create_direct_pay_by_user");
		//商户ID
        sParaTemp.put("partner", parasMap.get("partner"));
        //编码
        sParaTemp.put("_input_charset", AliPayCommon.aliInputCharset);
        //支付类型，购买
		sParaTemp.put("payment_type", "1");
		//异步回调地址
		sParaTemp.put("notify_url", parasMap.get("notify_url"));
		//同步回调地址
		sParaTemp.put("return_url",parasMap.get("return_url"));
		//错误地址
		sParaTemp.put("error_notify_url", parasMap.get("error_notify_url"));
		//商户账号
		sParaTemp.put("seller_email", parasMap.get("seller_email"));
		//商户订单号
		sParaTemp.put("out_trade_no", parasMap.get("out_trade_no"));
		//标题
		sParaTemp.put("subject",parasMap.get("subject"));
		//金额
		sParaTemp.put("total_fee",parasMap.get("total_fee"));
		//描述
		sParaTemp.put("body",parasMap.get("body"));
		//展示地址
		sParaTemp.put("show_url", "");
		//
		sParaTemp.put("anti_phishing_key", "");
		//外网IP
		sParaTemp.put("exter_invoke_ip", "");
		
		//自定义扩展参数
		sParaTemp.put("extra_common_param", parasMap.get("extra_common_param"));
		
//		String isQrPay = parasMap.get("isQrPay");
//		if(!StringUtils.isEmpty(isQrPay)){
//			sParaTemp.put("qr_pay_mode", "4");
//			String qrWidth = parasMap.get("qrWidth");
//			if(StringUtils.isEmpty(qrWidth)){
//				qrWidth = "200";
//			}
//			sParaTemp.put("qrcode_width", qrWidth);
//		}
		return sParaTemp;
	}
	
	public Map<String,String> buildH5(Map<String, String> parasMap){
		Map<String, String> sParaTemp = new HashMap<String, String>();
		//接口名称
		sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
		//商户ID
        sParaTemp.put("partner", parasMap.get("partner"));
        //编码
        sParaTemp.put("_input_charset", AliPayCommon.aliInputCharset);
        //支付类型，购买
		sParaTemp.put("payment_type", "1");
		//异步回调地址
		sParaTemp.put("notify_url", parasMap.get("notify_url"));
		
//		//同步回调地址
//		sParaTemp.put("return_url",parasMap.get("return_url"));
//		//错误地址
//		sParaTemp.put("error_notify_url", parasMap.get("error_notify_url"));

		//同步回调地址
		sParaTemp.put("return_url",parasMap.get("return_url"));
		//错误地址
		sParaTemp.put("error_notify_url", parasMap.get("error_notify_url"));
		
		//商户账号
		sParaTemp.put("seller_id", parasMap.get("seller_email"));
		//商户订单号
		sParaTemp.put("out_trade_no", parasMap.get("out_trade_no"));
		//标题
		sParaTemp.put("subject",parasMap.get("subject"));
		//金额
		sParaTemp.put("total_fee",parasMap.get("total_fee"));
		//描述
		sParaTemp.put("body",parasMap.get("body"));
		//展示地址
		sParaTemp.put("show_url", "");
		//
		sParaTemp.put("anti_phishing_key", "");
		//外网IP
		sParaTemp.put("exter_invoke_ip", "");
		sParaTemp.put("app_pay", "Y");
		//自定义扩展参数
		sParaTemp.put("extra_common_param", parasMap.get("extra_common_param"));

//		if(parasMap.get("isQrPay").equals("1")){
//			sParaTemp.put("qr_pay_mode", "4");
//			sParaTemp.put("qrcode_width", parasMap.get("qrcode_width"));
//		}
		return sParaTemp;
	}
	
	public String buildApp(Map<String,String> parasMap){
		StringBuilder builder = new StringBuilder();

		// 合作者身份ID
		builder.append("partner=\"");
		builder.append(parasMap.get("partner"));
		builder.append("\"");

		// 卖家支付宝账号
		builder.append("&seller_id=\"");
		builder.append(parasMap.get("seller_email"));
		builder.append("\"");
		
		// 商户网站唯一订单号
		builder.append("&out_trade_no=\"");
		builder.append(parasMap.get("out_trade_no"));
		builder.append("\"");

		// 商品名称
		builder.append("&subject=\"");
		builder.append(parasMap.get("subject"));
		builder.append("\"");
		
		// 商品详情
		builder.append("&body=\"");
		builder.append(parasMap.get("subject"));
		builder.append("\"");

		// 商品金额
		builder.append("&total_fee=\"");
		builder.append(parasMap.get("total_fee"));
		builder.append("\"");

		// 服务器异步通知页面路径
		builder.append("&notify_url=\"");
		builder.append(parasMap.get("notify_url"));
		builder.append("\"");

		
		//自定义扩展参数
		builder.append("&extra_common_param=\"");
		builder.append(parasMap.get("extra_common_param"));
		builder.append("\"");
		
		
		// 接口名称， 固定值
		builder.append("&service=\"mobile.securitypay.pay\"");
		// 支付类型， 固定值
		builder.append("&payment_type=\"1\"");
		// 参数编码， 固定值
		builder.append("&_input_charset=\"utf-8\"");

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		builder.append("&it_b_pay=\"1d\"");

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		builder.append("&return_url=\"");
		builder.append(parasMap.get("appLancher"));
		builder.append("\"");
		
		builder.append("&app_pay=\"Y\"");
		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";
		return builder.toString();
	}

	public boolean verifyNotifyResult(Map<String, String[]> requestParams) {
		Map<String,String> params = new HashMap<String,String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		String  extraCommonParam = params.get("extra_common_param");
		String tradeStatus = params.get("trade_status");
		String outOrderId = params.get("out_trade_no");
		String tradeNo = params.get("trade_no");
		
		String[] extras = extraCommonParam.split("AND");
		Integer payMode = Integer.valueOf(extras[0].split("EQ")[1]);
		Long orderId = Long.valueOf(extras[1].split("EQ")[1]);
		
		OrderVo orderInfo = orderService.info(orderId);
		
		//订单不存在
		if(StringUtils.isEmpty(orderInfo)){
			logger.error("支付宝回调失败,订单不存在,orderID:{}",orderId);
			return false;
		}	

		//订单为支付完成状态，则需再次通知商户方
		if(orderInfo.getStatus().equals(OrderStatus.paySuccess.getCode())){
			logger.error("支付宝重复回调,订单非待支付状态,不处理");
			return orderNotifyService.notifyPartner(orderId).getStatus();
		}

		
		//重复回调，不处理
		if(!orderInfo.getStatus().equals(OrderStatus.inPay.getCode())){
			logger.error("支付宝重复回调,订单非待支付状态,不处理");
			return false;
		}
		
		
		PayModeConfVo payModeConf  = payModeConfService.InfoById(orderInfo.getPartnerId(), orderInfo.getAppId(),payMode);
		
		if(ObjectUtils.isEmpty(payModeConf)){
			logger.error("支付宝回调调整,支付方式不存在：paymode:{}",payMode);
			return false;
		}
		
		if(!AlipayNotify.verify(params, payModeConf.getConfPrivateKey(), payModeConf.getConfPartnerId())){
			logger.error("支付宝回调结果验证不通过,{}",JSONObject.toJSONString(requestParams));
			return false;
		}
		
		if(!tradeStatus.equals("TRADE_SUCCESS")){
			logger.error("支付失败,{}",JSONObject.toJSONString(params));
			orderLogService.saveOrderLog(orderId, "微信支付通知", "支付失败："+params.get("return_msg"));
			return true;
		}
		//待支付状态
		if(orderInfo.getStatus().equals(OrderStatus.inPay.getCode())){
			ServiceResult serviceResult = orderService.update(OrderStatus.paySuccess,orderId,payMode,tradeNo);
			if(!serviceResult.getStatus()){
				logger.error("支付宝回调，更新订单失败:{}",serviceResult.getMsg());
			}
			
			//统计发送通知
			orderLogService.saveOrderLog(orderId, "支付宝支付通知", "支付成功");
			orderLogService.saveOrderLog(orderId, "支付宝支付凭证", JSONObject.toJSONString(params));
			JSONObject jsonPriceAndMode = new JSONObject();
			jsonPriceAndMode.put("payTotalPrice", orderInfo.formatTotalFee());
			jsonPriceAndMode.put("payMode", payMode);
			jsonPriceAndMode.put("appId", orderInfo.getAppId());
			jsonPriceAndMode.put("partnerId", orderInfo.getPartnerId());
			cacheService.addBasicDataStatistical(jsonPriceAndMode.toJSONString());
			logger.info("[微信支付回调_统计埋点]:{}",jsonPriceAndMode.toJSONString());
			//首次通知商户方
			return orderNotifyService.notifyPartner(orderId).getStatus();
		}
		return false;
	}
}