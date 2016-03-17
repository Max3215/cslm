<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>

<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/cartoon.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />

<style type="text/css">
    .passwordStrength { margin-left: 86px; padding-top: 10px;}
    .passwordStrength b { font-weight: normal; }
    .passwordStrength b, .passwordStrength span { display: inline-block; vertical-align: middle; line-height: 16px; line-height: 18px; height: 16px; }
    .passwordStrength span { width: 45px; text-align: center; background-color: #d0d0d0; border-right: 1px solid #fff; }
    .passwordStrength .last { border-right: none; }
    .passwordStrength .bgStrength { color: #fff; background-color: #71b83d; }
    .registerbox .passwordStrength { margin-left: 8px; }
    .passwordStrength b { font-weight: normal; display: inline-block; margin: 0 8px; color: #f27f02; }
 
</style>

</head>

<body>
<header class="logintop">
  <div class="main pt20 pb20">
  	<a href="/" class="logo"><img src="<#if site??>${site.logoUri!''}</#if>" /></a>
  </div>
</header>

<!-- 忘记密码 -->
<div class="main">
  <section class="fondpassowrdbox">
    <h3 class="fw400 pb20">
      <span class="ml20 fs20 red">个人用户 - 找回密码</span>
      <a class="unl fs14" href="#" style="margin-left:700px;">想起密码？立即登陆></a>
    </h3>
  <form action="/login/retrieve_step2" method="post" id="form">
    <div class="login_dl">
      <i><img src="/client/images/login_arrow.png" height="11"></i>
      <div class="fondpassowrd">      
        <!-- 找回密码步骤 -->
        <p class="fondpassbar">
          <span class="isok">1.验证身份<img src="/client/images/fondpass.png"></span>
          <span class="isok">2.设置新密码<img src="/client/images/fondpass.png"></span>
          <span class="">3.设置成功</span>
        </p>
        <div class="clear"></div>
        
        <input type="hidden" value="${username!''}" name="username">
        <input type="hidden" value="${mobile!''}" name="mobile">
          <!-- 第二步 -->
          <dl class="fondpassstep2">
            <dd class="fpsitem">
              <span class="spsititle ib">设置新密码：</span>
              <input type="password" class="fspass r3 bd" name="password" value="" placeholder="请输入注册时使用的手机号" datatype="s6-20">
              <div class="clear"></div>
            </dd>
            <dd class="fpsitem">
              <span class="spsititle ib">确认密码：</span>
              <input type="password" class="fspass r3 bd" value="" placeholder="请输入短信验证码" datatype="*" recheck="password">
            	<div class="clear"></div>
            </dd>
            <div class="clear"></div>
            <a href="javascript:;" onclick="editPassword();" class="fondpassbut bggreen r3 ib cf fondpassnexto3">提交</a>
          </dl>
          <div class="clear h30"></div>
<script>
	$(document).ready(function(){
	   //初始化表单验证
		    $("#form").Validform({
		        tiptype: 3
		    })
	})

function editPassword(){
	$("#form").submit();
}
</script>


      </div>
    </div>
  </form>
  </section>
  <div class="clear h30"></div>
</div>
<!-- 忘记密码 END -->

<#include "/client/common_footer.ftl">
</body>
</html>
