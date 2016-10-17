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
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/setting/index" class="set_up"></a>
		<p>会员中心</p>
		<!--
		<a href="#" class="news"></a>
		-->
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 会员头像部分 -->
  <section class="viptop_pic">
    <img src="/touch/images/pictures/v_banner.jpg" />
    <p class="head_pic"><img src="${user.headImageUri!'/client/headpic.png'}"></p>
  </section>
  <!-- 会员头像部分 END -->

  <section class="m_order">
    <p>
      <img src="/touch/images/m_icon01.png" />
      <span>我的订单</span>
      <a href="/touch/user/order/list/0">查看全部订单&gt;</a>
    </p>
    <div class="tabfix o_menu">
      <menu>
        <a href="/touch/user/order/list/2">
          <#if total_unpayed??><i>${total_unpayed!'0'}</i></#if>
          <img src="/touch/images/order01.png" />
          <span>待付款</span>
        </a>
        <a href="/touch/user/order/list/3">
           <#if total_undelivered??><i>${total_undelivered!'0'}</i></#if>
          <img src="/touch/images/order02.png" />
          <span>待发货</span>
        </a>
        <a href="/touch/user/order/list/4">
            <#if total_unreceived??><i>${total_unreceived!'0'}</i></#if>
          <img src="/touch/images/order03.png" />
          <span>待收货</span>
        </a>
        <a href="/touch/user/order/list/5">
           <#if total_finished??><i>${total_finished!'0'}</i></#if>
          <img src="/touch/images/order04.png" />
          <span>待评价</span>
        </a>
        <a href="/touch/user/return/list">
          <img src="images/order06.png" />
          <span>退货列表</span>
        </a>
      </menu>
    </div>
  </section>

  <menu class="m_other">
    <a href="/touch/user/address/list">
      <img src="/touch/images/m_icon02.png" />
      <span>我的地址</span>
      <font>&gt;</font>
    </a>
    <a href="/touch/user/collect/list/1">
      <img src="/touch/images/m_icon03.png" />
      <span>我的收藏</span>
      <font>&gt;</font>
    </a>
    <a href="/touch/user/point/list">
      <img src="/touch/images/m_icon04.png" />
      <span>我的积分</span>
      <font>&gt;</font>
    </a>
    <a href="/touch/user/account/info">
      <img src="/touch/images/m_icon05.png" />
      <span>账户管理</span>
      <font>&gt;</font>
    </a>
  </menu>

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
