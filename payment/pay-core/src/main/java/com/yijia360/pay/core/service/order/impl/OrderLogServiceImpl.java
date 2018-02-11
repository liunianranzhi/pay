package com.yijia360.pay.core.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.core.dao.order.OrderLogDao;
import com.yijia360.pay.core.service.order.OrderLogService;
import com.yijia360.pay.entity.po.order.OrderLog;

@Service
public class OrderLogServiceImpl implements OrderLogService{

	@Autowired
	private OrderLogDao OrderLogDao;
	
	@Override
	public boolean saveOrderLog(Long orderId, String opName, String opMsg) {
		OrderLog OrderLog = new OrderLog();
		OrderLog.setOrderId(orderId);
		OrderLog.setOpName(opName);
		OrderLog.setOpMsg(opMsg);
		boolean result = OrderLogDao.save(OrderLog) == 1 ? true : false;
		return result;
	}

}
