<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if productCategory??>${productCategory.title!''}-</#if>超市联盟</title>
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 
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

var pageIdx = 1;
function loadMore()
{
    $.ajax({
        type:"post",
        url:"/touch/list/more/${categoryId?c!'1'}-${brandIndex?c!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-${orderId?c!'0'}<#if sort_id_list??><#list sort_id_list as sortId>-${sortId!'0'}</#list></#if>-" + pageIdx,
        success:function(data){
            if ("" == data)
            {
                $("#a-more").css("display", "none");
            }
            else
            {
                $("#goods-menu").append(data);
                pageIdx++;
            }
        }
    });
}

</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p><#if productCategory??>${productCategory.title!''}</#if></p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 商品筛选栏 -->
  <section class="pro_screen">
    <td <#if orderId==0><#if sort_id_list[0]==0>class="act"<#else>class="act"</#if></#if>><a  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-0<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==0><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>"><span>综合</span></a></td>
    <td <#if orderId==1><#if sort_id_list[1]==0>class="act"<#else>class="act"</#if></#if>><a  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-1<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==1><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>"><span>价格</span></a></td>
    <td <#if orderId==2><#if sort_id_list[2]==0>class="act"<#else>class="act"</#if></#if>><a  href="${categoryId?c!'0'}-${brandIndex!'0'}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-2<#if sort_id_list??><#list sort_id_list as sortId><#if sortId_index==2><#if sortId==0>-1<#else>-0</#if><#else>-${sortId!'0'}</#if></#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>"><span>销量</span></a></td>
    <a href="javascript:void(0)" onclick="$('.screen_box').fadeIn(300);">筛选</a>
  </section>
  <div style="height:0.6rem;"></div>
  <!-- 商品筛选栏 END -->

  <!-- 弹出筛选 -->
  <aside class="screen_box">
    <a href="javascript:void(0);" onclick="$(this).parent().fadeOut(300);" class="close"></a>
    <div class="content">
      <p class="top">商品筛选</p>
      <menu>
        <p>品牌：</p>
        <#if brand_list??>
          <a href="${categoryId?c!'0'}-0<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-${orderId!'0'}<#if sort_id_list??><#list sort_id_list as sortId>-${sortId!'0'}</#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if brandIndex==0>class="sel"</#if>>全部</a>
          <#list brand_list as brand>
                <a href="${categoryId?c!'0'}-${(brand_index+1)?c}<#list param_index_list as pindex>-${pindex?c!'0'}</#list>-${orderId!'0'}<#if sort_id_list??><#list sort_id_list as sortId>-${sortId!'0'}</#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if brandIndex==brand_index+1>class="act"</#if>>${brand.title?trim!''}</a>
          </#list>
      </#if>
      </menu>
      <#if param_list??>
        <#list param_list as param>
          <menu>
                <#if param.valueList?? && param.valueList?contains(",")>
                <p>${param.title!""}：</p>
                    <#list param.valueList?split(",") as value>
                        <#if value!="">
                            <a href="${categoryId?c!'0'}-${brandIndex?c!'0'}<#list param_index_list as pindex><#if param_index==pindex_index>-${(value_index+1)?c}<#else>-${pindex?c!'0'}</#if></#list>-${orderId!'0'}<#if sort_id_list??><#list sort_id_list as sortId>-${sortId!'0'}</#list></#if>-${pageId!'0'}-${leftId!'0'}<#if priceLow?? && priceHigh??>_${priceLow?string("#.##")}-${priceHigh?string("#.##")}</#if>" <#if param_index_list[param_index]==value_index+1>class="act"</#if>>${value?trim!""}</a>
                        </#if>
                    </#list>
                 </#if>
          </menu>
           </#list>
        </#if>
    </div>
  </aside>
  <!-- 弹出筛选 END -->

  <!-- 商品类表 -->
  <section class="pro_hot">
  	<menu id="goods-menu">
  	     <#if goods_page?? && goods_page.content?size gt 0>
            <#list goods_page.content as goods>
                    <a href="/touch/goods/${goods.id?c}" class="a1">
                        <img src="${goods.coverImageUri!''}"/>
                        <p>${goods.goodsTitle!""}</p>
                        <p >￥${goods.goodsPrice?string("#.##")}</p>
                    </a>
            </#list>
          <#else>
          <div style="text-align: center; padding: 15px;">此类商品正在扩充中，敬请期待！</div>
        </#if> 
  	</menu>
  	<#if goods_page?? && goods_page.content?size gt 0>
   <a id="a-more" class="grey_more" href="javascript:loadMore();"><img src="/touch/images/more.png" /></a>
   </#if>
  </section>
  <!-- 商品类表 END -->
<style>
.grey_more {
    width:94%;
    display:block;
    border:none;
    font-family:"微软雅黑";
    font-size:1em;
    cursor:pointer;
    text-align:center;
    color:#fff;
    line-height:40px;
    height:40px;
    margin:20px auto 30px;}
</sctyle>
  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 sel" href="/touch/disout">平台首页</a>
	        <a class="a2" href="/touch/category/list">商品分类</a>
	        <a class="a3" href="/touch/cart">购物车</a>
	        <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
