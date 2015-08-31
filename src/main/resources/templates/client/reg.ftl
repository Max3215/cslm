<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="" />
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>

<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />

<script>
$(document).ready(function(){
    changeYzm(); 
   //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    });
});

<!--  验证码   -->
function changeYzm(){
    var image = document.getElementById("yzm_image");
    image.src = "/code?date="+Math.random()
}
</script>
</head>

<body>
<header class="logintop">
  <div class="main pt20 pb20">
    <a href="/"><img src="<#if site??>${site.logoUri!''}</#if>" /></a>
  </div>
</header>
    <form id="form1" method="post" action="/reg">
        <div class="logingbg">
             <section class="loginbox">
                <div>
                    <p>请输入用户名</p>
                    <input class="text" name="username" type="text" datatype="s6-20" ajaxurl="/reg/check/username"/>
                    <span class="Validform_checktip Validform_wrong" style=""></span>
                </div>
                <div>
                    <p>请输入手机号</p>
                    <input id="mobileNumber" class="text" name="mobile" type="text" datatype="m"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div>
                    <p>请输入密码</p>
                    <input class="text" name="password" type="password" datatype="s6-20"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div>
                    <p>请确认密码</p>
                    <input class="text" type="password" datatype="*" recheck="password"/>
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div>
                    <p>请输入验证码</p>
                    <div class="clear"></div>
                    <input class="text fl" type="text" name="code" style="width:35%;" />
                    <a class="yzm01" href="javascript:changeYzm()"><img id="yzm_image" src="" width="100px;" height="37px;"/></a>
                    <a class="yzm02" href="javascript:changeYzm()">看不清楚？换一张</a>
                    <span class="Validform_checktip Validform_wrong"></span>
                </div>
                <div class="clear h15"></div>
                <p class="pb10">
                <input type="checkbox" checked="checked"/>
                <span>我已阅读并同意<a href="#">《超市联盟用户协议》</a></span>
                <span class="absolute-r">已有账号？<a href="/login">点击登录</a></span>
                </p>
                <input type="submit" class="sub" value="注册" />
                <div class="clear h15"></div>
             </section>
        </div>
    </form><!--logingbg END-->
    
  <!--  底部  -->
  <#include "/client/common_footer.ftl" />
</body>
</html>
