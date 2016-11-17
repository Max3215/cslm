<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<title>积分兑换-${goods.goodsTitle!''}</title>
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/client/js/goods_comment_consult.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript" src="/client/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".click_a").click(function(){
		if($(this).next().is(":visible")==false){
			$(this).next().slideDown(300);
		}else{
			$(this).next().slideUp(300);
		}
	});//选择超市下拉效果

	$("#nav_down li").hover(function(){
		$(this).find(".nav_show").fadeIn(10);
	},function(){
		$(this).find(".nav_show").stop(true,true).fadeOut(10);
	})	

	$(".float_box .ewm").hover(function(){
		$(this).next().show();
	},function(){
		$(this).next().hide();
	})
})
</script>

</head>

<body>
	<#include "/client/common_header.ftl" />
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/">首页</a>
		&nbsp;&nbsp;&gt;&nbsp;&nbsp; 
		<a href="/pointGoods/list">积分兑换</a>
		&nbsp;&nbsp;&gt;&nbsp;&nbsp; 
		${goods.goodsTitle!''}
		</p>
	</section>

	<!--右边悬浮框-->
	<#include "/client/common_float_box.ftl" />

	<!--main-->
	<div class="main">
		<!-- 积分商品 -->
		<div class="integralpro_info">
			<div class="lfpic"><img src="${goods.imgUrl!''}" /></div>
			<div class="midinfo">
				<h2>${goods.goodsTitle!''}</h2>
				<p class="subtitle">${goods.subGoodsTitle!''}</p>
				<div class="price">
					<p>所需积分：<span>${goods.point!'0'}积分</span></p>
					<p>（您当前的可用积分为：<#if user??>${user.totalPoints!'0'}<#else>0</#if>）</p>
				</div>
				<#if goods.leftNumber?? && goods.leftNumber gt 0>
				<a onclick="addPoint(${goods.id?c})" class="btn">立即兑换</a>
				<#else>
                <a  class="btn ed">已兑换完</a>
                </#if>
			</div>
			<div class="lrtext">
				<p class="tit">兑换说明</p>
				<p>${goods.changeDetail!''}</p>
			</div>
		</div>
		<!-- 积分商品 end -->

		<div class="info_box">
			<div class="clear"></div>
			<section class="info_left">
				<p class="tit">
					<a class="sel">商品详情</a>
				</p>
				<div class="text_pic">
					${goods.detail!''}
				</div>
			</section>
			<div class="right_hot">
				<p class="tit">大家都在看</p>
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
		</div>
	</div>


	 <#include "/client/common_footer.ftl" />

</body>
</html>