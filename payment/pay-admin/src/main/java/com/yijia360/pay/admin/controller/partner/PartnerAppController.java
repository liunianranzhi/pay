package com.yijia360.pay.admin.controller.partner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yijia360.pay.admin.service.partner.PartnerAppService;
import com.yijia360.pay.admin.service.partner.PartnerService;
import com.yijia360.pay.admin.service.partner.PayModeConfService;
import com.yijia360.pay.entity.enums.PartnerAppIsIpLimit;
import com.yijia360.pay.entity.enums.PartnerState;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.po.partner.PartnerApp;
import com.yijia360.pay.entity.po.partner.PayModeConf;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;
import com.yijia360.pay.entity.vo.partner.PartnerVo;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;
import com.yijia360.pay.utils.PartnerAppUtils;

@Controller
@RequestMapping("/partnerApp")
public class PartnerAppController {

	@Autowired
	private PartnerAppService partnerAppService;
	
	@Autowired
	private PartnerService partnerService;
	
	@Autowired
	private PayModeConfService payModeConfService;
	
	@RequestMapping
	public String index(ModelMap map,PartnerApp partnerApp){
		map.addAttribute("partnerAppIsIpLimits",PartnerAppIsIpLimit.values());
		PageVo page = partnerAppService.page(partnerApp);
		map.addAttribute("partnerAppPage",page);
		map.addAttribute("partnerApp",partnerApp);
		return "partnerApp/index";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(ModelMap map){
		map.addAttribute("partnerAppIsIpLimits",PartnerAppIsIpLimit.values());
		return "partnerApp/add";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> add(PartnerApp partnerApp){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long appId = PartnerAppUtils.getAppId();
		String salt = PartnerAppUtils.getSalt();
		String appSecret = PartnerAppUtils.getAppSecret(salt, String.valueOf(appId));
		partnerApp.setAppId(appId);
		partnerApp.setSalt(salt);
		partnerApp.setAppSecret(appSecret);
		boolean result = partnerAppService.save(partnerApp);
		if(result){
			resultMap.put("msg", "添加成功");
		}else{
			resultMap.put("msg", "添加失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(PartnerApp partnerApp,ModelMap map){
		PartnerAppVo partnerAppVo = partnerAppService.info(partnerApp);
		Partner partner = new Partner();
		partner.setPartnerId(partnerAppVo.getPartnerId());
		PartnerVo partnerVo = partnerService.info(partner);
		map.addAttribute("partner",partnerVo);
		map.addAttribute("partnerApp",partnerAppVo);
		map.addAttribute("partnerAppIsIpLimits",PartnerAppIsIpLimit.values());
		return "partnerApp/edit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(PartnerApp partnerApp){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		result = partnerAppService.update(partnerApp);
		if(result){
			resultMap.put("msg", "修改成功");
		}else{
			resultMap.put("msg", "修改失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
	@RequestMapping("/partners")
	public String partners(ModelMap map,Partner partner){
		PageVo page = partnerService.page(partner);
		map.addAttribute("states",PartnerState.values());
		map.addAttribute("partnerPage",page);
		map.addAttribute("partner",partner);
		return "partnerApp/partners";
	}
	
	@RequestMapping("/showInfo")
	public String showInfo(ModelMap map, PartnerApp partnerApp){
		PartnerAppVo partnerAppVo = partnerAppService.info(partnerApp);
		map.addAttribute("partnerApp",partnerAppVo);
		return "partnerApp/showInfo";
	}
	
	@RequestMapping("/resetSecret")
	@ResponseBody
	public Map<String, Object> resetSecret(PartnerApp partnerApp){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long appId = partnerApp.getAppId();
		String salt = PartnerAppUtils.getSalt();
		String appSecret = PartnerAppUtils.getAppSecret(salt, String.valueOf(appId));
		boolean result = false;
		partnerApp.setSalt(salt);
		partnerApp.setAppSecret(appSecret);
		result = partnerAppService.update(partnerApp);
		if(result){
			resultMap.put("msg", "重置成功");
		}else{
			resultMap.put("msg", "重置失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
	@RequestMapping("/toSetPayMode")
	public String toSetPayMode(ModelMap map, PartnerApp partnerApp){
		PartnerAppVo partnerAppVo = partnerAppService.info(partnerApp);
		map.addAttribute("partnerApp",partnerAppVo);
		map.addAttribute("payModes",PayMode.values());
		return "partnerApp/setPayMode";
	}
	
	@RequestMapping("/setPayMode")
	@ResponseBody
	public Map<String, Object> setPayMode(PayModeConf payModeConf){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		PayModeConfVo payModeConfVo = payModeConfService.info(payModeConf);
		if(!StringUtils.isEmpty(payModeConfVo)){
			result = payModeConfService.update(payModeConf);
		}else{
			result = payModeConfService.save(payModeConf);
		}
		if(result){
			resultMap.put("msg", "设置成功");
		}else{
			resultMap.put("msg", "设置失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
	@RequestMapping("/getPayMode")
	@ResponseBody
	public PayModeConfVo getPayMode(PayModeConf payModeConf){
		PayModeConfVo payModeConfVo = payModeConfService.info(payModeConf);
		if(!StringUtils.isEmpty(payModeConfVo)){
			return payModeConfVo;
		}else{
			return null;
		}
	}
	
}
