package com.yijia360.pay.admin.controller.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.admin.service.order.OrderLogService;
import com.yijia360.pay.admin.service.order.OrderNotifyService;
import com.yijia360.pay.admin.service.order.OrderService;
import com.yijia360.pay.admin.service.partner.PartnerAppService;
import com.yijia360.pay.admin.service.partner.PartnerService;
import com.yijia360.pay.entity.enums.OrderNotifyStatus;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.po.order.OrderLog;
import com.yijia360.pay.entity.po.order.OrderNotify;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.po.partner.PartnerApp;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;
import com.yijia360.pay.entity.vo.partner.PartnerVo;
import com.yijia360.pay.utils.excel.ExcelUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired 
	private OrderLogService orderLogService;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private OrderNotifyService orderNotifyService;
	
	@Autowired
	private PartnerAppService partnerAppService;
	
	@RequestMapping
	public String index(ModelMap map, Order order){
		PageVo page = orderService.page(order);
		Partner partner = new Partner();
		List<PartnerVo> partners = partnerService.getList(partner);
		Long appId = order.getAppId();
		PartnerApp partnerApp = new PartnerApp();
		if(!StringUtils.isEmpty(appId)){
			partnerApp.setAppId(appId);
			PartnerAppVo partnerAppVo = partnerAppService.info(partnerApp);
			if(!StringUtils.isEmpty(partnerAppVo)){
				map.addAttribute("partnerApp",partnerAppVo);
			}
		}
		map.addAttribute("partners",partners);
		map.addAttribute("orderStatus",OrderStatus.values());
		map.addAttribute("payModes",PayMode.values());
		map.addAttribute("order",order);
		map.addAttribute("orderPage",page);
		return "order/index";
	}
	
	@RequestMapping("/getOrderLog")
	public String getOrderLog(ModelMap map,OrderLog orderLog){
		PageVo page = orderLogService.page(orderLog);
		map.addAttribute("orderLogPage",page);
		return "order/orderLog";
	}
	
	@RequestMapping("/getOrderNotify")
	public String getOrderNotify(ModelMap map, OrderNotify orderNotify){
		PageVo page = orderNotifyService.page(orderNotify);
		map.addAttribute("orderNotifyPage",page);
		map.addAttribute("orderNotifyStatus",OrderNotifyStatus.values());
		return "order/orderNotify";
	}
	
	@RequestMapping("/getPartnerApp")
	@ResponseBody
	public Map<String, Object> getPartnerApp(PartnerApp partnerApp){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(partnerApp.getPartnerId())){
			List<PartnerAppVo> partnerApps = partnerAppService.getList(partnerApp);
			if(partnerApps.size() != 0){
				resultMap.put("status", true);
				resultMap.put("partnerApps", partnerApps);
			}else{
				resultMap.put("status", false);
			}
		}
		return resultMap;
	}
	
	@RequestMapping("/export")
	@ResponseBody
	public void export(Order order,HttpServletResponse response){
		
	    String[] headers = {"订单编号","商品主题","商品描述","商品价格","商户订单号","创建时间","支付状态","支付方式","支付备注","支付平台流水号"};
	    String[] keys = {"orderId","subject","detail","_totalFee","outTradeNo","cTime","statusMsg","payModeMsg","payInfo","tradeNo"};
	    
		List<JSONObject> exportList = orderService.exportList(order);
		try {
		    if(exportList.size() == 0){
		    	response.setHeader("contentType", "text/html; charset=gbk");
		    	response.setCharacterEncoding("gbk");
				PrintWriter writer = response.getWriter();
		    	StringBuilder html = new StringBuilder();
		    	html.append("<script src='/pay-admin/layui/layui.js'></script>");
		    	html.append("<script>");
		    	html.append("layui.use([");
		    	html.append("'layer'");
		    	html.append("], function() {var layer = layui.layer;");
		    	html.append("");
		    	html.append("layer.confirm('暂无数据，点击返回', {btn: ['确认','返回']}, function(){history.go(-1);}, function(){history.go(-1);});");
		    	html.append("})");
		    	html.append("</script>");
		    	writer.write(html.toString());
		    	writer.flush();
		    	writer.close();
		    	return;
		    }else{
		    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			    String time = format.format(new Date());
		    	response.setHeader("content-Type", "application/vnd.ms-excel");
		    	// 下载文件的默认名称
		    	response.setHeader("Content-Disposition", "attachment;filename="+time+"export.xls");
		    	ServletOutputStream out = response.getOutputStream();
			    ExcelUtil.exportExcel(headers, keys, exportList, out);
			    out.flush();
			    out.close();
			    return;
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
