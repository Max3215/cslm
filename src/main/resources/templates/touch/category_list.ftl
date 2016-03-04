<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>全部分类</title>
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
	//indexBanner("box","sum",300,5000,"num");//Banner
	var allHeight = $(window).height();
	var cutHeight = $(".com_top").height();
	$(".pro_classify li .show").height(allHeight - cutHeight);

	$(".pro_classify li").click(function(){
		$(this).addClass("act").siblings().removeClass("act");
	})

});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>商品分类</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 商品分类 -->
  <section class="pro_classify">
  	<ul>
  	    <#if top_cat_list??>
            <#list top_cat_list as item>
          		<li <#if item_index=0> class="act" </#if>>
          			<a href="javascript:void(0)" class="a1">${item.title!''}</a>
          			<#if ("second_level_"+item_index+"_cat_list")?eval?? >
              			<div class="show">
              				<#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                  				<dl>
                  					<dt>${secondLevelItem.title!''}</dt>
                  					<#if ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval?? >
                                        <#list ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval as thirdLevelItem>
                          					<dd><a href="/touch/list/${thirdLevelItem.id?c}">${thirdLevelItem.title!''}</a></dd>
                                        </#list>
                                  </#if>
                                  </dl>
                              </#list>
              			</div>
          			</#if>
          		</li>
      		</#list>
  		</#if>
  	</ul>
  </section>
  <!-- 商品分类 END -->
  
</body>
</html>
