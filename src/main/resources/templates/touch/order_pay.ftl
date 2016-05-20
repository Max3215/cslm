<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  
});
</script>
</head>

<body>
<header class="com_top">
    <a href="javascript:history.go(-1);" class="back"></a>
        <p>订单完成</p>
        <a href="/" class="c_home"></a>
    </header>
    <div style="height:0.88rem;"></div>
<div class="comhead_bg"></div>
<!--header END-->

<div id="main" class="page-main">
    <div class="car_success">
        <p class="fc fs30 lh40 pb10">订单提交成功! </p>
        <p> 订单号：<a href="/touch/user/order?id=${order.id?c!''}" style="color: #FF0000;">${order.orderNumber!''}</a></p>
        <p> 支付方式：${order.payTypeTitle!''}</p>
        <p>应付金额￥${order.totalPrice?string('0.00')} 
            <a class="blue" href="/touch/order/dopay/${order.id?c}" style="color: #FF0000;">点击支付</a></p>
        <p>您还可以在订单详情页面
            <a class="blue" style="color: #FF0000;" href="/touch/user/order?id=${order.id?c!''}">查看详情</a></p>
    </div>
</div>

</body>
</html>
