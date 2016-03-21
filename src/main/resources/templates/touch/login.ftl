<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>登录</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner

});
</script>

<script>
$(document).ready(function(){
    changeYzm(); 
   //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    });
    
    $(function(){
        $("#btn_login").click(function(){
            login();
        }); 
    });
})

function login(){
        var username = $("#txt_loginId").val();
        var password = $("#txt_loginPwd").val();
        var yzm = $("#yzm").val();
        
        if (username.length < 6 || password.length < 6)
        {
            alert("用户名或密码长度输入不足");
           // ct.alert({
           //         text: "用户名或密码长度输入不足",
           //         type: "alert"
           // });
            return;
        }
        
        $.ajax({
            type: "post",
            url: "/touch/login",
            data: { "username": username, "password": password, "code": yzm},
            dataType: "json",
            success: function (data) {
                if (data.code == 0) {
                    var url = document.referrer;
                    if(undefined==url || ""==url){
                        window.location.href="/touch/user";
                    }else{
                        window.location.href = url; 
                    }
                } else {
                    alert(data.msg);
                    //ct.alert({
                    //        text: data.msg,
                    //        type: "alert"
                   // });
                }
            }
        });
    }



function changeYzm(){
    var image = document.getElementById("yzm_image");
    image.src = "/code?date="+Math.random()
}
</script>
</head>

<body style="background:#fff;">
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>登录</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 登录 -->
  <section class="login_box">
    <img src="images/logo.png" />
    <input type="text" class="text" placeholder="请输入账号" id="txt_loginId" />
    <input type="password" class="text" placeholder="请输入密码" id="txt_loginPwd"/>
    <p>
      <input class="text" type="text" id="yzm" placeholder="请输入验证码" style="width:50%;margin:20px 0 0 0;">
      <a class="yz_code" href="javascript:changeYzm()"><img id="yzm_image" src="" style="height:35px;width:75px"/></a>
    </p>
    <input type="submit" class="sub" value="登录" id="btn_login"/>
    <p class="login_a">
      <a href="/touch/reg">注册账号</a>
      <a href="/touch/login/password_retrieve">忘记密码？</a>
    </p>
  </section>
  <!-- 登录 END -->

 
  
</body>
</html>
