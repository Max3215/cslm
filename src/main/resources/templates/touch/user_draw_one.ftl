<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>联超商城</title>
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
    var bank = $("#bank").val();
    var name = $("#name").val();
    var money = parseFloat($("#money").html());
    
    var cards = /^(\d{16}|\d{19})$/;
    if(undefined == card || "" == card || !cards.test(card)){
        ct.alert({
                text: "输入正确的卡号",
                type: "alert"
            });
        return;
    }
    
    if(undefined == bank || "" ==bank){
        ct.alert({
                text: "输入开户行",
                type: "alert"
            });
        return;
    }
    if(undefined == name || "" ==name){
        ct.alert({
                text: "输入开户姓名",
                type: "alert"
            });
        return;
    }
    
    var reg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
    if(undefined == price || isNaN(price) || "" == price || !reg.test(price) || price > money){
         ct.alert({
                text: "输入正确的提现金额",
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
  	<div class="number">到账卡号<input type="text" value="<#if user??>${user.bankCardCode!''}</#if>" class="text" id="card" name="card" placeholder="请输入卡号" /></div>
  	<div class="number">开户银行<input type="text" value="<#if user??>${user.bankTitle!''}</#if>" class="text" id="bank" name="bank" placeholder="请输入开户行" /></div>
  	<div class="number">开户姓名<input type="text" value="<#if user??>${user.bankName!''}</#if>" class="text" id="name" name="name" placeholder="请输入开户姓名" /></div>
  	<div class="money">
  		<h3>提现金额<input type="text" id="price" name="price"  class="text" onkeyup="value=value.replace(/[^0-9]/g,'.')" placeholder="请输入提现金额" /></h3>
  		<p>余额 ¥<span id="money"><#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0.00</#if></span></p>
  	</div>
  	<span style="color:red;font-size: 0.22rem;">*每次提现最小金额100元，提交前请仔细核对信息是否有误</span>
  	<a href="javascript:draw();" class="btn">下一步</a>
  	</form>
  </section>
  
  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
        <menu>
            <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
</body>
</html>
