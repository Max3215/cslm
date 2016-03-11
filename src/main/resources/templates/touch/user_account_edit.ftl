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
<link href="/touch/css/style.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/message.js"></script>
<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />
<script src="/touch/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	
	 //初始化表单验证
    $("#form1").Validform({
        tiptype: 1,
        ajaxPost:true,
        callback: function(data) {
            ct.alert({
                    text: data.msg,
                    type: "alert"
                });
            if(data.code==1){
               window.location.href ="/touch/user/account/edit/${type!''}"
            }
        }
    });
	
//	$(".public_modify .edit a").click(function(){
//        $(".public_modify .edit .text").val("");
//    })
});

function clear(){
    $(".public_modify .edit .text").val("");
}
function clear1(){
    $(".public_modify .edit1 .text").val("");
}
function clear2(){
    $(".public_modify .edit2 .text").val("");
}

</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<#if type="email">
		      <p>邮箱</p>
		<#elseif type="mobile">
		      <p>手机</p>
		<#elseif type="password">
		      <p>修改密码</p>
        <#elseif type="payPassword">
              <p>支付密码</p>
        <#else>
              <p>真实姓名</p>
        </#if>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <form action="/touch/user/account/save/${type!''}" method="post" id="form1">
  <section class="public_modify">
  	    <#if type="email">
              <div class="edit">
                   <span>邮箱：</span>
                   <input type="text" value="${user.email!''}" name="email" class="text" placeholder="请输入邮箱" datatype="e" nullmsg="请输入邮箱"/>
                   <a href="javascript:clear();" ></a>
              </div>
        <#elseif type="mobile">
              <div class="edit">
                   <span>手机号：</span>
                   <input type="text" class="text" name="mobile" value="${user.mobile!''}" placeholder="请输入手机号" nullmsg="请输入手机号" errmsg="手机号输入错误" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/"/>
                   <a href="javascript:clear();" ></a>
               </div>
        <#elseif type="password">
              <div class="edit">
                   <span>原密码：</span>
                   <input type="password" class="text" name="password" placeholder="请输入原密码" nullmsg="请输入原密码"  datatype="*2-20"/>
                   <a href="javascript:clear();" ></a>
               </div>
               <div class="edit1">
                   <span>新密码：</span>
                   <input type="password" class="text" name="newPwd" placeholder="请输入密码" nullmsg="请输入新密码" datatype="*2-20"/>
                   <a href="javascript:clear1();" ></a>
               </div>
               <div class="edit2">
                   <span>确认密码：</span>
                   <input type="password" class="text" placeholder="请再次输入密码" datatype="*2-20" recheck="newPwd" nullmsg="请再次输入密码" />
                   <a href="javascript:clear2();" ></a>
               </div>
        <#elseif type="payPassword">
                <#if user.payPassword??>
              	<div class="edit">
                    <span>原密码：</span>
                    <input type="password" class="text" name="payPassword" value="" placeholder="请输入原支付密码" nullmsg="请输入原支付密码" datatype="*2-6"/>
                    <a href="javascript:clear();" ></a>
              	</div>
              	</#if>
              	<div class="edit1">
                    <span>新密码：</span>
                    <input type="password" class="text" name="newPayPwd" placeholder="请输入支付密码" nullmsg="请输入新支付密码" datatype="*2-6"/>
                    <a href="javascript:clear1();" ></a>
                </div>
                <h4></h4>
                <div class="edit2">
                    <span>确认密码：</span>
                    <input type="password" class="text" placeholder="请再次输入支付密码" datatype="*2-6" recheck="newpassword"/>
                    <a href="javascript:clear2();" ></a>
                </div>
                <h4></h4>
        <#else>
                <div class="edit">
              		<span>昵称：</span>
              		<input type="text" class="text" name="realName" placeholder="请输入名称" nullmsg="请输入名称" datatype="*2-6"/>
              		<a href="javascript:clear();" ></a>
                <div class="edit">
        </#if>
  	     <input type="submit" class="sub" value="保存">
  </section>
  </form>
  
</body>
</html>
