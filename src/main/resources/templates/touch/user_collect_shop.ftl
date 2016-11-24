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
function getUrl(){
       document.location = "http://www.chinacslm.cc/touch/user";
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user" class="back"></a>
		<p>我的收藏</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
   <menu class="collection_tit">
        <a href="/touch/user/collect/list/1">宝贝收藏</a>
        <a href="javascript:;" class="sel">店铺收藏</a>
    </menu>
  <div style="height:0.61rem;"></div>
  <!-- 我的收藏 -->
  <section class="collection_list">
    <ul>
    <#if collect_page??>
        <#list collect_page.content as cg>     
      <li>
        <i></i>
        <a href="/touch/index?id=${cg.distributorId?c}" class="pic"><img src="${cg.goodsCoverImageUri!''}" /></a>
        <a href="/touch/index?id=${cg.distributorId?c}" class="name">${cg.goodsTitle!''}</a>
        <menu>
        <a href="/touch/user/collect/delshop?id=${cg.id?c!''}" class="btn">取消收藏</a>
        <a href="/touch/index?id=${cg.distributorId?c}" class="btn btn01">进入店铺</a>
        </menu>
        <div class="clear"></div>
      </li>
        </#list>
     <#else>
    <div>
        <p style="text-align:center">您还没有收藏店铺哦！</p>
    </div>
    </#if>
    </ul>
  </section>
  <div style="height:2rem;"></div>
  <!-- 我的收藏 END -->

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
