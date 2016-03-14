<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	indexBanner("box","sum",300,5000,"num");//Banner
});
</script>
</head>

<body>
<div id="allmap" style="display:none"></div>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="#" class="home"></a>
		<div class="branch">
			<p>王明辉超市五华总店</p>
		</div>
		<a href="#" class="news"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- banner -->
  <section id="box" class="bannerbox">
  	<div class="search">
  	     <form action="/touch/search">
  		<input type="text" class="text" name="keywords" placeholder="请搜索关键字" />
  		<input type="submit" class="sub" value=" " />
  		</form>
  	</div>
    <ul id="sum" class="bannersum">
        <#if banner_ad_list??>
            <#list banner_ad_list as item>
                <li><a href="${item.linkUri!''}" <#if item.typeIsNewWindow?? && item.typeIsNewWindow>target="_blank"</#if>><img src="${item.fileUri!''}" /></a></li>
            </#list>
        </#if>
    </ul>
    <div class="clear"></div>
  </section>
  <!-- banner END -->

  <!-- 入口菜单 -->
  <section class="tabfix index_enter">
  	<nav>
  		<a href="/touch/category/list">
  			<img src="/touch/images/index01.png" />
  			<p>商品分类</p>
  		</a>
  		<a href="#">
  			<img src="/touch/images/index02.png" />
  			<p>新品推荐</p>
  		</a>
  		<a href="/touch/info/list/12">
  			<img src="/touch/images/index03.png" />
  			<p>帮助中心</p>
  		</a>
  	</nav>
  	<nav>
  		<a href="/touch/user">
  			<img src="/touch/images/index04.png" />
  			<p>会员中心</p>
  		</a>
  		<a href="/touch/cart">
  			<img src="/touch/images/index05.png" />
  			<p>购物车</p>
  		</a>
  		<a href="#">
  			<img src="/touch/images/index06.png" />
  			<p>投诉建议</p>
  		</a>
  	</nav>
  </section>
  <!-- 入口菜单 END -->

  <!-- 新品推荐广告位 -->
  <section class="new_arrivals">
  <#if recommend_right_ad_list?? && recommend_right_ad_list?size gt 0>
    <a href="${recommend_right_ad_list[0].linkUri!''}" class="left_pic"><img src="${recommend_right_ad_list[0].fileUri!''}" /></a>
  </#if>
  	<div class="right_pic">
  	     <#if recommend_top_ad_list?? && recommend_top_ad_list?size gt 0>
            <a href="${recommend_top_ad_list[0].linkUri!''}" class="row"><img src="${recommend_top_ad_list[0].fileUri!''}" /></a>
          </#if>
  		<div class="clear"></div>
  		<menu>
  		   <#if recommend_bot_ad_list??>
  		   <#list recommend_bot_ad_list as ad>
  		        <#if ad_index lt 2>
        		<a href="${ad.linkUri!''}"><img src="${ad.fileUri!''}" /></a>
        		</#if>
  		   </#list>
  		   </#if> 
  			<div class="clear"></div>
  		</menu>
  	</div>
  </section>
  <!-- 新品推荐广告位 END -->

  <!-- 超市快讯 -->
  <section class="news_flash">
    <#if news_ad_list??>
        <#list news_ad_list as ad>
              	<p class="top"><img src="${ad.fileUri!''}" /></p>
        </#list>
    </#if>
  	<menu>
  	    <#if news_page?? && news_page.content?size gt 0>
  	    <#list news_page.content as news>
      		<a href="/touch/info/content/${news.id?c}?mid=10">${news.title!''}</a>
  	    </#list>
  	    </#if>
  	</menu>
  	<a href="/touch/info/list/10" class="more_btn">查看更多>></a>
  </section>
  <!-- 超市快讯 END -->

  <!-- 楼层part -->
  <section class="index_part">
  	<div class="top">
  	     <#if top_category_list?? && top_category_list?size gt 0 >
            <#list top_cat_list as item>
                <#if item_index lt 2>
              		<a href="/touch/list/${item.id?c}">
              			<img src="${item.imgUrl!''}" />
              			<p class="p1">${item.title!''}</p>
              		</a>
                </#if>
            </#list>
         </#if>
  	</div>
  	<menu>
  	     <#if top_cat_goods_page0?? && top_cat_goods_page0.content?size gt 0 >
      	    <#list top_cat_goods_page0.content as item>
                 <#if item_index lt 4 > 
              		<a href="/touch/goods/${item.id?c!''}">
              			<img src="${item.coverImageUri!''}" />
              			<p>${item.goodsTitle!''}</p>
              			<p>¥ ${item.goodsPrice?string('0.00')}</p>
              		</a>
          		</#if>
            </#list>
         </#if>
  	</menu>
  </section>
  <!-- 楼层part END -->

  <!-- 楼层part -->
  <section class="index_part">
  	<div class="top plus">
  	 <#if top_category_list?? && top_category_list?size gt 0 >
            <#list top_cat_list as item>
                <#if item_index gt 1 && item_index lt 5>
                    <a href="/touch/list/${item.id?c}">
                        <img src="${item.imgUrl!''}" />
                        <p class="p1">${item.title!''}</p>
                    </a>
                </#if>
            </#list>
         </#if>
  	</div>
  	<menu>
  	 <#if top_cat_goods_page2?? && top_cat_goods_page2.content?size gt 0 >
        <#list top_cat_goods_page2.content as item>
             <#if item_index lt 5 > 
                <a href="/touch/goods/${item.id?c!''}">
                    <img src="${item.coverImageUri!''}" />
                    <p>${item.goodsTitle!''}</p>
                    <p>¥ ${item.goodsPrice?string('0.00')}</p>
                </a>
            </#if>
        </#list>
     </#if>
  	</menu>
  </section>
  <!-- 楼层part END -->

  <!-- 楼层part -->
  <section class="index_part">
  	<div class="top plus">
  	     <#if top_category_list?? && top_category_list?size gt 0 >
            <#list top_cat_list as item>
                <#if item_index gt 4 && item_index lt 8>
                    <a href="/touch/list/${item.id?c}">
                        <img src="${item.imgUrl!''}" />
                        <p class="p1">${item.title!''}</p>
                    </a>
                </#if>
            </#list>
         </#if>
  	</div>
  	<menu>
  	     <#if top_cat_goods_page5?? && top_cat_goods_page5.content?size gt 0 >
            <#list top_cat_goods_page5.content as item>
                 <#if item_index lt 5 > 
                    <a href="/touch/goods/${item.id?c!''}">
                        <img src="${item.coverImageUri!''}" />
                        <p>${item.goodsTitle!''}</p>
                        <p>¥ ${item.goodsPrice?string('0.00')}</p>
                    </a>
                </#if>
            </#list>
         </#if>
  	</menu>
  </section>
  <!-- 楼层part END -->

  <!-- 热卖商品 -->
  <section class="pro_hot">
  	<p class="tit">热卖商品</p>
  	<menu>
  	 <#if recommed_index_page?? && recommed_index_page.content?size gt 0 >
         <#list recommed_index_page.content as item>
         <#if item_index lt 12>
            <a href="/touch/goods/${item.id?c}">
                <img src="${item.coverImageUri!''}"" />
                <p>${item.goodsTitle!""}</p>
                <p>￥${item.goodsPrice?string('0.00')}</p>
            </a> 
         </#if>
         </#list>
     </#if>
  	</menu>
  </section>
  <!-- 热卖商品 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 sel" href="/touch">平台首页</a>
	        <a class="a2" href="/touch/category/list">商品分类</a>
	        <a class="a3" href="/touch/cart">购物车</a>
	        <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
