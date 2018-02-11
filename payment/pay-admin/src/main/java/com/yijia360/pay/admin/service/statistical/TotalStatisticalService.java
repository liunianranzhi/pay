package com.yijia360.pay.admin.service.statistical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.paybasicdata.TotalStatisticalDao;
import com.yijia360.pay.entity.po.paystatistics.TotalStatistical;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.paystatistics.TotalStatisticalVo;

@Service
public class TotalStatisticalService {
	
	@Autowired
	private TotalStatisticalDao totalStatisticalDao;
	
	public PageVo page(TotalStatistical totalStatistical){
		int count = totalStatisticalDao.count(totalStatistical);
		Object result = totalStatisticalDao.list(totalStatistical);
		PageVo page = new PageVo(totalStatistical.getPageNo(), 7, count, result);
		return page;
	}
	
	public boolean save(TotalStatistical totalStatistical){
		boolean result = totalStatisticalDao.save(totalStatistical) == 1 ? true : false;
		return result;
	}
	
	public List<TotalStatisticalVo> getBeforeList(TotalStatistical totalStatistical){
		List<TotalStatisticalVo> list = totalStatisticalDao.getBeforeList(totalStatistical);
		return list;
	}
	
}
