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
<script type="text/javascript" src="/touch/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>
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
		<li>订单日期：<span>${order.createTime!''}</span></li>
		<li>使用积分：<span>${order.point}</span></li>
		<li>订单编号：<span><#if order??>${order.orderNumber!''}</#if></span></li>
		<li>订单状态：<span>
		          <#if order.statusId?? && order.statusId == 1>     
                         待发货
                    <#elseif order.statusId?? && order.statusId == 2>
                            待收货
                    <#elseif order.statusId?? && order.statusId == 3>
                        已完成
                    <#elseif order.statusId?? && order.statusId == 4>
                        已取消
                    </#if></span></li>
	</ul>
  	<ul class="order_detail">
		<li>收货人：<span><#if order??>${order.shippingName!''}</#if></span></li>
		<li>手机号码：<span>${order.shippingPhone!''}</span></li>
		<li>详细地址：<span>${order.shippingAddress!''}</span></li>
		<li>备注：<span>${order.userRemarkInfo!''}</span></li>
	</ul>

	<section class="order_list" style="margin-bottom:0.4rem;border:none;">
    <ul>
          <li>
                <a href="/touch/point/goods/detail?id=${order.pointId?c}" class="pic"><img src="${order.goodsImg!''}" /></a>
                <div class="info">
                  <a href="/touch/point/goods/detail?id=${order.pointId?c}">${order.goodsTitle}</a>
                  <p>积分：${order.point}</p>
                </div>
                <div class="clear"></div>  
          </li>
    </ul>  
  </section>
   <section class="cart_foot">
   <#if order.statusId==1>
   <a  id="btn_show" onclick="orderFinish(${order.id?c},4);" class="btn">取消兑换</a>
   <#elseif order.statusId==2>
    <a  id="btn_show" onclick="orderFinish(${order.id?c},3);" class="btn">确定收货</a>
    </#if>
  </section>

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
