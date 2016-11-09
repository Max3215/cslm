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
<script type="text/javascript">

</script>
</head>

<body class="whitebg">
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>支付超时</p>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <section class="pay_overtime">
    <h3>抱歉，订单支付时间已过！</h3>
    <div class="operate"><a href="/touch">返回首页</a></div>
  </section>
  
  
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
</body>
</html>
