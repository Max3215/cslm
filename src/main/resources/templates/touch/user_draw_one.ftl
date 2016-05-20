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

function draw(){
    var price = $("#price").val();
    var card = $("#card").val();
    var money = $("#money").html();
    
    var cards = /^(\d{16}|\d{19})$/;
    if(undefined == card || "" == card || !cards.test(card)){
        ct.alert({
                text: "输入正确的卡号",
                type: "alert"
            });
        return;
    }
    
    var reg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
    if(undefined == price || "" == price || !reg.test(price) || price > money){
         ct.alert({
                text: "输入正确的充值金额",
                type: "alert"
            });
        return;
    }else{
       $("#form1").submit();
    }
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>填写提现金额</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <section class="withdraw recharge_money">
    <form id="form1" action="/touch/user/draw2"  method="post">
  	<div class="number">到账卡号<input type="text" class="text" id="card" name="card" placeholder="请输入卡号" /></div>
  	<div class="money">
  		<h3>提现金额<input type="text" id="price" name="price"  class="text" placeholder="请输入提现金额" /></h3>
  		<p>余额 ¥<span id="money"><#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0.00</#if></span></p>
  	</div>
  	<a href="javascript:draw();" class="btn">下一步</a>
  	</form>
  </section>
  
</body>
</html>
