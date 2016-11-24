<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>支付成功</title>
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

<script>
function getUrl(){
       document.location = "http://www.chinacslm.cc/touch/user/order/list/0";
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user/order/list/0" class="back"></a>
		<p>支付成功</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <section class="pay_success">
    <p>您已成功付款${order.totalPrice?string('0.00')}元</p>
    <p>订单编号为：${order.orderNumber!''}<br />我们将尽快安排发货，请您耐心等候</p>
    <menu>
      <a href="/touch/user/order?id=${order.id?c}">查看订单详情</a>
      <a href="/touch">继续逛商城</a>
    </menu>
  </section>
  
  <div style="height:0.88rem;"></div>
    <section class="comfooter tabfix">
        <menu>
            <a class="a1" href="/touch/disout">平台首页</a>
            <#if DISTRIBUTOR_ID??>
            <a class="a5" href="/touch">店铺首页</a>
            </#if>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
       </menu>
    </section>
</body>
</html>
