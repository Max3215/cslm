<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>分销中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果


  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})

function subRecord(){
    $("#form").submit();
}

</script>
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/client/js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->

</head>

<body>
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
        <#include "/client/common_supply_menu.ftl">
    <div class="mymember_mainbox">  
    
  	<form action="/supply/retrieve_step2" id="form" method="post">
    <div class="login_dl">
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
              <input type="text" name="mobile" id="mobile" class="fstext r3 bd" value="${mobile!''}" placeholder="请输入商家手机号">
            </dd>
            <dd class="fpsitem">
              <span class="spsititle">验证码：</span>
              <input type="text" name="smsCode" class="fstext r3 bd" value="" placeholder="请输入短信验证码">
              <input id="smsCodeBtn" class="r3 ib spsigetcode bggreen cf" style="border:none;" readonly="readonly"  value="获取验证码" />
            </dd>
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
            url : "/supply/smscode",  
            async : true,  
            type : 'post',  
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
        $("#smsCodeBtn").val('获取验证码');  
        var t2 = clearInterval(t1);  
    } else {  
        $("#smsCodeBtn").val(seed + "秒后重新获取");  
    }  
} 


	
	function goNext(){
		$("#form").submit();
	}
</script>
      </div>
    </div>
  </form>
	
  </div>
	<!--mymember_center END-->
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->

<div class="clear"></div>
<#include "/client/common_footer.ftl">
</body>
</html>
