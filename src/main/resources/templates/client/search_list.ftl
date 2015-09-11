<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<title><#if productCategory??>${productCategory.seoTitle!''}-</#if>超市联盟</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>

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
<!--   顶部   -->
<#include "/client/common_header.ftl" />
	
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/" title="首页">首页</a>
		&nbsp;&nbsp;&gt;&nbsp;&nbsp; <#if keywords??>${keywords!''}</#if>
        </p>
	</section>

	<!--banner-->
	<#if list_scroll_ad_list?? && list_scroll_ad_list?size gt 0>
    	<section id="n_banner_box">
    		<ul id="n_banner_sum">
    		  <#list list_scroll_ad_list as ad>
    		      <#if ad_index == 0>
        		      <li style="display:block;">
        			       <a href="${ad.linkUri!''}" title="${ad.title!''}" target="_blank">
        				        <img src="${ad.fileUri!''}" alt="">
        				   </a>
        			  </li>
    			  </#if>
    			  <#if ad_index gt 0 && ad_index lt 6 >
    			     <li>
    			         <a href="${ad.linkUri!''}" title="${ad.title!''}" target="_blank">
                             <img src="${ad.fileUri!''}" alt="">
                         </a>
    			     </li>
    			  </#if>
    		  </#list>
    		</ul>
    	</section>
    </#if>
    <!--右边悬浮框-->
	<#include "/client/common_float_box.ftl" />

	<!--main-->
	<section class="main">
		<div class="select_box">
		</div><!--  参数结束  -->

		<div class="left_list">
			<div class="tit">
				<div class="page">
					<span><b><#if goods_page.totalPages==0>0<#else>${goods_page.number+1}</#if></b>/${goods_page.totalPages!"0"}</span>
					
					<#if goods_page.number+1 == goods_page.totalPages || goods_page.totalPages==0>
                        <a href="javascript:;">上一页</a>
                    <#else>
                        <a href="/search?keywords=${keywords!''}&page=${goods_page.number+1}">上一页</a> <#-- goods_page.number+1 -->
                    </#if>
                            
                    <#if goods_page.number+1 == 1>
                        <a href="javascript:;">下一页</a>
                    <#else>
                        <a href="/search?keywords=${keywords!''}&page=${goods_page.number-1}">下一页</a> <#-- goods_page.number-1 -->
                    </#if>
				</div>
			</div>
			<!--    商品列表   -->
			<ul class="pro_list">
			    <#if goods_page?? && goods_page.content?size gt 0>
                    <#list goods_page.content as goods>
        				<li>
        					<a href="/goods/${goods.id?c}" class="a1">
        						<img src="${goods.coverImageUri!''}" width="200" height="201" title="${goods.title!''}"/>
        						<p>${goods.subTitle!""}</p>
        					</a>
        					<p class="price">￥${goods.salePrice?string("0.00")}<span>原价：￥${goods.marketPrice?string("0.00")}</span></p>
        					<menu class="btn">
        						<a href="/cart/init?id=${goods.id?c}" class="car" title="加入购物车"></a>
        						<a href="/order/byNow/${goods.id}" class="buy">立即购买</a> 
        						<div class="clear"></div>
        					</menu>
        				</li>
                    </#list>
                  <#else>
                  <div style="text-align: center; padding: 15px;">此类商品正在扩充中，敬请期待！</div>
                </#if> 
			</ul>
			<div class="clear"></div>
			
			<!--  分页     -->
			<div class="pages">
				<#if goods_page??>
                    <#assign continueEnter=false>
    				<span>共<#if goods_page??>${goods_page.totalElements!"0"}</#if>条记录&nbsp;&nbsp;&nbsp;&nbsp;<#if goods_page.totalPages==0>0<#else>${goods_page.number+1}</#if>/${goods_page.totalPages}页</span>
				    <#if goods_page.number == 0>
                         <a href="javascript:;">上一页</a>
                    <#else>
                         <a href="/search?keywords=${keywords!''}&page=${goods_page.number-1}">上一页</a>
                    </#if>
				    <#if goods_page.totalPages gt 0>
                        <#list 1..goods_page.totalPages as page>
                            <#if page <= 3 || (goods_page.totalPages-page) < 3 || (goods_page.number+1-page)?abs<3 >
                                <#if page == goods_page.number+1>
                                    <a class="sel" href="javascript:;">${page}</a>
                                <#else>
                                    <a href="/search?keywords=${keywords!''}&page=${page-1}">${page}</a> 
                                </#if>
                                <#assign continueEnter=false>
                            <#else>
                                <#if !continueEnter>
                                    <span> ... </span>
                                    <#assign continueEnter=true>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
        
                    <#if goods_page.number+1 == goods_page.totalPages || goods_page.totalPages==0>
                        <a href="javascript:;">下一页</a>
                    <#else>
                        <a href="/search?keywords=${keywords!''}&page=${goods_page.number+1}">下一页</a>
                    </#if>
                </#if>
			</div>
		</div>

		<div class="right_hot">
			<p class="tit">热卖推荐</p>
			<ul>
			     <#if hot_sale_list??>
                    <#list hot_sale_list as item>
                         <#if item_index < 6>
            				<li>
            					<a href="/goods/${item.id?c}" title="${item.title!''}" target="_blank">
            						<img src="${item.coverImageUri!''}" title="${item.title!''}"/>
            						<p>${item.title!''}</p>
            					</a>
            					<p class="price">￥<#if item.salePrice??>${item.salePrice?string("0.00")}</#if><span>原价：￥<#if item.marketPrice??>${item.marketPrice?string("0.00")}</#if></span></p>
            				</li>
                         </#if>
                    </#list>
                </#if>
			</ul>
		</div>
		<div class="clear"></div>
	</section>

 <!--底部footer-->
 <#include "/client/common_footer.ftl" />

</body>
</html>