package com.yijia360.pay.api.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.order.OrderNotifyService;
import com.yijia360.pay.utils.PayUtils;

@RequestMapping(PayApi.PayTestNotify)
@RestController
public class TestNotifyAction extends BaseAction{
	
	@Autowired
	private OrderNotifyService orderNotifyService;
	
	/**
	 * 通知商户
	 * @param request
	 * @return
	 */
	@RequestMapping("/notifyPartner")
	public ServiceResult notifyPartner(HttpServletRequest request){
		Long orderId = Long.valueOf(request.getParameter("orderId"));
		return orderNotifyService.notifyPartner(orderId);
	}
	
	/**
	 * 支付消息回调
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/notify")
	public String payNotify(HttpServletRequest request,HttpServletResponse response){
		
		String tradeNo = request.getParameter("tradeNo");
		
		String outTradeNo = request.getParameter("outTradeNo");
		
		String code = request.getParameter("code");
		
		String totalFee = request.getParameter("totalFee");
		
		String msg = request.getParameter("msg");
		
		String sign = request.getParameter("sign");
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("outTradeNo", outTradeNo);
		map.put("code", code);
		map.put("totalFee", totalFee);
		map.put("msg", msg);
		map.put("tradeNo", tradeNo);
		map.put("sign", sign);
		logger.info(JSONObject.toJSONString(map));
		//验证通过
		if(code.equals("200") && PayUtils.createSign(map, "ed0410112e77f269dabeeebb34dfc8e1").equals(sign)){
			//处理订单状态
			return "SUCCESS";
		}
		return "FAIL";
	}
}