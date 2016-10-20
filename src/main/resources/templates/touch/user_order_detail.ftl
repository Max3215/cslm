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

</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>订单详情</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  	
  	<ul class="order_detail">
		<li>订单日期：<span><#if order??>${order.orderTime?string('yyyy-MM-dd')}</#if></span></li>
		<li>订单总价：<span>￥<#if order??>${order.totalPrice?string("0.00")}</#if></span></li>
		<li>使用积分：<span><#if order.pointUse??>${order.pointUse!'0'}<#else>0</#if></span></li>
		<li>订单编号：<span><#if order??>${order.orderNumber!''}</#if></span></li>
		<li>支付方式：<span>${order.payTypeTitle!""}</span></li>
		<li>配送方式：<span><#if order?? && order.deliveryMethod?? && order.deliveryMethod==1>门店自提：${order.shipAddress!''}<#else>送货上门</#if></span></li>
	</ul>
  	<ul class="order_detail">
		<li>收货人：<span><#if order??>${order.shippingName!''}</#if></span></li>
		<li>手机号码：<span>${order.shippingPhone!''}</span></li>
		<li>详细地址：<span>${order.shippingAddress!''}</span></li>
		<li>备注：<span>${order.userRemarkInfo!''}</span></li>
	</ul>

	<section class="order_list" style="margin-bottom:0.4rem;border:none;">
    <ul>
        <#if order?? && order.orderGoodsList??>
            <#list order.orderGoodsList as og> 
              <li>
                    <a href="/touch/goods/${og.goodsId?c}" class="pic"><img src="${og.goodsCoverImageUri!''}" /></a>
                    <div class="info">
                      <a href="/touch/goods/${og.goodsId?c}">${og.goodsTitle!''}</a>
                      <#if og.specName??><p>规格：${og.specName!''}</p></#if>
                      <p>价格：￥${og.price?string('0.00')}</p>
                      <p>数量：${og.quantity!'0'}</p>
                      <#if order.statusId==5 && og.isReturnApplied?? && og.isReturnApplied== false>
                      <a href="/touch/user/return/${order.id?c}?id=${og.id?c}" class="sqth">申请退货</a>
                      </#if>
                    </div>
                    <div class="clear"></div>  
              </li>
              </#list>
         </#if>
    </ul>  
  </section>

  <ul class="order_detail">
		<li>商品价格：<span>￥<#if order?? && order.totalGoodsPrice??>${order.totalGoodsPrice?string('0.00')}</#if></span></li>
		<li>邮费：<span>￥<#if order.postPrice??>${order.postPrice?string("0.00")}<#else>0</#if></span></li>
		<li style="height:20px;"></li>
		<li>实付总额：<span style="color:#d64532;">￥<#if order??>${order.totalPrice?string("0.00")}</#if></span></li>
		
	</ul>
	<div style="height:0.88rem;"></div>
<section class="comfooter tabfix">
        <menu>
            <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
</body>
</html>
