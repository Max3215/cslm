<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<title><#if site??>${site.seoTitle!''}-</#if>咨询中心</title>
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
})
</script>

</head>

<body>
	<#include "/client/common_header.ftl">
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/">首页</a>>客户服务><#if info??>${info.title}</#if></p>
	</section>

	<#include "/client/common_float_box.ftl">

	<!--main-->
	<div class="main">
		<section class="service">
			<h3>客户服务</h3>
			<div class="left_list">
			 <#if td_art_list??>
                 <#list td_art_list as item>
				    <p class="tit">${item.title!''}</p>
				    <#if ("second_level_"+item_index+"_category_list")?eval??>
				        <menu>
                        <#list ("second_level_"+item_index+"_category_list")?eval as second_item>
					       <a href="/info/content/${second_item.id?c}?mid=12" <#if info?? && second_item.id==info.id>class="sel"</#if>>${second_item.title!''}<span>&gt;</span></a>
					    </#list>
				        </menu>
				    </#if>
			     </#list>	
			</#if>
			</div>
			<div class="right_con">
				<#if info??>${info.content!''}</#if>
			</div>
			<div class="clear"></div>
		</section>
	</div>


	<#include "/client/common_footer.ftl">

</body>
</html>