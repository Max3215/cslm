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

<script type="text/javascript" src="/client/js/goods.js"></script>
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
    bannerCartoon("n_banner_box","a","n_banner_num",300,5000,"","");

	$(".float_box .ewm").hover(function(){
		$(this).next().show();
	},function(){
		$(this).next().hide();
	})
})

function setprice() {
    var p1 = $.trim($('#ParamFiltern_price1').val()), p2 = $.trim($('#ParamFiltern_price2').val())
    if (isNaN(p1) || p1=="") { p1 = 0 }
    if (isNaN(p2) || p2== "") { p2 = 0 }
    var price = p1 + '-' + p2;
    var url = "/list/${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}";
    if (price != "0-0") { url += "_" + price; }
    location.href = url;
}


</script>

</head>

<body>
<!--   顶部   -->
<#include "/client/common_header.ftl" />
	
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/" title="首页">首页</a>
    		<#if category_tree_list??> 
                <#list category_tree_list as item> 
                    &nbsp;&nbsp;&gt;&nbsp;&nbsp; 
                    <a href="/list/${item.id?c}" title="${item.title!''}" >${item.title!''}</a>
                </#list> 
            </#if>
        </p>
	</section>

	<!--banner-->
	<#if list_scroll_ad_list?? && list_scroll_ad_list?size gt 0>
    	<section id="n_banner_box">
    	   <#--
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
    		-->
    		<menu id="banner_sum">
                <#list list_scroll_ad_list as item>
                        <a href="${item.linkUri!''}" target="_blank">
                                  <img src="${item.fileUri!''}" />
                            </a>
                 </#list>
        </menu>
    	</section>
    </#if>
    <!--右边悬浮框-->
	<#include "/client/common_float_box.ftl" />

	<!--main-->
	<section class="main">
		<div class="select_box">
		  <#if brand_list??>
		    <dl>
                <dt>品牌：</dt>
                <dd>
                    <a <#if brandIndex==0>class="sel"</#if> href="${categoryId?c!'0'}-0<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">全部</a>
                
                    <#list brand_list as brand>
                        <td><a href="${categoryId?c!'0'}-${brand_index+1}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if brandIndex==brand_index+1>class="sel"</#if>>${brand.title?trim!''}</a>
                    </#list>
                </dd>
                <div class="clear"></div>
            </dl>
            </#if>
            <!-- 参数开始   -->
            <#if param_list??>
                <#list param_list as param>
                    <#if param.valueList?? && param.valueList?contains(",")>
        			<dl>
        				<dt>${param.title!""}：</dt>
        				<dd>
        				<a href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex><#if param_index==pindex_index>-0<#else>-${pindex!'0'}</#if></#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if param_index_list[param_index]==0>class="sel"</#if>>全部</a>
        					<#--<#if param.valueList??> -->
                                <#list param.valueList?split(",") as value>
                                    <#if value!="">
                                        <a href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex><#if param_index==pindex_index>-${value_index+1}<#else>-${pindex!'0'}</#if></#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if param_index_list[param_index]==value_index+1>class="sel"</#if>>${value?trim!""}</a>
                                    </#if>
                                </#list>
                             
        				</dd>
        				<div class="clear"></div>
        			</dl>
        			</#if>
    			</#list>
            </#if>
		</div><!--  参数结束  -->

		<div class="left_list">
			<div class="tit">
				<menu class="sort">
					<span>排序：</span>
                    <a <#if orderId==0><#if sort_id_list[0]==0>class="sel"<#else>class="sel"</#if></#if>  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-0<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==0><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">综合</a>
                    <a <#if orderId==1><#if sort_id_list[1]==0>class="sel"<#else>class="sel"</#if></#if>  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-1<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==1><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">价格</a>
                    <a <#if orderId==2><#if sort_id_list[2]==0>class="sel"<#else>class="sel"</#if></#if>  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-2<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==2><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">销量</a>
				</menu>
				<div class="price">
					<span>价格区间：</span>
					<input type="text" id="ParamFiltern_price1" class="text" value="<#if priceLow??>${priceLow?string("#.##")}</#if>"  onkeyup="value=value.replace(/[^0-9]/g,'')"/>-
                    <input type="text" id="ParamFiltern_price2" class="text" value="<#if priceHigh??>${priceHigh?string("#.##")}</#if>"  onkeyup="value=value.replace(/[^0-9]/g,'')"/>
                    <input type="submit" class="sub" onclick="setprice()" value="确定" />
				</div>
				<div class="page">
					<span><b><#if goods_page.totalPages==0>0<#else>${goods_page.number+1}</#if></b>/${goods_page.totalPages!"0"}</span>
					
					<#if goods_page.number == 0>
                         <a href="javascript:;">上一页</a>
                    <#else>
                         <a href="${categoryId?c!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${(goods_page.number-1)?c}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">上一页</a>
                    </#if>
                            
                   <#if goods_page.number+1 == goods_page.totalPages || goods_page.totalPages==0>
                        <a href="javascript:;">下一页</a>
                    <#else>
                        <a href="${categoryId?c!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${(goods_page.number+1)?c}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">下一页</a>
                    </#if>
				</div>
			</div>
<script>
function showmsg(){
    layer.alert("库存不足！");
    return;
}
</script>
			<!--    商品列表   -->
			<ul class="pro_list">
			    <#if goods_page?? && goods_page.content?size gt 0>
                    <#list goods_page.content as goods>
        				<li>
        					<a href="/goods/${goods.id?c}" class="a1" target="_blank">
        					     <#if goods.tagId??><i><img src="${goods.tagImg!''}" /></i></#if>
        						<img src="${goods.coverImageUri!''}" width="200" height="201" title="${goods.goodsTitle!''}"/>
        						<p>${goods.goodsTitle!""}</p>
        					</a>
        					<p class="red">${goods.distributorTitle!''}</p>
        					<p class="price">￥${goods.goodsPrice?string("#.##")}<span>原价：￥${goods.goodsMarketPrice?string("#.##")}</span></p>
        					<menu class="btn">
        					   <#if goods.leftNumber?? && goods.leftNumber gt 0>
                                    <a onclick="addCart(${goods.id?c});" title="加入购物车" class="car" id="addCart"></a>
                                <#else>
                                    <a  onclick="showmsg();"  title="加入购物车" class="car" id="addCart"></a>
                                </#if>
                                <#if goods.leftNumber?? && goods.leftNumber gt 0>
                                   <#if goods.isDistribution?? && goods.isDistribution == true>
                                       <a onclick="byGoodsNow(${goods.id?c});"  title="预购商品" class="buy" id="proGoods">立即预购</a>
                                   <#else>
                                       <a onclick="byGoodsNow(${goods.id?c});" id="buyNow"  title="立即购买" class="buy">立即购买</a>
                                   </#if>
                               <#else>
                                   <a href="javascript:; " onclick="showmsg();"  title="立即购买" class="buy">立即购买</a>
                               </#if>
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
                         <a href="${categoryId?c!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${(goods_page.number-1)?c}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">上一页</a>
                    </#if>
				    <#if goods_page.totalPages gt 0>
                        <#list 1..goods_page.totalPages as page>
                            <#if page <= 3 || (goods_page.totalPages-page) < 3 || (goods_page.number+1-page)?abs<3 >
                                <#if page == goods_page.number+1>
                                    <a class="sel" href="javascript:;">${page}</a>
                                <#else>
                                    <a href="${categoryId?c!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${(page-1)?c}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">${page}</a> 
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
                        <a href="${categoryId?c!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${(goods_page.number+1)?c}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">下一页</a>
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