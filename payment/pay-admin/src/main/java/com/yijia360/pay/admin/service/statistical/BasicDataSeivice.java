package com.yijia360.pay.admin.service.statistical;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yijia360.pay.admin.dao.paybasicdata.BasicDataStatisticalDao;
import com.yijia360.pay.entity.po.paystatistics.BasicDataStatistical;
import com.yijia360.pay.entity.vo.paystatistics.BasicDataStatisticalVo;

@Service
public class BasicDataSeivice {

	@Autowired
	private BasicDataStatisticalDao basicDataDao; 
	
	//保存统计基础数据
	public boolean save(BasicDataStatistical basic){
		boolean result;
		result = basicDataDao.save(basic) == 1 ? true : false;
		return result;
	}
	
	//取出基本数据
	public List<BasicDataStatisticalVo> getList(BasicDataStatistical basic){
		return basicDataDao.getList(basic);
	}
	
}
