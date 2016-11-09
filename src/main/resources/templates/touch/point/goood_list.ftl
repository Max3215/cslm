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
<script src="/touch/js/search.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	
	var url = '/touch/point/goods/list/more';
    $('#goods_list').refresh(url,"#goods_list",0);
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>积分商城</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <section class="integralmall_top">
    <#if point_ad_list?? && point_ad_list?size gt 0>
        <img src="${point_ad_list[0].fileUri!''}" />
        </#if>
    <div class="user">
      <p class="pic"><img src="<#if user??>${user.username!''}<#else>/client/images/headimg.jpg</#if>" /></p>
      <p class="name"><#if user??>${user.username!''}<#else><a href="/touch">请登录</a></#if></p>
      <div class="num">
        <p><#if user??>${user.totalPoints!'0'}<#else>0</#if></p>
        <p>可用积分</p>
      </div>
    </div>
  </section>

  <section class="product_list">
    <ul id="goods_list">
    <#if goods_page?? && goods_page.content?size gt 0>
        <#list goods_page.content as goods>
          <li>
            <a href="/touch/point/goods/detail?id=${goods.id?c}"><img src="${goods.imgUrl!''}" /></a>
            <a href="/touch/point/goods/detail?id=${goods.id?c}" class="name">${goods.goodsTitle!''}</a>
            <p class="jf_num">${goods.point!'0'}分</p>
            <a onclick="addPoint(${goods.id?c})" class="dh_btn">立即兑换</a>
          </li>
      </#list>
      </#if> 
    </ul>
  </section>
  <!-- 商品类表 END -->

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
            <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
