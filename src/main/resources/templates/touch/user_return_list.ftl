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
<script src="/touch/js/search.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
    //indexBanner("box","sum",300,5000,"num");//Banner
    var url = '/touch/user/return/list_more';
    $('#return_list').refresh(url,"#return_list",0);
});
</script>
<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user" class="back"></a>
		<p>退货列表</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->

    <div id="return_list">
    <#if return_page??>
    <#list return_page.content as return>
    <section class="order_list">
        <ul>
          <p class="number">订单号：${return.orderNumber!''}<span></span></p>
          <li>
            <a href="/touch/goods/${return.goodsId?c!''}" class="pic"><img src="${return.goodsCoverImageUri!''}"></a>
            <div class="info">
              <a href="/touch/goods/${return.goodsId?c!''}">${return.goodsTitle!''}</a>
              <p>价格：￥<#if return.goodsPrice??>${return.goodsPrice?string('0.00')}</#if></p>
              <p>数量：${return.returnNumber!'0'}</p>
              <p>申请时间：${return.returnTime!''}</p>
            </div>
            <div class="clear"></div>
          </li>
        </ul>
        <div class="btns">
          <span><#if return.statusId==0>待审核<#elseif return.statusId=1>已批准<#else>未通过</#if></span>
        </div>
    </section>
    </#list>
    </#if>
    </div>
    
      <div style="height:0.88rem;"></div>
      <section class="comfooter tabfix">
            <menu>
                <a class="a1" href="/touch/disout">平台首页</a>
                <a class="a2" href="/touch/category/list">商品分类</a>
                <a class="a3" href="/touch/cart">购物车</a>
                <a class="a4 sel" href="/touch/user">会员中心</a>
          </menu>
      </section>
</body>
</html>
