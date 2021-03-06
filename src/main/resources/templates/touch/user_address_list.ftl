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
<script src="/layer/layer.js"></script>
<script src="/touch/js/user.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
  $(".address_list a").click(function(){
    $(this).addClass("act").siblings().removeClass("act");
  })
});

function getUrl(){
       document.location = "http://www.chinacslm.cc/touch/user";
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user" class="back"></a>
		<p>收货地址</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 收货地址列表 -->
  <#if address_list??>
        <#list address_list as address>
          <section class="address_list">
            <div class="tit">
              <p>收货人信息</p>
              <#if address.isDefaultAddress?? && address.isDefaultAddress =true>
              <a  class="on">默认</a>
              <#else>
              <a onclick="defaultAddr(${address.id?c})">设为默认</a>
              </#if>
              <a href="/touch/user/address/update?id=${address.id?c}"></a>
              <a onclick="deleteAddr(${address.id?c})" ></a>
            </div>
            <p class="name">${address.receiverName!''}&nbsp;&nbsp;&nbsp;&nbsp;${address.receiverMobile!''}</p>
            <p class="add">${address.province!''}${address.city!''}${address.disctrict!''}${address.detailAddress!''}</p>
          </section>
        </#list>
  </#if>
  <a href="/touch/user/address/update" class="add_address_btn">添加新地址</a>
  <div style="height:1rem;"></div>
  <!-- 收货地址列表 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1" href="/touch/disout">平台首页</a>
	        <#if DISTRIBUTOR_ID??>
            <a class="a5" href="/touch">店铺首页</a>
            </#if>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
