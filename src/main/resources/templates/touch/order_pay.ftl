<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>联超商城</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  
});
</script>
</head>

<body class="whitebg">
<!-- 顶部 -->
    <header class="com_top">
        <a href="javascript:history.go(-1);" class="back"></a>
        <p>提交订单</p>
        <a href="/touch" class="c_home"></a>
    </header>
    <div style="height:0.88rem;"></div>
    <!-- 顶部 END -->

  <section class="sub_order_success">
    <h3>您已成功提交订单！</h3>
    <p>订单号：<span>${order.orderNumber!''}</span></p>
    <p>支付方式：${order.payTypeTitle!''}</p>
    <p>应付金额：<span>¥${order.totalPrice?string('0.00')}</span></p>
    <menu class="btns">
      <a href="/touch/order/dopay/${order.id?c}">立即支付</a>
      <span>您还可以</span>
      <a href="/touch/user/order?id=${order.id?c!''}">查看订单详情</a>
    </menu>
  </section>
  

</body>
</html>
