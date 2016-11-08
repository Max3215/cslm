<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>确认订单</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/style.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>

<script type="text/javascript" src="/touch/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner

});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>兑换商品</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
 <form id="form1" action="/touch/point/order/submit" method="post">
  <!-- 购物车确认 -->
  <div id="address_list">
    <#include "/touch/point/order_info_addr.ftl">
  </div>
      
  <div class="pay_massege"><textarea placeholder="给商家留言" name="userRemarke"></textarea></div>

  <section class="order_list" style="margin-bottom:0.4rem;border:none;">
    <ul>
        <li>
            <input type="hidden" name="goodsId"  value="${goods.id?c}">
            <a href="/touch/point/goods/detail?id=${goods.id?c}" class="pic"><img src="${goods.imgUrl!''}"></a>
            <div class="info">
              <a href="/touch/point/goods/detail?id=${goods.id?c}"">${goods.goodsTitle!''}</a>
              <p>积分：${goods.point!'0'}</p>
            </div>
            <div class="clear"></div>
          </li>
    </ul>  
  </section>
   
  <div style="height:0.58rem;"></div>
  <section class="cart_foot">
     <p>可使用：<span >${user.totalPoints!''}</span>&nbsp;本次消耗：<span >${goods.point!'0'}</span></p>
    <a  id="btn_show" onclick="subForm();" class="btn">确定</a>
  </section>
  <!-- 购物车确认 END -->
</form>
  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 " href="/touch/disout">平台首页</a>
	        <a class="a2" href="/touch/category/list">商品分类</a>
	        <a class="a3 sel" href="/touch/cart">购物车</a>
	        <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
