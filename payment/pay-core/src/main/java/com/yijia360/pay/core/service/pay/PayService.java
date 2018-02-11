package com.yijia360.pay.core.service.pay;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.SpringContextUtil;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月30日下午1:27:39
 */
@Service
public class PayService extends BaseService{

	/**
	 * 发起支付
	 * @param order
	 * @return
	 */
	public ServiceResult pay(Order order) {
		
		//获取支付方式
		String serivceName = PayMode.getPayService(order.getPayMode());
		
		if(StringUtils.isEmpty(serivceName)){
			logger.error("[支付方式不存在]:orderId {},outTradeNo:{},payMode:{}",order.getOrderId(),order.getOutTradeNo(),order.getPayMode());
			return  buildServiceResult(false, "支付方式不存在");
		}
		
		TradeService tradeService = (TradeService) SpringContextUtil.getBean(serivceName);
		
		return tradeService.trade(order);
	}
}
