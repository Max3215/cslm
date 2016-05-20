<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>找回密码</title>
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
<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/message.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	$("#form").Validform({
        tiptype: 1,
        ajaxPost:true,
        callback:function(data){
            if(data.code==1)
            {
                ct.alert({
                    text: data.msg,
                    type: "alert"
                });
                 window.location.href="/touch/user"
            }else{
                ct.alert({
                    text: data.msg,
                    type: "alert"
                });
            }
        }
    });
	
});
</script>
<script>
var seed=60;    //60秒  
var t1=null; 

function smsSend() {  
        
        var username = $("#username").val();
        var mobile = $("#mobile").val();
        
        var re = /^1\d{10}$/;
        
        if(undefined== username || ""== username){
            ct.alert({
                text: "请输入账号",
                type: "alert"
            });
            return ;
        }
        
        if (!re.test(mobile)) {
            ct.alert({
                text: "请输入正确的手机号",
                type: "alert"
            });
            return;
        }
        
        $("#smsCodeBtn").attr("disabled","disabled"); 
        
        $.ajax({  
            url : "/touch/reg/smscode",  
            async : true,  
            type : 'GET',  
            data : {"username":username,"mobile": mobile},  
            success : function(data) {  
                if(data.code==1){
                     ct.alert({
                            text: data.msg,
                            type: "alert"
                        });
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
        
    }
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
        $("#smsCodeBtn").val('获取验证码');  
        var t2 = clearInterval(t1);  
    } else {  
        $("#smsCodeBtn").val(seed + "秒后重新获取");  
    }  
} 
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>找回密码</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 修改密码 -->
  <form id="form" method="post" action="/touch/login/password_retrieve">
  <section class="modify_mm">
    <input type="text" class="text" name="username" id="username" placeholder="用户名"  datatype="*"/>
  	<input type="text" class="text" name="mobile" placeholder="手机号码" id="mobile"  datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/"/>
  	<input type="text" class="text fl" name="smsCode" style="width: 50%;" placeholder="请输入验证码" datatype="*">
  	<input id="smsCodeBtn" onclick="smsSend();" class="sub" style="width:40%; font-size:0.8em;" readonly="readonly"  value="获取验证码" />
  	<div class="clear"></div>
  	<input type="password" class="text" placeholder="请输入新密码"  name="password" datatype="s6-20"/>
  	<input type="password" class="text" placeholder="请确认新密码"  datatype="*" recheck="password"/>
  	<input class="sub" type="submit" value="确定">
  </section>
  </form>
  <!-- 修改密码 END -->

  
  <!-- 底部 -->
  <#--
  <div style="height:0.88rem;"></div>
  
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 sel" href="#">平台首页</a>
	        <a class="a2" href="#">商品分类</a>
	        <a class="a3" href="#">购物车</a>
	        <a class="a4" href="#">会员中心</a>
      </menu>
  </section>
  -->
  <!-- 底部 END -->
  
</body>
</html>
