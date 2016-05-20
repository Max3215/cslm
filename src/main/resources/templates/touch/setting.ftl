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
		<p>设置</p>
		<a href="#" class="news"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 设置选项 -->
  <menu class="m_other" style="margin-bottom:0;">
    <a href="/touch/user/account/show">
      <img src="/touch/images/m_icon06.png" />
      <span>个人资料</span>
      <font>&gt;</font>
    </a>
    <a href="/touch/user/account/edit/password">
      <img src="/touch/images/m_icon07.png" />
      <span>修改密码</span>
      <font>&gt;</font>
    </a>
    <#-->
    <a href="#">
      <img src="/touch/images/m_icon08.png" />
      <span>清除缓存</span>
      <font>&gt;</font>
    </a>
    -->
    <div style="height:0.24rem;"></div>
    <a href="/touch/info/list/12">
      <img src="/touch/images/m_icon09.png" />
      <span>帮助中心</span>
      <font>&gt;</font>
    </a>
    <#--
    <a href="#">
      <img src="/touch/images/m_icon10.png" />
      <span>关于超市</span>
      <font>&gt;</font>
    </a>
    -->
  </menu>
  <a href="/touch/logout" class="add_address_btn">退出当前账户</a>
  <div style="height:1rem;"></div>
  <!-- 设置选项 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
