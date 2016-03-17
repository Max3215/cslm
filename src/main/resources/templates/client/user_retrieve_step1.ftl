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
      <a class="unl fs14" href="/login" style="margin-left:700px;">想起密码？立即登陆></a>
    </h3>
    
  <form action="/login/retrieve_step1" id="form" method="post">
    <div class="login_dl">
      <i><img src="/client/images/login_arrow.png" height="11"></i>
      <div class="fondpassowrd">      
        <!-- 找回密码步骤 -->
        <p class="fondpassbar">
          <span class="isok">1.验证身份<img src="/client/images/fondpass.png"></span>
          <span class="">2.设置新密码<img src="/client/images/fondpass.png"></span>
          <span class="">3.设置成功</span>
        </p>
        <div class="clear"></div>
        
          <!-- 第一步 -->
          <dl class="fondpassstep1 active">
          	<dd class="fpsitem">
              <span class="spsititle">账号：</span>
              <input type="text" name="username" id="username" class="fstext r3 bd" value="${username!''}" placeholder="请输入账号">
            </dd>
            <dd class="fpsitem">
              <span class="spsititle">手机号：</span>
              <input type="text" name="mobile" id="mobile" class="fstext r3 bd" value="${mobile!''}" placeholder="请输入注册时使用的手机号">
            </dd>
            <dd class="fpsitem">
              <span class="spsititle">验证码：</span>
              <input type="text" name="smsCode" class="fstext r3 bd" value="" placeholder="请输入短信验证码">
              <input id="smsCodeBtn" class="r3 ib spsigetcode bggreen cf" style="border:none;" readonly="readonly"  value="获取验证码" />
            </dd>
            <#--
            <span style="color: #F00;padding: 0 80px;">
					<#if errCode??>
				        <#if errCode==1>
				            验证码错误
				        <#elseif errCode==4>
				            短信验证码错误
				        </#if>
				    </#if>
				</span>
				-->
				<span class="Validform_checktip Validform_wrong">
                           <#if errCode??>
				        		<#if errCode==1>
						            验证码错误
					             <#elseif errCode==3>
						            短信验证码过期
						        <#elseif errCode==4>
						            短信验证码错误
						        </#if>
						    </#if>
                    </span>
            <div class="clear"></div>
            <a href="javascript:;" onclick="goNext()" class="fondpassbut bggreen r3 ib cf fondpassnexto2">下一步</a>
          </dl>
          <div class="clear h30"></div>
<script>
var seed=60;    //60秒  
var t1=null; 

$("#smsCodeBtn").bind("click", function() {  
        
        var username = $("#username").val();
        var mobile = $("#mobile").val();
        
        var re = /^1\d{10}$/;
        
        if(undefined== username || ""== username){
        	alert("请输入账号");
        	return ;
        }
        console.debug(mobile);
        if (!re.test(mobile)) {
            alert("请输入正确的手机号");
            return;
        }
        
        $("#smsCodeBtn").attr("disabled","disabled"); 
        
        $.ajax({  
            url : "/reg/smscode",  
            async : true,  
            type : 'GET',  
            data : {"username":username,"mobile": mobile},  
            success : function(data) {  
                if(data.code==1){
                	alert(data.msg);
                }
                
                if(data.statusCode == '000000')
                {  
                    t1 = setInterval(tip, 1000);  
                }
                else
                {
                    $("#smsCodeBtn").removeAttr("disabled");
                }
            },  
            error : function(XMLHttpRequest, textStatus,  
                    errorThrown) {  
                alert("error");
            }  
  
        });
        
    }); 

function enableBtn()
{  
    $("#smsCodeBtn").removeAttr("disabled");   
} 

function tip() 
{  
    seed--;  
    if (seed < 1) 
    {  
        enableBtn();  
        seed = 60;  
        $("#smsCodeBtn").val('点击获取短信验证码');  
        var t2 = clearInterval(t1);  
    } else {  
        $("#smsCodeBtn").val(seed + "秒后重新获取");  
    }  
} 


//	$(document).ready(function(){
//	    changeYzm(); 
//	})
//	function changeYzm(){
//	    var image = document.getElementById("yzm_image");
//	    image.src = "/code?date="+Math.random()
//	}
	
	function goNext(){
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
