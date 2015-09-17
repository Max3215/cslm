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
<title><#if productCategory??>${productCategory.title!''}-</#if>超市联盟</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $("#add").citySelect({
    nodata:"none",
    prov: "云南",
    city: "昆明",
    <#if address?? && address.disctrict??>dist: "${address.disctrict!''}",</#if>
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
	adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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
    var url = "/list/${categoryId!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}";
    if (price != "0-0") { url += "_" + price; }
    location.href = url;
}

<!--  加入购物车   -->
function cartInit(goodsId){
   
    $.ajax({
        type: "get",
        url: "/goods/incart",
        data: {"id":goodsId},
        success: function (data) { 
            if(data.msg){
                alert(data.msg);
                return;
            }
            window.open("/cart/init?id="+goodsId);
        }
    });
}

<!--  立即购买   -->
function byNow(goodsId){
   
    $.ajax({
        type: "get",
        url: "/goods/incart",
        data: {"id":goodsId},
        success: function (data) { 
            if(data.msg){
                alert(data.msg);
                return;
            }
            window.location.href = "/order/byNow/"+goodsId;
        }
    });
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
                    <a href="/list/${item.id}" title="${item.title!''}" >${item.title!''}</a>
                </#list> 
            </#if>
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
		  <#if brand_list??>
		    <dl>
                <dt>品牌：</dt>
                <dd>
                    <a <#if brandIndex==0>class="sel"</#if> href="${categoryId!'0'}-0<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">全部</a>
                
                    <#list brand_list as brand>
                        <td><a href="${categoryId!'0'}-${brand_index+1}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if brandIndex==brand_index+1>class="sel"</#if>>${brand.title?trim!''}</a>
                    </#list>
                </dd>
                <div class="clear"></div>
            </dl>
            </#if>
            <!-- 参数开始   -->
            <#if param_list??>
                <#list param_list as param>
        			<dl>
        				<dt>${param.title!""}：</dt>
        				<dd>
        				<a href="${categoryId!'0'}-${brandIndex!'0'}<#list param_index_list as pindex><#if param_index==pindex_index>-0<#else>-${pindex!'0'}</#if></#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if param_index_list[param_index]==0>class="sel"</#if>>全部</a>
        					<#if param.valueList??>
                                <#list param.valueList?split(",") as value>
                                    <#if value!="">
                                        <a href="${categoryId!'0'}-${brandIndex!'0'}<#list param_index_list as pindex><#if param_index==pindex_index>-${value_index+1}<#else>-${pindex!'0'}</#if></#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if param_index_list[param_index]==value_index+1>class="sel"</#if>>${value?trim!""}</a>
                                    </#if>
                                </#list>
                             </#if>
        				</dd>
        				<div class="clear"></div>
        			</dl>
    			</#list>
            </#if>
		</div><!--  参数结束  -->

		<div class="left_list">
			<div class="tit">
				<menu class="sort">
					<span>排序：</span>
					<a <#if orderId==0>class="sel"</#if> href="${categoryId!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex!'0'}</#list>-0-<#if orderId!=0 || soldId==1>0<#else>1</#if>-${priceId!'0'}-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>"><#if orderId==0><#if soldId==0>销量↓<#else>销量↑</#if><#else>销量</#if></a>
                    <a <#if orderId==1>class="sel"</#if> href="${categoryId!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex!'0'}</#list>-1-${soldId!'0'}-<#if orderId!=1 || priceId==1>0<#else>1</#if>-${timeId!'0'}-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>"><#if orderId==1><#if priceId==0>价格↓<#else>价格↑</#if><#else>价格</#if></a>
				</menu>
				<div class="price">
					<span>价格区间：</span>
					<input type="text" id="ParamFiltern_price1" class="text" value="<#if priceLow??>${priceLow?string("#.##")}</#if>"/>-
                    <input type="text" id="ParamFiltern_price2" class="text" value="<#if priceHigh??>${priceHigh?string("#.##")}</#if>"/>
                    <input type="submit" class="sub" onclick="setprice()" value="确定" />
				</div>
				<div class="page">
					<span><b><#if goods_page.totalPages==0>0<#else>${goods_page.number+1}</#if></b>/${goods_page.totalPages!"0"}</span>
					
					<#if goods_page.number+1 == goods_page.totalPages || goods_page.totalPages==0>
                        <a href="javascript:;">上一页</a>
                    <#else>
                        <a href="${categoryId!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${goods_page.number+1}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">上一页</a> <#-- goods_page.number+1 -->
                    </#if>
                            
                    <#if goods_page.number+1 == 1>
                        <a href="javascript:;">下一页</a>
                    <#else>
                        <a href="${categoryId!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${goods_page.number-1}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">下一页</a> <#-- goods_page.number-1 -->
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
        					<p class="price">￥${goods.salePrice?string("#.##")}<span>原价：￥${goods.marketPrice?string("#.##")}</span></p>
        					<menu class="btn">
        						<a href="javascript:cartInit(${goods.id?c});" class="car" title="加入购物车"></a>
        						<a href="javascript:byNow(${goods.id?c});" class="buy">立即购买</a> 
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
                         <a href="${categoryId!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${goods_page.number-1}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">上一页</a>
                    </#if>
				    <#if goods_page.totalPages gt 0>
                        <#list 1..goods_page.totalPages as page>
                            <#if page <= 3 || (goods_page.totalPages-page) < 3 || (goods_page.number+1-page)?abs<3 >
                                <#if page == goods_page.number+1>
                                    <a class="sel" href="javascript:;">${page}</a>
                                <#else>
                                    <a href="${categoryId!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${page-1}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">${page}</a> 
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
                        <a href="${categoryId!'0'}-${brandIndex!0}<#list param_index_list as pindex>-${pindex!'0'}</#list>-${orderId!'0'}-${soldId!'0'}-${priceId!'0'}-${timeId!'0'}-${goods_page.number+1}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>">下一页</a>
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
            					<p class="price">￥<#if item.salePrice??>${item.salePrice?string("#.##")}</#if><span>原价：￥<#if item.marketPrice??>${item.marketPrice?string("#.##")}</#if></span></p>
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