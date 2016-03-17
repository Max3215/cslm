<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<link href="/client/images/cslm.ico" rel="shortcut icon">
<script type="text/javascript" src="/client/js/jquery.cookie.js"></script>

<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/cartoon.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    
var seed=60;    //60秒  
var t1=null; 

$(function(){
       //记住密码
    if ($.cookie("rmbUser") == "true") { 
        $("#rmbUser").attr("checked", true); 
        $("#txt_loginId").val($.cookie("userName")); 
        $("#txt_loginPwd").val($.cookie("passWord")); 
    } 
    
    chooseMoreShow();

    $("#btn_login").click(function(){
        saveUserInfo();
            login();
     });
    
    <!-- 点击获取短信验证  start -->
    $("#smsCodeBtn").bind("click", function() {  
        var username = $("#txt_loginId").val();
        <!--   查询用户是否存在  -->
        $.ajax({
            url : "/login/user",
            async : true,
            type : 'GET',
            data : {"username" : username},
            success : function(data){
            
                if(data.msg){
                    alert(data.msg);
                    return;
                }
               <!-- 验证手机号 --> 
                var mob = data.mobile;
        
                var re = /^1\d{10}$/;
            
                if (!re.test(mob)) {
                    alert("请输入正确的手机号");
                    return;
                }
            
                $("#smsCodeBtn").attr("disabled","disabled"); 
                
                <!--   获取短信验证码-->
                $.ajax({  
                    url : "/reg/smscode",  
                    async : true,  
                    type : 'GET',  
                    data : {"mobile": mob},  
                    success : function(data) {  
                    
                        if(data.statusCode == '000000')
                        {  
                            t1 = setInterval(tip, 1000);  
                        }
                        else
                        {
                            $("#smsCodeBtn").removeAttr("disabled");
                        }
                    },  
                    error : function(XMLHttpRequest, textStatus,errorThrown) {  
                        alert("error");
                    }  
                });
            }
        })
     }); <!-- END  -->
         
});

document.onkeydown = function(event){
   if((event.keyCode || event.which) == 13){
        saveUserInfo();
        login();
   }
}
   
function login(){
     var username = $("#txt_loginId").val();
     var password = $("#txt_loginPwd").val();
     var type ="";
     var obj = document.getElementsByName("type");
     for(var i=0; i<obj.length; i ++){
        if(obj[i].checked){
            type=obj[i].value;
        }
    }
    
     if (username.length < 6 || password.length < 6)
     {
          alert("用户名或密码长度输入不足");
          return;
      }
     $.ajax({
          type: "post",
          url: "login",
          data: { "username": username, "password": password ,"type":type},
          dataType: "json",
          success: function (data) { 
          <!-- 修改 -->
              if (data.code == 1){
                  window.location.href="/distributor/index";
              }
              else if (data.code == 2){
                    window.location.href="/provider/index";
              }else if(data.code == 3){
                    window.location.href="/supply/index";
              }else if (data.code == 0) {
                   var url = document.referrer;          
                   if(undefined==url || ""==url){
                    window.location.href="/";
                    }else{
                        window.location.href = url; 
                    }
               } else {
                    alert(data.msg);
               }
          }
      });
}
<!--   登录END       -->

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
 //保存用户信息 
function saveUserInfo() { 
    if (document.getElementById("rmbUser").checked==true) { 
        var userName = $("#txt_loginId").val(); 
        var passWord = $("#txt_loginPwd").val(); 
        $.cookie("rmbUser", "true", { expires: 7 }); // 存储一个带7天期限的 cookie 
        $.cookie("userName", userName, { expires: 7 }); // 存储一个带7天期限的 cookie 
        $.cookie("passWord", passWord, { expires: 7 }); // 存储一个带7天期限的 cookie 
    } 
    else { 
        $.cookie("rmbUser", "false", { expires: -1 }); 
        $.cookie("userName", '', { expires: -1 }); 
        $.cookie("passWord", '', { expires: -1 }); 
    } 
}  
//选择类型计算 - 本效果由昆明天度网络IRIS原创制作
function chooseMoreShow(){
    var _arr = $(".choose_box menu");
    var _length = _arr.length;
    if(_length > 4){
        for(var i=4;i<_length;i++){
            _arr.eq(i).css("display","none");
            }
    }//if END
}
</script>

</head>

<body>
<header class="logintop">
  <div class="main pt20 pb20">
    <a href="/"><img src="<#if site??>${site.logoUri!''}</#if>" /></a>
  </div>
</header>
<div class="logingbg">

  <section class="loginbox">
  
    <p>请输入用户名</p>
    <input class="text" type="text" id="txt_loginId"/>
    <p>请输入密码</p>
    <input class="text" type="password" id="txt_loginPwd"/>   
    <#-- 
    <p>请输入验证码</p>
    <input class="text fl" type="text" style="width:40%;" id="smsCode"/> 
   <a href="javascript:;" id="smsCodeBtn" class="get_code">获取手机验证码</a> 
    <input id="smsCodeBtn" onclick="javascript:;" readOnly="true" class="sub" style="text-align:center;width: 50%; border-radius: 3px;  float:left; margin-left:10px; background: #ff5b7d; color: #fff; height: 35px;" value="点击获取短信验证码" />
    -->
    <div class="clear h15"></div>
    <p class="identity pb10">
      <span><input type="radio" name="type" value="user" checked="checked">普通会员</span>
      <span><input type="radio" name="type" value="distributor">超市</span>
      <span><input type="radio" name="type" value="provider">批发商</span>
      <span><input type="radio" name="type" value="supply">分销商</span>
    </p>
    <p class="pb10">
      <input id="rmbUser" type="checkbox" />
      <span>记住密码</span>
      <span class="absolute-r"><a href="/login/password_retrieve">忘记密码</a> | <a href="/reg">免费注册</a></span>
    </p>
    <div class="clear h40"></div>
    <input type="submit" class="sub" id="btn_login" value="登录" />
    <div class="clear h20"></div>
  </section>
</div><!--logingbg END-->

    <!--  底部    -->
    <#include "/client/common_footer.ftl" />
</body>
</html>
