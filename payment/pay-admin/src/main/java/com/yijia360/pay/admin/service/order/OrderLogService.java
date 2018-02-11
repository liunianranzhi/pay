package com.yijia360.pay.admin.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.order.OrderLogDao;
import com.yijia360.pay.entity.po.order.OrderLog;
import com.yijia360.pay.entity.vo.PageVo;

@Service
public class OrderLogService {

	@Autowired
	private OrderLogDao orderLogDao;
	
	public PageVo page(OrderLog orderLog){
		int count = orderLogDao.count(orderLog);
		Object result = orderLogDao.list(orderLog);
		PageVo page = new PageVo(orderLog.getPageNo(), orderLog.getPageSize(), count, result);
		return page;
				
	}
	
}
