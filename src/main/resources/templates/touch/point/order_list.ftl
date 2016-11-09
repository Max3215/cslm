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

<script src="/touch/js/message.js"></script>
<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />
<script src="/touch/js/search.js"></script>

<script type="text/javascript" src="/touch/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
    var url = '/touch/user/point/order/more';
    $('#order_list').refresh(url,"#order_list",0);
});


</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user" class="back"></a>
		<p>积分订单</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 我的订单 -->

    <div  id="order_list">
  <#if order_page??>
    <#list order_page.content as order>
    <section class="order_list" >
        <ul>
        <a href="/touch/user/point/order/detail?id=${order.id?c}"> <p class="number">订单号：${order.orderNumber!''}</p></a>
      <li>
        <a href="/touch/point/goods/detail?id=${order.pointId?c}" class="pic"><img src="${order.goodsImg!''}" /></a>
        <div class="info">
          <a href="/touch/point/goods/detail?id=${order.pointId?c}">${order.goodsTitle}</a>
          <p>积分：${order.point}</p>
        </div>
        <div class="clear"></div>
      </li>
    </ul>
    <div class="btns">
      <#if order.statusId??>
      <#switch order.statusId>
      <#case 1>
           <span>待发货</span>
           <menu>
            <a onclick="orderFinish(${order.id?c},4)" class="cur">取消兑换</a>
            <a href="/touch/user/point/order/detail?id=${order.id?c}">查看订单</a>
           </menu>
      <#break>
      <#case 2>
           <span>待收货</span>
           <menu>
                <a  onclick="orderFinish(${order.id?c},3)" class="cur">确认收货</a>
                <a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 3>
           <span>完成</span>
           <menu>
           <a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 4>
           <span>已取消</span>
           <menu>
                <a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
        </#switch>
      </#if>
    </div>
  </section>
   </#list>
   </#if>
   </div>
  <!-- 我的订单 END -->

  <!-- 底部 -->
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
  <!-- 底部 END -->
  
</body>
</html>
