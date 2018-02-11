package com.yijia360.pay.admin.dao.paybasicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.paystatistics.BasicDataStatistical;
import com.yijia360.pay.entity.vo.paystatistics.BasicDataStatisticalVo;

@Mapper
public interface BasicDataStatisticalDao extends BaseDao<BasicDataStatistical ,BasicDataStatisticalVo> {

	public List<BasicDataStatisticalVo> getList(BasicDataStatistical basic);
	
}
