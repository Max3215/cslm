<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if productCategory??>${productCategory.title!''}-</#if>超市联盟</title>
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 
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
        url:"/touch/search?keywords=${keywords!''}&page=" + pageIdx,
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
		<p><#if keywords??>${keywords!''}</#if></p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
	
	<section class="pro_screen">
        <a <#if st==0>class="act"</#if> href="/touch/search?keywords=${keywords!''}&page=0&st=0&<#if sd?? && sd==0>sd=1<#else>sd=0</#if>"><span>销量</span></a>
         <a <#if st==1>class="act"</#if> href="/touch/search?keywords=${keywords!''}&page=0&st=1&<#if sd?? && sd==0>sd=1<#else>sd=0</#if>"><span>价格</span></a>
         <a <#if st==2>class="act"</#if> href="/touch/search?keywords=${keywords!''}&page=0&st=2&<#if sd?? && sd==0>sd=1<#else>sd=0</#if>"><span>上架时间</span></a>
    </section>

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
