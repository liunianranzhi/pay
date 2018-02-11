package com.yijia360.pay.admin.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.order.OrderNotifyDao;
import com.yijia360.pay.entity.po.order.OrderNotify;
import com.yijia360.pay.entity.vo.PageVo;

@Service
public class OrderNotifyService {

	@Autowired
	private OrderNotifyDao orderNotifyDao;
	
	public PageVo page(OrderNotify orderNotify){
		int count = orderNotifyDao.count(orderNotify);
		Object result = orderNotifyDao.list(orderNotify);
		PageVo page = new PageVo(orderNotify.getPageNo(), orderNotify.getPageSize(), count, result);
		return page;
	}
	
}
