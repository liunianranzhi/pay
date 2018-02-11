package com.yijia360.pay.core.service.pay.alipay.util;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class AlipaySubmit {
    
    /**
     * 支付宝提供给商户的服务接入网关URL(新)
     */
    private static final String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";
	
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara,String key) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AliPayCommon.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, key, AliPayCommon.aliInputCharset);
        }
        return mysign;
    }
	
    /**
     * 生成要请求给支付宝的参数数组
     * @param sParaTemp 请求前的参数数组
     * @return 要请求的参数数组
     */
    private static Map<String, String> buildRequestPara(Map<String, String> sParaTemp,String key) {
        Map<String, String> sPara = AlipayCore.paraFilter(sParaTemp);
        String mysign = buildRequestMysign(sPara,key);
        sPara.put("sign", mysign);
        sPara.put("sign_type", AliPayCommon.sign_type);
        return sPara;
    }

    public static String buildRequest(Map<String, String> sParaTemp, String strMethod, String strButtonName,String key) {
        //待请求参数数组
        Map<String, String> sPara = buildRequestPara(sParaTemp,key);
        List<String> keys = new ArrayList<String>(sPara.keySet());

        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id='alipaysubmit' name='alipaysubmit' action='" + ALIPAY_GATEWAY_NEW
                      + "_input_charset=" + AliPayCommon.aliInputCharset + "' method='" + strMethod
                      + "'>");
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            sbHtml.append("<input type='hidden' name='" + name + "' value='" + value + "'/>");
        }
        sbHtml.append("<input type='submit' value='" + strButtonName + "' style='display:none;'></form>");
        sbHtml.append("<script>document.forms['alipaysubmit'].submit();</script>");
        return sbHtml.toString();
    }
    
    
    /**
     * 生成支付链接
     * @param sParaTemp
     * @return
     */
    public static String buildRequestUrl(Map<String, String> sParaTemp,String privateKey){
        Map<String, String> sPara = buildRequestPara(sParaTemp,privateKey);
        List<String> keys = new ArrayList<String>(sPara.keySet());
        StringBuffer sbHtml = new StringBuffer();
        sbHtml.append(ALIPAY_GATEWAY_NEW);
        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sPara.get(name);
            String prefix = i>0?"&":"";
            sbHtml.append(prefix);
            sbHtml.append(name);
            sbHtml.append("=");
            sbHtml.append(value);
        }
        return sbHtml.toString();
    }
    
    
    
    private static final String ALGORITHM = "RSA";
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	private static final String DEFAULT_CHARSET = "UTF-8";

    public static String buildAppResult(String orderInfo,String privateKey){
    	try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(orderInfo.getBytes(DEFAULT_CHARSET));
			byte[] signed = signature.sign();
			String temp = Base64.encode(signed);
	    	String sign =URLEncoder.encode(temp, "UTF-8");
			String payInfo = orderInfo + "&sign=\"" + sign + "\"&sign_type=\"RSA\"";
			return payInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
}
