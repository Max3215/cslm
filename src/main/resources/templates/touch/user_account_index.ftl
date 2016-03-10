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
		<p>账户安全</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 账户安全 -->
  <section class="user_safe">
    <p class="user">账户名称<span>${user.username!''}</span></p>
    <menu>
      <a href="/touch/user/account/edit/name">真实姓名<span>${user.realName!''}</span></a>
      <a href="/touch/user/account/edit/email">邮箱<span>${user.email!'未添加'}</span></a>
      <a href="/touch/user/account/edit/mobile">手机<span>${user.mobile!''}</span></a>
    </menu>
    <menu>
      <a href="/touch/user/account/edit/password">登录密码<span>&nbsp;</span></a>
      <a href="/touch/user/account/edit/payPassword">支付密码<span>&nbsp;</span></a>
    </menu>
  </section>
  <!-- 账户安全 END -->
  
</body>
</html>
