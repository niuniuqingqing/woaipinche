package com.woaipinche.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class WxPay {
    private String AppId = "wx3677e4735b4a133a";//公众账号ID
    private String AppSecret = "6ff2428cf94f9815c7258667ba3ea097";//公众账号密钥
    private String Key = "78945612301234567898765432101234";//微信支付密钥
    private String MchId = "1547873161";//微信支付分配的商户号
    /**
     * 根据code获取openId
     *
     * @param code
     * @return openId
     */
    private String getOpenId(String code) {
        String getUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
	             +  AppId + "&secret=" + AppSecret + "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = getUrlJson(getUrl);
        return jsonObject.get("openid").toString();
    }
    /**
     * httpget请求
     *
     * @param url
     * @return JSONObject
     */
    private JSONObject getUrlJson(String url) {
        JSONObject jsonObject = null;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
            httpGet.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    
    /**
     * 网页请求生成支付订单
     *
     * @param request
     * @param map
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/wxPay", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView createOrderOpenId(HttpServletRequest request, HttpServletResponse response) {
    	ModelAndView mv=new ModelAndView();
        Map orderMap = new HashMap();
        String total_fee = request.getParameter("total_fee");
		//获取用户的code
		String code = request.getParameter("code");
		
        String openId = getOpenId(code);
        if (StringUtils.isEmpty(openId)) {
            return mv;
        }
        try {
            String clientIp = getClientIp(request);
            orderMap.put("appid", AppId);  //  微信支付分配的公众账号ID（企业号corpid即为此appId）
            orderMap.put("mch_id", MchId); //  商户号
            orderMap.put("nonce_str", WxPayUtil.generateNonceStr());    //  随机字符串
            orderMap.put("body", "会员充值");   //  商品描述
            orderMap.put("out_trade_no", String.valueOf(System.currentTimeMillis()));   //  商户订单号
            orderMap.put("total_fee", total_fee);   //  订单总金额，单位为分
            orderMap.put("spbill_create_ip", clientIp);   //  终端IP,用户的客户端IP
            orderMap.put("notify_url","http://www.liujunchen.com.cn/woaipinche/payNotify"); //  通知地址-异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
            orderMap.put("trade_type", "JSAPI");    //  交易类型	 JSAPI -JSAPI支付
            orderMap.put("openid", openId); //  用户标识
            //  获取sign
            orderMap.put("sign", WxPayUtil.generateSignature(orderMap, Key));    //  key: apiKey
            //  订单数据拼接完成,转换map为xml的String字符串
            String xmlData = WxPayUtil.mapToXml(orderMap);
            //  统一下单->获取订单的prepay_id
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
            StringEntity postEntity = new StringEntity(xmlData, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader("User-Agent", WXPayConstants.USER_AGENT);
            httpPost.setEntity(postEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            //  获取统一下单接口返回数据
            String resXml = EntityUtils.toString(httpEntity, "UTF-8");
            Map orderResMap = WxPayUtil.xmlToMap(resXml);
            String prepayId = orderResMap.get("prepay_id").toString();
           
            //  封装前端调起JSAPI支付所需参数
            Map payMap = new HashMap();
            payMap.put("appId", AppId);  //  公众号id
            payMap.put("timeStamp", String.valueOf(WxPayUtil.getCurrentTimestamp())); //  时间戳,转换成秒数
            payMap.put("nonceStr", WxPayUtil.generateNonceStr());    //  随机字符串
            payMap.put("package", "prepay_id=" + prepayId);   //  订单详情扩展字符串
            payMap.put("signType", "MD5");   //  签名方式
            //  再次签名获取paySign
            payMap.put("paySign", WxPayUtil.generateSignature(payMap, Key));   //  签名 key:apiKey
            mv.addAllObjects(payMap);
            mv.setViewName("pay");
    		return mv;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return mv;
    }
    
    
    /**
     * 支付成功回调通知
     *
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void payNotify(HttpServletRequest request, HttpServletResponse response) {
        String returnStr = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        InputStream is = null;
        try {
            is = request.getInputStream();//获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
            String xmlStr = convertStreamToString(is);
            Map<String, String> notifyMap = WxPayUtil.xmlToMap(xmlStr);//将微信发的xml转map
            Map signMap = new HashMap(notifyMap);
            signMap.remove("sign");
            //  验签
            String localSign = WxPayUtil.generateSignature(signMap, Key);
            if (!notifyMap.get("sign").equals(localSign)) {
                return;
            }
            //同样的通知可能会多次发送给商户系统，商户系统必须能够正确处理重复的通知。如果已处理过，直接给微信支付返回成功。
            //  获取本地订单信息比对
            //响应微信服务器
            response.getWriter().write(returnStr);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    	
    private String getClientIp(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for");
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("Proxy-Client-IP");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("WL-Proxy-Client-IP");
    	}
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getRemoteAddr();
    	}
    	return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    	}
    
    
    
    public static String convertStreamToString(InputStream is) {      
         BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
         StringBuilder sb = new StringBuilder();      
     
         String line = null;      
        try {      
            while ((line = reader.readLine()) != null) {      
                 sb.append(line + "\n");      
             }      
         } catch (IOException e) {      
             e.printStackTrace();      
         } finally {      
            try {      
                 is.close();      
             } catch (IOException e) {      
                 e.printStackTrace();      
             }      
         }      
     
        return sb.toString();      
     }
    
    
    
}
