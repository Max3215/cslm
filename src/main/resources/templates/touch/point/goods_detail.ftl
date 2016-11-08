<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title>暂无标题</title>
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 
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

<script type="text/javascript">
$(document).ready(function(){
	// indexBanner("pro_box","pro_sum",300,5000,"pro_num");//Banner
    menuCheckShow("check_menu","a","check_con",".text","act","")//选项卡
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
        <p>商品详情</p>
        <a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 商品滑动图片 -->
  <section id="pro_box" class="bannerbox">
    <ul id="pro_sum" class="bannersum">
      <li><img src="${goods.imgUrl!''}" /></li>
    </ul>
    <div class="clear"></div>
  </section>
  <!-- 商品滑动图片 END -->

  <!-- 商品信息 -->
  <section class="pro_info">
    <div class="con">
      <h3>${goods.goodsTitle!''}</h3>
      <p class="f_tit">${goods.subGoodsTitle!''}</p>
      <p class="num">商品编号：${goods.code!''}</p>
      <div class="need_jf">
        <p>所需积分：<span>${goods.point!'0'}积分</span><font>（您当前的可用积分为：<#if user??>${user.totalPoints!'0'}<#else>0</#if>）</font></p>
      </div>
      <a onclick="addPoint(${goods.id?c})"class="dh_btn">立即兑换</a>
    </div>
  </section>
  <!-- 商品信息 END -->


  <!-- 详情和评价 -->
  <menu id="check_menu">
    <a href="javascript:void(0)">商品详情</a>
    <a href="javascript:void(0)">兑换说明</a>
  </menu>
  <section id="check_con">
    <div class="text">
      ${goods.detail!''}
    </div>
    <div class="text">
      ${goods.changeDetail!''}
    </div>
  </section>
  <!-- 详情和评价 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
        <menu>
            <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
