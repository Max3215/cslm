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
<script src="/touch/js/search.js"></script>
<script type="text/javascript">
var pageIdx = 0;
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	var url = window.location.href+"/more";
    $('#collect_page').refresh(url,"#collect_page",0);	
});

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
        <a href="javascript:;" class="sel">宝贝收藏</a>
        <a href="/touch/user/collect/list/2">店铺收藏</a>
    </menu>
  <div style="height:0.61rem;"></div>
  <!-- 我的收藏 -->
  <section class="collection_list">
    <ul id="collect_page">
    <#assign allChecked=true >
    <#if collect_page??>
        <#list collect_page.content as cg>
        <#if cg.isSelect?? && cg.isSelect == false>
            <#assign allChecked=false >
        </#if>     
	      <li>
	        <a href="/touch/user/collect/select?id=${cg.id?c}" class="choose <#if cg.isSelect?? && cg.isSelect==true>sel</#if>" ></a>
	        <a href="/touch/goods/${cg.distributorId?c}" class="pic"><img src="${cg.goodsCoverImageUri!''}" /></a>
	        <a href="/touch/goods/${cg.distributorId?c}" class="name">${cg.goodsTitle!''}</a>
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
    <a href="/touch/user/collect/selectAll" class="all_choose <#if allChecked ==true>sel</#if>">全选</a>
  </section>
  <a href="/touch/user/collect/delAll" class="add_address_btn">取消收藏</a>
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
