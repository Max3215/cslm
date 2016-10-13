<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>注册</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />
<link href="/touch/css/style.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
    
    $("#form1").Validform({
        tiptype: 1 
    });


});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>注册</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 注册 -->
  <section class="modify_mm">
    <form action="/touch/reg" method="post" id="form1">
    	<input type="hidden" value="<#if goodsId??>${goodsId?c}</#if>" name="goodsId"/>
         <p style="color: #F00">${error!''}</p>
        <div>
            <input type="text" name="username" class="text" placeholder="请输入账号" ajaxurl="/reg/check/username" datatype="s6-12">
        </div>
        <div>
        <input type="text" name="mobile" class="text" placeholder="请输入手机号" errormsg="手机号格式错误" ajaxurl="/reg/check/mobile" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/">
        </div>
        <#--
        <input type="text" class="text fl" style="width: 50%;" placeholder="请输入验证码">
        <input type="submit" class="sub" value="发送验证码" style="width:40%; font-size:0.8em;">
        <div class="clear"></div>
        -->
        <div>
            <input class="text" name="password" type="password" placeholder="请输入新密码" datatype="s6-20">
        </div>
        <div>
            <input type="password" class="text" placeholder="请确认新密码" datatype="*" recheck="password">
        </div>
        <input class="sub" type="submit" value="确定" style="background:#39bee9;">
    </form>
  </section>
  <!-- 注册 END -->

 
  
</body>
</html>
