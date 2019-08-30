<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信支付1111</title>
<script src="weui/js/zepto.min.js"></script>
<script src="weui/js/zepto.weui.js"></script>
</head>
<body>
<input id="input1"></input>
<input id="input2"></input>
<input id="input3"></input>
<input id="input4"></input>
<input id="input5"></input>
</body>
<script type="text/javascript">
	$(function() {
		onBridgeReady();
	});
	function pay() {
		if (typeof WeixinJSBridge == "undefined") {
			if (document.addEventListener) {
				document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
						false);
			} else if (document.attachEvent) {
				document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
				document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			}
		} else {
			onBridgeReady();
		}
	}
	function onBridgeReady() {
		var appid = '${appId}';
		var timestamp = '${timeStamp}';
		var nocncestr = '${nonceStr}';
		var packageValue ='${packageValue}';
		var paysign = '${paySign}';
		$("#input1").val(appid);
		$("#input2").val(timestamp);
		$("#input3").val(nocncestr);
		$("#input4").val(packageValue);
		$("#input5").val(paysign);
		/* WeixinJSBridge.invoke('getBrandWCPayRequest', {
			"appId" : appid, //公众号名称，由商户传入
			"timeStamp" : timestamp, 
			"nonceStr" : nocncestr, //随机串
			"package" : packageValue,
			"signType" : "MD5", //微信签名方式:
			"paySign" : paysign //微信签名
		}, function(res) {
			if (res.err_msg == "get_brand_wcpay_request:ok") {
			} else if (res.err_msg == "get_brand_wcpay_request:cancel") {
				console.log("用户取消支付")
			} else {
				alert("支付失败!");
			}
		}); */
	}
</script>
</html>