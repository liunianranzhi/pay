package com.yijia360.pay.admin.controller.partner;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yijia360.pay.entity.enums.PartnerState;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.partner.PartnerVo;
import com.yijia360.pay.admin.service.partner.PartnerService;

@Controller
@RequestMapping("/partner")
public class PartnerController {
	
	@Autowired
	private PartnerService partnerService;
	
	@RequestMapping
	public String index(ModelMap map,Partner partner){
		PageVo page = partnerService.page(partner);
		map.addAttribute("states",PartnerState.values());
		map.addAttribute("partnerPage",page);
		map.addAttribute("partner",partner);
		return "partner/index";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(ModelMap map){
		map.addAttribute("states",PartnerState.values());
		return "partner/add";
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Partner partner){
		Map<String,Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		result = partnerService.save(partner);
		if(result){
			resultMap.put("msg", "添加成功");
		}else{
			resultMap.put("msg", "添加失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
	@RequestMapping("/toEdit")
	public String toEdit(ModelMap map, Partner partner){
		PartnerVo partnerVo = partnerService.info(partner);
		map.addAttribute("partner",partnerVo);
		map.addAttribute("states",PartnerState.values());
		return "partner/edit";
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(ModelMap map, Partner partner){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = false;
		result = partnerService.update(partner);
		if(result){
			resultMap.put("msg", "修改成功");
		}else{
			resultMap.put("msg", "修改失败");
		}
		resultMap.put("status", result);
		return resultMap;
	}
	
}
