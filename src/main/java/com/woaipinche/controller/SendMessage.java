package com.woaipinche.controller;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import java.io.IOException;
public class SendMessage {
	// 短信应用 SDK AppID
	int appid = 1400238363; // SDK AppID 以1400开头
	// 短信应用 SDK AppKey
	String appkey = "9c81b3035ad5dea093fed1b943de84d3";
	// 需要发送短信的手机号码
	/*String phoneNumber = "18581515171";*/
	// 短信模板 ID，需要在短信应用中申请
	int templateId = 383800; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
	// 签
	String smsSign = "芝麻信息"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
	public void send(String[] params,String phoneNumber){
	        try {
		    //String[] params = {};//参数，验证码为5678，30秒内填写
	          /*  String[] params = {"123456","1"};//参数，验证码为123456，100秒内填写
*/	            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
	            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
	                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
	           result.toString();
	        } catch (HTTPException e) {
	            // HTTP响应码错误
	            e.printStackTrace();
	 
	        } catch (JSONException e) {
	            // json解析错误
	            e.printStackTrace();
	        } catch (IOException e) {
	            // 网络IO错误
	            e.printStackTrace();
	        }catch (Exception e) {
	            // 网络IO错误
	            e.printStackTrace();
	        }
	}
	
	public static void main(String[] args) {
		SendMessage ms = new SendMessage();
	/*	ms.send();*/
	}
}
