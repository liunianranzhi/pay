package com.yijia360.pay.admin.controller.paystatistical;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yijia360.pay.admin.service.statistical.BasicStatisticalSeivice;
import com.yijia360.pay.admin.service.statistical.TotalStatisticalService;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.paystatistics.EveryDayTotalStatistical;
import com.yijia360.pay.entity.po.paystatistics.TotalStatistical;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.paystatistics.EveryDayTotalStatisticalVo;
import com.yijia360.pay.entity.vo.paystatistics.TotalStatisticalVo;
import com.yijia360.pay.utils.DateUtils;


@Controller
@RequestMapping("/statistical")
public class BasicStatisticalController {
	
	@Autowired
	private BasicStatisticalSeivice basicStatisticalSeivice; 
	
	@Autowired
	private TotalStatisticalService totalStatisticalSercive;
	
	@RequestMapping
	public String index(ModelMap map, TotalStatistical totalStatistical){
		
		//取出每天各渠道总金额和总笔数
		EveryDayTotalStatistical statistical = new EveryDayTotalStatistical();
		List<EveryDayTotalStatisticalVo> listvo = basicStatisticalSeivice.getList(statistical);
		
		//累计统计分页数据
		PageVo totalDataPage = totalStatisticalSercive.page(totalStatistical);
		
		BigDecimal dailyPayPrice = new BigDecimal(0) ;
		Integer dailyCount = 0;
		if(!CollectionUtils.isEmpty(listvo)){
			for (int i = 0; i < listvo.size(); i++) {
				if(!ObjectUtils.isEmpty(listvo.get(i).getChannelTotalPrice())){
					dailyPayPrice = dailyPayPrice.add(listvo.get(i).getChannelTotalPrice());
					dailyCount = dailyCount+listvo.get(i).getChannelTotalCount() ;
				}
			}
		}
		totalStatistical.setcTime(DateUtils.getDateToInt(new Date()));
		List<TotalStatisticalVo> totalDatas = totalStatisticalSercive.getBeforeList(totalStatistical);
		//累计统计总量和总额计算
		BigDecimal payTotalPrice = new BigDecimal(0);
		Integer totalCount = 0;
		if(!CollectionUtils.isEmpty(totalDatas)){
			for (TotalStatisticalVo totalStatisticalVo : totalDatas) {
				payTotalPrice = payTotalPrice.add(totalStatisticalVo.getHistoryTotalPrice());
				totalCount += totalStatisticalVo.getHistoryTotalCount();
			}
		}
		
		map.addAttribute("totalDataPage",totalDataPage);
		map.addAttribute("dailyCount",dailyCount);
		map.addAttribute("dailyPayPrice",dailyPayPrice);
		map.addAttribute("listvo",listvo);
		map.addAttribute("payTotalPrice",payTotalPrice);
		map.addAttribute("totalCount",totalCount);
		map.addAttribute("payModes",PayMode.values());
		map.addAttribute("uTime",listvo.get(0).getuTime());
		
		return "statistical/index";
	}
	
	


}
