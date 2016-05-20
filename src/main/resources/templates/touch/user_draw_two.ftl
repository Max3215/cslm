<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>

<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />
<script src="/touch/js/message.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
});
function subDraw(){
    var pay = $("#payPwd").val();
    var password = $("#password").val();
    
    if(undefined == password || "" == password )
    {
        ct.alert({
            text: "请输入密码",
            type: "alert"
        })
        return ;
    }
    if(password != pay)
    {
        ct.alert({
            text : "密码错误，请重新输入",
            type : "alert"
        })
        return ;
    }
    
    $("#form").submit();
}


</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>安全验证</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <section class="with_money">
    <form action="/touch/user/draw3" id="form" method="post">
      	<input type="hidden" value="${card!''}" name="card" />
      	<input type="hidden" value="${price?string('0.00')}" name="price" />
      	<input type="hidden" value="${user.payPassword!''}" id="payPwd" />
      	<p>提现金额<span>¥${price?string('0.00')}</span></p>
      	<input type="password" class="text" placeholder="输入支付密码" id="password"  name="password"/>
      	<a href="javascript:subDraw();" class="btn">提现</a>
  	</form>
  </section>
  
</body>
</html>
