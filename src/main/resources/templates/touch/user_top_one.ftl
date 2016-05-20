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

function topup(){
    var price = $("#price").val();
    
    var reg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
    if(undefined == price || "" == price || !reg.test(price)){
         ct.alert({
                text: "输入正确的充值金额",
                type: "alert"
            });
        return;
    }else{
        if(price > 50000 || price < 10){
            ct.alert({
                text: "充值金额必须在10~50000之间",
                type: "alert"
            });
            return;
        }else{
            $("#form1").submit();
        }
    }
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>填写充值金额</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 填写充值金额 -->
  <section class="recharge_money">
    <form action="/touch/user/topup2" method="post" id="form1">
    <input type="hidden" value="${user.username}" name="username"/>
  	<div class="number">金额<input type="text" class="text" id="price" name="price" placeholder="请输入充值的金额" /></div>
  	<div class="words">
  		<p>请注意：</p>
  		<p>支持国内主流银行储蓄卡充值，在线支付成功后，充值金额会在1分钟内到账。如遇充值异常，请致电客户处理。</p>
  		<p>客服电话：${site.telephone!''} </p>
  		<p>服务时间：周一至周日 0:00-24:00</p>
  	</div>
  	<a href="javascript:topup();" class="btn">下一步</a>
  	</form>
  </section>
  <!-- 填写充值金额 END -->
  
</body>
</html>
