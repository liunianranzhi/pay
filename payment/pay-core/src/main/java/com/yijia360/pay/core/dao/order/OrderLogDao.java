package com.yijia360.pay.core.dao.order;

import org.springframework.stereotype.Repository;

import com.yijia360.pay.core.dao.BaseDao;
import com.yijia360.pay.entity.po.order.OrderLog;
import com.yijia360.pay.entity.vo.order.OrderLogVo;

@Repository
public interface OrderLogDao extends BaseDao<OrderLog, OrderLogVo>{

}
