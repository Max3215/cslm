<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
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
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>编辑收货地址</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 收货人信息填写 -->
  <form>
  <section class="add_consignee">
    <p>收货人姓名</p>
    <input type="text" class="text" />
    <p>收货人电话</p>
    <input type="text" class="text" />
    <p>邮政编码</p>
    <input type="text" class="text" />
    <p>收货地址</p>
    <div class="address">
          <select class="fl"><option>省</option></select>
          <select class="fr"><option>市</option></select>
          <div class="clear"></div>
          <select style="width:100%;"><option>区</option></select>
          <p>详细地址</p>
          <textarea></textarea>
    </div>
    <input type="submit" class="sub" value="保存" />
  </section>
  <!-- 收货人信息填写 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 sel" href="#">平台首页</a>
	        <a class="a2" href="#">商品分类</a>
	        <a class="a3" href="#">购物车</a>
	        <a class="a4" href="#">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
