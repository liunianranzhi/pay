package com.yijia360.pay.api.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.order.OrderService;
import com.yijia360.pay.core.service.pay.PayService;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;
import com.yijia360.pay.utils.CommonUtils;
import com.yijia360.pay.utils.PayUtils;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午11:25:29
 */
@RequestMapping(PayApi.Order)
@RestController
public class OrderAction extends BaseAction{
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PayService payService;
	
	/**
	 * 订单详情
	 * @param request
	 * @return
	 */
	@PostMapping("/info")
	public Result info(HttpServletRequest request){
		String _outTradeNo = request.getParameter("outTradeNo");
		if(!CommonUtils.isNumberOrLetter(_outTradeNo)){
			return buildErrorResult("请检查订单号格式");
		}
		Integer partnerId = Integer.valueOf(request.getParameter("partnerId"));
		Long appId = Long.valueOf(request.getParameter("appId"));
		OrderVo orderInfo = orderService.info(_outTradeNo, appId, partnerId);
		if(ObjectUtils.isEmpty(orderInfo)){
			return buildErrorResult("订单不存在");
		}
		
		JSONObject orderInfoJson = JSONObject.parseObject(JSONObject.toJSONString(orderInfo));
		orderInfoJson.put("totalFee", orderInfo.formatTotalFee());
		Result result =new Result();
		result.setCode(code_success);
		result.setResult(orderInfoJson);
		return result;
	}
	
	/**
	 * 支付订单
	 * @param request
	 * @return
	 */
	@PostMapping("/pay")
	public Result pay(HttpServletRequest request){
		
		String payMode = request.getParameter("payMode");
		
		if(StringUtils.isEmpty(payMode) || !CommonUtils.isNumber(payMode)){
			return buildErrorResult("请检查支付方式");
		}
		Integer _payMode = Integer.valueOf(payMode);
		
		if(ObjectUtils.isEmpty(PayMode.getInfo(_payMode))){
			return buildErrorResult("请检查支付方式");
		}
		
		String _outTradeNo = request.getParameter("outTradeNo");
		if(!CommonUtils.isNumberOrLetter(_outTradeNo)){
			return buildErrorResult("请检查订单号格式");
		}
		
		String _notifyUrl = request.getParameter("notifyUrl");
		if(!CommonUtils.isUriNotParas(_notifyUrl)){
			return buildErrorResult("请检查回调地址格式");
		}
		
		String _totalFee = request.getParameter("totalFee");
		if(!CommonUtils.isNumber(_totalFee)){
			return buildErrorResult("请检查订单总金额");
		}
		
		if(Integer.valueOf(_totalFee) <= 0){
			return buildErrorResult("请检查订单总金额");
		}
		
		String _subject = request.getParameter("subject");
		if(StringUtils.isEmpty(_subject)){
			return buildErrorResult("请检查支付标题");
		}
		Integer partnerId = Integer.valueOf(request.getParameter("partnerId"));
		Long appId = Long.valueOf(request.getParameter("appId"));
		
		Order order = new Order();
		String _detail = request.getParameter("detail");
		if(StringUtils.isEmpty(_detail)){
			return buildErrorResult("请检查支付描述");
		}
		order.setDetail(_detail);
		
		String _attr = request.getParameter("attr");
		order.setAttr(StringUtils.isEmpty(_attr)?"":_detail);
		
		String _qrPay = request.getParameter("qrPay");
		String _width = request.getParameter("width");
		String _height = request.getParameter("height");
		if(!StringUtils.isEmpty(_qrPay)){
			if(CommonUtils.isNumber(_qrPay) && _qrPay.equals(1)){
				 if(!CommonUtils.isNumber(_width)){
					 _width = "200";
				 }
				 if(!CommonUtils.isNumber(_height)){
					 _height = "200";
				 }
			}
		}
		
		String wxOpenId = request.getParameter("wxOpenId");
		if(!StringUtils.isEmpty(wxOpenId)){
			order.setWxOpenId(wxOpenId);
		}

		String appLancher = request.getParameter("appLancher");
		String paySuccessUrl = request.getParameter("paySuccessUrl");
		String payErrorUrl = request.getParameter("payErrorUrl");
		
		order.setAppLancher(appLancher);
		order.setPaySuccessUrl(paySuccessUrl);
		order.setPayErrorUrl(payErrorUrl);
		
		order.setSubject(_subject);
		order.setTotalFee(Integer.valueOf(_totalFee));
		order.setNotifyUrl(_notifyUrl);
		order.setOutTradeNo(_outTradeNo);
		order.setPayMode(_payMode);
		order.setAppId(appId);
		order.setPartnerId(partnerId);
		order.setCreateIp(getRemoteIp(request));
		OrderVo orderVo = orderService.info(_outTradeNo,appId,partnerId);
		if(ObjectUtils.isEmpty(orderVo)){
			logger.info("[商户下单:{}]",JSONObject.toJSONString(order));
			order.setStatus(OrderStatus.inPay.getCode());
			order.setStatusMsg(OrderStatus.inPay.getMessage());
			order.setOrderId(PayUtils.createOrderId());
			ServiceResult createOrderServiceResult =  orderService.creatOrder(order);
			if(!createOrderServiceResult.getStatus()){
				return buildErrorResult(createOrderServiceResult.getMsg());
			}
		}
		if(!ObjectUtils.isEmpty(orderVo)){
			if(!orderVo.getTotalFee().equals(Integer.valueOf(_totalFee))){
				return buildErrorResult("订单已存在,金额不符");
			}
			if(!orderVo.getPayMode().equals(_payMode)){
				orderVo.setPayMode(_payMode);
			}
			BeanUtils.copyProperties(orderVo, order);
		}
		
		//支付
		ServiceResult payServiceResult = payService.pay(order);
		
		if(!payServiceResult.getStatus()){
			return buildErrorResult(payServiceResult.getMsg());
		}
		
		return buildResult(payServiceResult);
	}
}