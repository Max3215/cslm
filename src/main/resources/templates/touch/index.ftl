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
<script type="text/javascript" src="/touch/js/jquery.SuperSlide.2.1.1.js"></script>
<script src="/touch/js/index.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	indexBanner("box","sum",300,5000,"num");//Banner
});

$(function(){
		<#if top_cat_list??>
	    categoryGoods($("#one_cat"),${top_cat_list[0].id?c});
	    </#if>
});

function hidenStart(){
    $(".start_pop").css("display","none");
}
function showShop(){
    $('.infp_eject').fadeIn(300)
    $(".start_pop").css("display","none");
}
</script>
</head>

<body>

	<!-- 顶部 -->
	<header class="com_top">
        <a href="javascript:;" class="home"></a>
        <div class="branch">
            <a href="javascript:void(0)" onclick="$('.infp_eject').fadeIn(300)"><#if distributorTitle??>${distributorTitle!''}<#else>联超商城店铺选择</#if></a>     
        </div>
    <div class="infp_eject" style="display: none;" id="shopList">
       <#include "/touch/shop_list.ftl" />
    </div>
        <a href="/touch/user/collect/list/1" class="coll"></a>
    </header>
    <div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- banner -->
  <section id="box" class="bannerbox">
    <#--
  	<div class="search">
  	     <form action="/touch/search">
  		<input type="text" class="text" name="keywords" placeholder="请搜索关键字" />
  		<input type="submit" class="sub" value=" " />
  		</form>
  	</div>
  	-->
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

     <!-- 搜索框 -->
    <form action="/touch/search">
    <div class="search_box">
        <div class="select">
          <a class="on" href="javascript:void(0);" onclick="shopDown(this)">商品</a>
          <input type="hidden" name="type" id="type" value="商品" />
          <menu>
            <a href="javascript:void(0);" onclick="shopCheck(this)">商品</a>
            <a href="javascript:void(0);" onclick="shopCheck(this)">店铺</a>
          </menu>
        </div>
        <input type="text" class="text" name="keywords" placeholder="请搜索关键字" />
        <input type="submit" class="sub" value=" " />
    </div>
    </form>
  <script type="text/javascript">
    function shopDown(obj){
      var _box = $(obj).parent().find("menu");
      _box.slideToggle(200);
    }

    function shopCheck(obj){
      var _str = $(obj).html();
      var _box = $(obj).parent().parent().find(".on");
      _box.html(_str);
      $("#type").attr("value",_str);
      $(obj).parent().slideUp(200);
    }
  </script>

  <!-- 入口菜单 -->
  <section class="tabfix index_enter">
  	<nav>
  		<a href="/touch/category/list">
  			<img src="/touch/images/index01.png" />
  			<p>商品分类</p>
  		</a>
  		<a href="/touch/findNew">
  			<img src="/touch/images/index02.png" />
  			<p>新品推荐</p>
  		</a>
  		<a href="/touch/info/list/12">
  			<img src="/touch/images/index03.png" />
  			<p>帮助中心</p>
  		</a>
  		<a href="/touch/user">
  			<img src="/touch/images/index04.png" />
  			<p>会员中心</p>
  		</a>
  		<#--
  		<a href="/touch/cart">
  			<img src="/touch/images/index05.png" />
  			<p>购物车</p>
  		</a>
  		-->
  		<a href="/touch/point/goods/list">
            <img src="/touch/images/jifen.png" />
            <p>积分兑换</p>
        </a>
  		<a href="/touch/user/suggestion/list">
  			<img src="/touch/images/index06.png" />
  			<p>投诉建议</p>
  		</a>
  	</nav>
  </section>
  <!-- 入口菜单 END -->
   
   <!-- 超市快讯 -->
   <#if news_page?? && news_page.content?size gt 0>
  <section class="sm_news">
    <#if news_ad_list??>
        <#list news_ad_list as ad>
            <#if ad_index ==0>
            <a href="javascript:;" class="pic"><img src="${ad.fileUri!''}" /></a>
            </#if>
        </#list>
    </#if>
    <div class="slide">
      <menu>
        <#list news_page.content as news>
        <a href="/touch/info/content/${news.id?c}?mid=10">${news.title!''}</a>
        </#list>
      </menu>
    </div>
    <a href="/touch/info/list/10" class="more">更多</a>
  </section>
  <script type="text/javascript">
    jQuery(".sm_news").slide({mainCell:".slide menu",autoPage:true,effect:"top",autoPlay:true,vis:2});
   </script>
   </#if>
   <!-- END -->

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
  <#if tag_goodsList?? && tag_goodsList?size gt 0>
  <section class="cx_area">
    <p class="tit">促销专区</p>
    <div class="list" id="pic_move">
      <menu>
        <#list tag_goodsList as goods>
        <a href="/touch/goods/${goods.id?c}">
          <div class="content">
            <img  src="${goods.coverImageUri!''}" title="${goods.goodsTitle!''}" />
            <b>¥ ${goods.goodsPrice?string("0.00")}</b>
            <#if goods?? && goods.goodsMarketPrice??><p>¥ ${goods.goodsMarketPrice?string('0.00')}</p></#if>
          </div>
        </a>
        </#list>
      </menu>
    </div>
  </section>
  </#if>
  <script type="text/javascript">
    listInfeedMove("pic_move","menu","a",3);//横向滑动
  </script>
  <!-- 促销专区 end -->
  
	<!-- 精品推荐 -->
  <section class="jp_recommen">
    <p class="tit">精品推荐</p>
    <div class="top" id="slide_check">
      <menu>
      	<#if top_cat_list?? && top_cat_list?size gt 0 >
            <#list top_cat_list as item>
              		<a onclick="categoryGoods($(this),${item.id?c})" <#if item_index==0>class="cur" id="one_cat"</#if>>
			          <img src="${item.imgUrl!''}">
			          <img src="${item.imgUrl1!''}">
			          <p>${item.title!''}</p>
			        </a>
            </#list>
         </#if>
      </menu>
    </div>
    <script>
    $(document).ready(function(){
	var _arr = $(".jp_recommen .top"),
    	_arrtop = _arr.offset().top; 
      	$(window).scroll(function () {
         
        	if($(window).scrollTop() > _arrtop) {
          	_arr.addClass("float");
    	    }else {
    	      	_arr.removeClass("float");
    	    }
      	});	  
    	 
    });
    </script>
    

    <menu class="list" id="cateGoods">
     
    </menu>
  </section>

  <script type="text/javascript">
    listInfeedMove("slide_check","menu","a",4);//横向滑动
  </script>
  <!-- 精品推荐 end -->


  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <#if !DISTRIBUTOR_ID??>
	        <a class="a1 sel" href="/touch/disout">平台首页</a>
	        <#else>
	        <a class="a1 " href="/touch/disout">平台首页</a>
	        <a class="a5 sel" href="/touch">店铺首页</a>
	        </#if>
	        <a class="a2" href="/touch/category/list">商品分类</a>
	        <a class="a3" href="/touch/cart">购物车</a>
	        <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
  <!-- 提示弹出 -->
  <#if ISF?? && ISF ==true>
  <aside class="start_pop">
    <menu>
        <a onclick="showShop();" class="a1"></a>
        <a onclick="hidenStart();" class="a2"></a>
    </menu>
  </aside>
  </#if>
  
</body>
</html>
