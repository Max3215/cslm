<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<title><#if productCategory??>${productCategory.title!''}-</#if>联超商城</title>
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script type="text/javascript" src="/client/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#add").citySelect({
    nodata:"none",
    prov: "云南",
    required:false
    });


	$(".click_a").click(function(){
		if($(this).next().is(":visible")==false){
			$(this).next().slideDown(300);
		}else{
			$(this).next().slideUp(300);
		}
	});//选择超市下拉效果

	navDownList("nav_down","li",".nav_show");
	menuDownList("mainnavdown","#nav_down",".a2","sel");
  //  bannerCartoon("n_banner_box","a","n_banner_num",300,5000,"","");

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
                    &nbsp;&nbsp;&gt;&nbsp;&nbsp; 
                    <a >积分兑换</a>
        </p>
	</section>

	<!--banner-->
	<!-- 积分商城 广告位 -->
    <section class="main integralmall_top">
        <#if point_ad_list?? && point_ad_list?size gt 0>
        <img src="${point_ad_list[0].fileUri!''}" />
        </#if>
        <!-- 未登录 -->
        <#if !user??>
        <div class="user">
            <p class="pic"><img src="/client/images/headimg.jpg" /></p>
            <p class="name"><a href="/login">请登录账号</a></p>
            <p class="number">0</p>
            <p class="text">可用积分</p>
        </div>
        <#else>
        <!-- 登录后 -->
        <div class="user">
            <p class="pic"><img src="${user.headImageUri!''}" /></p>
            <p class="name">${user.username!''}</p>
            <p class="number">${user.totalPoints!'0'}</p>
            <p class="text">可用积分</p>
        </div>
        </#if>
    </section>
    <!-- 积分商城 广告位 end -->
    
    <!--右边悬浮框-->
	<#include "/client/common_float_box.ftl" />

	<!--main-->
	<section class="main">
        <div class="clear"></div>
		<div class="left_list change">
			<div class="tit">
			 <div class="page">
                    <span><b><#if goods_page.totalPages==0>0<#else>${goods_page.number+1}</#if></b>/${goods_page.totalPages!"0"}</span>
                    <#if goods_page.number == 0>
                         <a href="javascript:;">上一页</a>
                    <#else>
                         <a href="/pointGoods/list?page=${(goods_page.number-1)?c}">上一页</a>
                    </#if>
                            
                   <#if goods_page.number+1 == goods_page.totalPages || goods_page.totalPages==0>
                        <a href="javascript:;">下一页</a>
                    <#else>
                        <a href="/pointGoods/list?page=${(goods_page.number+1)?c}">下一页</a>
                    </#if>
                </div>
			</div>
			<!--    商品列表   -->
			<ul class="pro_list">
			    <#if goods_page?? && goods_page.content?size gt 0>
                    <#list goods_page.content as goods>
                        <li>
                            <a href="/point/goods/detail?id=${goods.id?c}" class="a1">
                                <img src="${goods.imgUrl!''}">
                                <p>${goods.goodsTitle!''}</p>
                            </a>
                            <p class="number"><b>${goods.point!'0'}</b>积分</p>
                            <#if goods.leftNumber?? && goods.leftNumber gt 0>
                            <a onclick="addPoint(${goods.id?c})" class="dh_btn">立即兑换</a>
                            <#else>
                            <a  class="dh_btn ed">已兑换完</a>
                            </#if>
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
                         <a href="/pointGoods/list?page=${(goods_page.number-1)?c}">上一页</a>
                    </#if>
				    <#if goods_page.totalPages gt 0>
                        <#list 1..goods_page.totalPages as page>
                            <#if page <= 3 || (goods_page.totalPages-page) < 3 || (goods_page.number+1-page)?abs<3 >
                                <#if page == goods_page.number+1>
                                    <a class="sel" href="javascript:;">${page}</a>
                                <#else>
                                    <a href="/pointGoods/list?page=${(page-1)?c}">${page}</a> 
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
                        <a href="/pointGoods/list?page=${(goods_page.number+1)?c}">下一页</a>
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
            					<a href="/goods/${item.id?c}" title="${item.goodsTitle!''}" target="_blank">
            						<img src="${item.coverImageUri!''}" title="${item.goodsTitle!''}"/>
            						<p>${item.goodsTitle!''}</p>
            					</a>
            					<p class="price"><#if item.goodsPrice??>￥${item.goodsPrice?string("#.##")}</#if><span>原价：￥<#if item.goodsMarketPrice??>${item.goodsMarketPrice?string("#.##")}</#if></span></p>
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