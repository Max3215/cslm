<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
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
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>我的收藏</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 我的收藏 -->
  <section class="collection_list">
    <ul>
    <#if collect_page??>
        <#list collect_page.content as cg>     
      <li>
        <a href="" class="choose" onclick="$(this).toggleClass('sel');"></a>
        <a href="/touch/goods/${cg.distributorId?c}" class="pic"><img src="${cg.goodsCoverImageUri!''}" /></a>
        <a href="#" class="name">${cg.goodsTitle!''}</a>
        <p>价格：￥${cg.goodsSalePrice?string("0.00")}</p>
        <a href="/touch/user/collect/del?id=${cg.distributorId?c!''}" class="btn">取消收藏</a>
        <div class="clear"></div>
      </li>
        </#list>
     <#else>
    <div>
        <p style="text-align:center">您还没有收藏商品哦！</p>
        <p style="text-align:center"><a class="blue" href="/touch/">马上去购物>>  </a></p>
    </div>
    </#if>
    </ul>
    <a href="#" class="all_choose">全选</a>
  </section>
  <a href="#" class="add_address_btn">取消收藏</a>
  <div style="height:2rem;"></div>
  <!-- 我的收藏 END -->

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
