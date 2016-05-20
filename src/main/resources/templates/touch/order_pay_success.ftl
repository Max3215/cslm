<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>暂无标题</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
  
});
</script>
<style>
.zhifushibai{background:}
</style>
</head>

<body>
    <header class="com_top">
        <a href="javascript:history.go(-1);" class="back"></a>
        <p>支付成功</p>
        <a href="/touch" class="c_home"></a>
    </header>
    <div style="height:0.88rem;"></div>
<!--header END-->
<section class="loginbox">
  <div class="main pt20 pb40">
    <p class="ta-c fs10 pt20"><img src="/touch/images/transsucc.png" /></p>
    <p class="fs10 ta-l pt20">订单号：<a href="/touch/user/order?id=${order.id?c}" style="color: #ff4454;">${order.orderNumber!''}</a></p>
    <p class="ta-l fs10 pt20">支付金额：${order.totalPrice?string('0.00')}元</p>
    <p class="ta-l fs10 pt20">支付方式：${order.payTypeTitle!''}</p>
    <p class="ta-l fs10 pt20">订单状态：</p>
    <p class=" ta-l zhifushibai fs12 pt20 red">付款成功！</p>
  </div>
</section>


</body>
</html>
