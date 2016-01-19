<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=${charset!'UTF-8'}" />
<title>订单支付成功</title>
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
  adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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

    <div class="car_success">
    <#if order??>
    <p class="fc fs30 lh40 pb10">您已成功付款 ${order.totalPrice?string('0.00')}元</p>
    <p>对方将立即收到您的付款。</p>
    </#if>
    <#-->
    <p>如果您有未付款信息，<a class="blue" href="#">查看并继续付款</a></p>
    -->
    <p class="pt20"><a class="c9" href="/">继续购物</a></p>
    <img class="img" src="<#if site??>${site.wxQrCode!''}</#if>" height="150" />
  </div>

    <div class="clear h40"></div>
  
<#include "/client/common_footer.ftl" />
</body>
</html>
<!--结束-->
