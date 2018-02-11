package com.yijia360.pay.admin.service.statistical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.paybasicdata.EveryDayTotalStatisticalDao;
import com.yijia360.pay.entity.po.paystatistics.EveryDayTotalStatistical;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.paystatistics.EveryDayTotalStatisticalVo;

@Service
public class BasicStatisticalSeivice {
	
	@Autowired
	private EveryDayTotalStatisticalDao statisticalDao; 
	
	public PageVo page(EveryDayTotalStatistical statistical){
		int count = statisticalDao.count(statistical);
		Object result = statisticalDao.list(statistical);
		PageVo page = new PageVo(statistical.getPageNo(), statistical.getPageSize(), count, result);
		return page;
	}
	
	public List<EveryDayTotalStatisticalVo> getList(EveryDayTotalStatistical statistical){
		return statisticalDao.getList(statistical);
	}
	
	public boolean update(EveryDayTotalStatistical statistical){
		boolean result;
		result = statisticalDao.update(statistical) == 1 ? true : false;
		return result;
	}
	
	public void updateAlluTime(EveryDayTotalStatistical statistical){
		statisticalDao.updateAlluTime(statistical);
	}
	
}
