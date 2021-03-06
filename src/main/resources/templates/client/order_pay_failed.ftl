<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=${charset!'UTF-8'}" />
<title>订单支付失败</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<link href="/client/images/cslm.ico" rel="shortcut icon">
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/order_info.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />
<link href="/client/style/payment_status.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

  navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})
</script>
</head>
<body>
<#include "/client/common_header.ftl" />
  <div class="main">
  
  <div class="car_success" style="background: url(/client/images/transfailed.png) no-repeat left center;">
	    <p class="fc fs30 lh40 pb10">订单支付失败! </p>
	    <p> 订单号：<a href="/user/order?id=${order.id?c}">${order.orderNumber!''}</a></p>
	    <p> 支付方式：${order.payTypeTitle!''}</p>
	    <p>应付金额￥${order.totalPrice?string('0.00')} <a class="blue" href="/order/dopay/${order.id?c}" target="_blank">点击支付</a></p>
	    <p><#if order??>付款失败，或者支付结果验证失败，如果订单已经成功支付，请联系客服处理！<#else>找不到对应的订单！</#if></p>
	</div>
    <div class="clear"></div>
  </div>
<#include "/client/common_footer.ftl" />
</body>
</html>
<!--结束-->
