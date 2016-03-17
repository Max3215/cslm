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
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<link href="/client/images/cslm.ico" rel="shortcut icon">
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
    
    $("#isCheck").change(function(){
        var check = document.getElementById("isCheck");
        if(check.checked){
            $("#btn_reg").removeAttr("disabled");
            $("#btn_reg").css("background","#ff5b7d");
        }else{
            $("#btn_reg").attr("disabled","true");
            $("#btn_reg").css("background","#999999");
        }
     });
    
    
    
});

<!--  验证码   -->
function changeYzm(){
    var image = document.getElementById("yzm_image");
    image.src = "/code?date="+Math.random()
}

                       // 弹出窗口
function checkwindowshow()
{
    
    $("#tanchuang").css("display", "block");
    $("#tanchuangbackgroud").addClass("thickdiv"); 
    
    //$('html,body').animate({scrollTop:0},500);
}

// 窗口隐藏
function checkwindowhide()
{

    $("#tanchuang").css("display", "none");
    $("#tanchuangbackgroud").removeClass("thickdiv"); 
    
}
</script>
</head>

<body>
<iframe class="thickframe" id="" marginwidth="0" marginheight="0" frameborder="0" scrolling="no" style="display:none"></iframe>
<div  id="tanchuangbackgroud"></div>
<div class="thickbox" id="tanchuang" style="width: 924px; height: 500px; left: 15%; top: 88px; display:none">
    <div class="thicktitle" id="" style="width:922"><span>超市联盟注册协议</span></div>
    <div class="thickcon" id="" style="width: 922px; height: 450px; padding-left: 0px; padding-right: 0px; border-left-width: 1px; border-right-width: 1px;">
        <div class=" regist-2013">
            <div class="regist-bor">
                <div class="mc">
                    <div id="protocol-con">
                        <#if site??>${site.registerNego!''}</#if>
                    </div>
                    <div class="btnt">
                        <input class="btn-img" type="submit" value="同意并继续" onclick="checkwindowhide();"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <a href="javascript:checkwindowhide();" id="closeBox" class="thickclose">×</a></div>


    <div class="w1065">
    <div class="denglutop">
<style>
.thickbox {
    position: absolute;
    z-index: 10000002;
    overflow: hidden;
    padding: 0;
    border: 4px solid rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    -moz-border-radius: 5px;
    -webkit-border-radius: 5px;
}
.thicktitle {
    height: 27px;
    padding: 0 10px;
    border: solid #C4C4C4;
    border-width: 1px 1px 0;
    background: #F3F3F3;
    line-height: 27px;
    font-family: arial, "\5b8b\4f53";
    font-size: 14px;
    font-weight: bold;
    color: #333;
}
.thickcon {
    overflow: auto;
    background: #fff;
    border: solid #C4C4C4;
    border-width: 1px;
    padding: 10px;
}
.regist-2013 .btnt .btn-img {
    width: 322px;
    height: 34px;
    line-heiht: 34px;
    background: #e4393c;
    color: #FFF;
    -moz-border-radius: 3px;
    -webkit-border-radius: 3px;
    border-radius: 3px;
    font-family: "微软雅黑";
    font-size: 16px;
    font-weight: 800;
}
.regist-2013 .btnt {
    width: 322px;
    margin: 20px auto 0;
}
.thickclose:link, .thickclose:visited {
    display: block;
    position: absolute;
    z-index: 100000;
    top: 7px;
    right: 12px;
    overflow: hidden;
    width: 15px;
    height: 15px;
    background: url(/client/images/bg_thickbox.gif) no-repeat 0 -18px;
    font-size: 0;
    line-height: 100px;
}
#protocol-con {
    height: 356px;
    overflow: auto;
    padding: 10px 20px 0 10px;
}
.btn-img {
    cursor: pointer;
    overflow: hidden;
    margin: 0;
    padding: 0;
    border: 0;
    text-align: center;
}
.thickframe {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 10000000;
    width: 100%;
    height: 100%;
    background: #000;
    border: 0;
    filter: alpha(opacity = 0);
    opacity: 0;
}
.thickdiv {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 10000001;
    width: 100%;
    height: 100%;
    background: #000;
    border: 0;
    filter: alpha(opacity = 15);
    opacity: .15;
}
</style>
    
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
                    <input id="mobileNumber" class="text" name="mobile" type="text" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/"/>
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
                    <input class="text fl" type="text" name="code" style="width:35%;" datatype="*"/>
                    <a class="yzm01" href="javascript:changeYzm()"><img id="yzm_image" src="" width="100px;" height="37px;"/></a>
                    <a class="yzm02" href="javascript:changeYzm()">看不清楚？换一张</a>
                    <span class="Validform_checktip Validform_wrong">
                            <#if errCode??>
                                <#if errCode==1>
                                    验证码错误
                                </#if>
                            </#if>
                    </span>
                </div>
                <div class="clear h15"></div>
                <p class="pb10">
                <input type="checkbox" checked="checked" id="isCheck" />
                <span>我已阅读并同意<a href="javascript:checkwindowshow();">《超市联盟用户协议》</a></span>
                <span class="absolute-r">已有账号？<a href="/login">点击登录</a></span>
                </p>
                <input type="submit" class="sub" id="btn_reg" value="注册" />
                <div class="clear h15"></div>
             </section>
        </div>
    </form><!--logingbg END-->
    
  <!--  底部  -->
  <#include "/client/common_footer.ftl" />
</body>
</html>
