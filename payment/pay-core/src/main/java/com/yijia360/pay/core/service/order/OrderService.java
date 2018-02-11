package com.yijia360.pay.core.service.order;

import org.springframework.stereotype.Service;

import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;

@Service
public interface OrderService {

	/**
	 * 创建商户订单
	 * @return
	 */
	ServiceResult creatOrder(Order order);
	
	/**
	 * 查询商户订单
	 * @param order
	 * @return
	 */
	OrderVo info(String outTradeNo,Long appId,Integer partnerId);
	
	OrderVo info(Long orderId);
	
	/**
	 * 更新订单状态
	 * @return
	 */
	ServiceResult update(OrderStatus order,Long orderId,Integer payMode,String tradeNo);
	
}
