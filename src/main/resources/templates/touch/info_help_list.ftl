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
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>帮助中心</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 帮助中心 -->
  <menu class="pro_menu"> 
        <#if td_art_list??>
        <#list td_art_list as art>
            <li>
                <a class="a1" href="javascript:void(0);" onclick="proMenuDown(this)">
                    <p class="p1" style="padding-bottom: 0px; float: none;">${art.title!''}</p>
                    <div class="clear"></div>
                </a>
                <div class="pro_menu_part" style="display: none;">
                <#if ("second_level_"+art_index+"_category_list")?eval??>
                    <#list ("second_level_"+art_index+"_category_list")?eval as info>
                    <a class="new_category_list_a" href="/touch/info/content/${info.id?c}?mid=${help_id!'12'}" title="">${info.title!''}</a>
                    </#list>
                 </#if>
                    <div class="clear"></div>
                </div>
            </li>
        </#list>
        </#if>      
    </menu>
  <!-- 帮助中心 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 sel" href="/touch/disout">平台首页</a>
	        <#if DISTRIBUTOR_ID??>
            <a class="a5" href="/touch">店铺首页</a>
            </#if>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 " href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
