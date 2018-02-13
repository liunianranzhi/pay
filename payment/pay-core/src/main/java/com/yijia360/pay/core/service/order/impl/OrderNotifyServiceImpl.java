package com.yijia360.pay.core.service.order.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.core.service.order.OrderLogService;
import com.yijia360.pay.core.service.order.OrderNotifyService;
import com.yijia360.pay.core.service.order.OrderService;
import com.yijia360.pay.core.service.partner.PartnerAppService;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.vo.order.OrderVo;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;
import com.yijia360.pay.utils.PayUtils;

@Service
public class OrderNotifyServiceImpl extends BaseService implements OrderNotifyService{

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderLogService orderLogService;
	
	@Autowired
	private PartnerAppService partnerAppService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ServiceResult notifyPartner(Long orderId) {
		
		OrderVo orderVo = orderService.info(orderId);
		//支付成功 状态
		if(orderVo.getStatus().equals(OrderStatus.paySuccess.getCode())){
			
			PartnerAppVo partnerAppVo = partnerAppService.infoById(orderVo.getPartnerId(), orderVo.getAppId());
			String appSecret = partnerAppVo.getAppSecret();
			
			orderVo.formatTotalFee();
			Map<String,String> signMap = new HashMap<String,String>();
			signMap.put("outTradeNo", orderVo.getOutTradeNo());
			signMap.put("totalFee", orderVo.getTotalFee().toString());
			signMap.put("tradeNo", orderVo.getTradeNo());
			signMap.put("code", "200");
			signMap.put("msg", OrderStatus.paySuccess.getMessage());
			signMap.put("attr", orderVo.getAttr());
			String sign  = PayUtils.createSign(signMap, appSecret);
			try {
				MultiValueMap<String, String> notifyMap= new LinkedMultiValueMap<String, String>();
				notifyMap.add("outTradeNo", orderVo.getOutTradeNo());
				notifyMap.add("totalFee", orderVo.getTotalFee().toString());
				notifyMap.add("tradeNo", orderVo.getTradeNo());
				notifyMap.add("code", "200");
				notifyMap.add("msg", OrderStatus.paySuccess.getMessage());
				notifyMap.add("attr", orderVo.getAttr());
				notifyMap.add("sign", sign);

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(notifyMap, headers);
				ResponseEntity<String> response = restTemplate.postForEntity(orderVo.getNotifyUrl(), request , String.class );
				String notifyResult = response.getBody();
				if(StringUtils.isEmpty(notifyResult) && response.getStatusCodeValue() == HttpStatus.OK.value()){
					logger.error("支付消息通知商户_失败_应答为空,orderId:{},notifyResult:{}",orderId,notifyResult);
					orderLogService.saveOrderLog(orderId, "支付系统", "支付消息_通知商户_失败");
					return buildServiceResult(false, "支付消息通知商户_失败: 应答为空");
				}
				if(notifyResult.equals("SUCCESS")){
					logger.info("支付消息通知商户_成功,orderId:{},notifyResult:{}",orderId,notifyResult);
					orderLogService.saveOrderLog(orderId, "支付系统", "支付消息_通知商户_成功");
					return buildServiceResult(true, "支付消息通知商户_成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("支付消息通知商户_失败,orderId:{},exception:{}",orderId,e.getMessage());
				orderLogService.saveOrderLog(orderId, "支付系统", "支付消息_通知商户_失败");
				return buildServiceResult(false, "支付消息通知商户_失败:"+e.getMessage());
			}
		}
		logger.error("支付消息通知商户_失败:订单非支付成功状态,orderId:{}",orderId);
		return  buildServiceResult(false, "支付消息通知商户_失败:订单非支付成功状态");
	}
}
