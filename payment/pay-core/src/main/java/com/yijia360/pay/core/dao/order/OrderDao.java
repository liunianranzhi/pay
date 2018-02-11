package com.yijia360.pay.core.dao.order;

import org.springframework.stereotype.Repository;

import com.yijia360.pay.core.dao.BaseDao;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;

@Repository
public interface OrderDao extends BaseDao<Order, OrderVo>{

}
