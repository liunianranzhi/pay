package com.yijia360.pay.api.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yijia360.pay.api.constants.PayApi;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.pay.payModeConf.PayModeConfService;
import com.yijia360.pay.core.service.wx.WeixinService;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;
import com.yijia360.pay.utils.CommonUtils;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年4月12日上午11:00:29
 */
@RequestMapping(PayApi.WeiXin)
@RestController
public class WxAction extends BaseAction{
	
	@Autowired
	PayModeConfService payModeConfService;
	
	@Autowired
	WeixinService weixinService;
	
	@PostMapping("/getOpenId")
	public Result getOpenId(HttpServletRequest request){
		String wxCode = request.getParameter("code");
		if(!CommonUtils.isNumberOrLetter(wxCode)){
			return buildErrorResult("请检查code");
		}
		Integer partnerId = Integer.valueOf(request.getParameter("partnerId"));
		Long appId = Long.valueOf(request.getParameter("appId"));
		
		//微信公众号
		PayModeConfVo payModeConfVo = payModeConfService.InfoById(partnerId, appId, PayMode.wxPub.getCode());
		
		String wxAppId = payModeConfVo.getConfAppId();
		
		String wxAppSecret = payModeConfVo.getConfRsaPrivateKey();
		
		if(StringUtils.isEmpty(wxAppSecret)){
			return buildErrorResult("未配置wxAppSecret");
		}
		
		ServiceResult snsouth2ServiceResult = weixinService.snsOAuth2(wxCode, wxAppId, wxAppSecret);
		
		return buildResult(snsouth2ServiceResult);
	}
}