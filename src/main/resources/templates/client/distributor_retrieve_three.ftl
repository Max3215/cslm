<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
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
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
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
        <#include "/client/common_distributor_menu.ftl">
    <div class="mymember_mainbox">  
    
  	<form id="form" method="post">
	<div class="login_dl">
      <div class="fondpassowrd">      
        <!-- 找回密码步骤 -->
        <p class="fondpassbar">
          <span class="isok">1.验证身份<img src="/client/images/fondpass.png"></span>
          <span class="isok">2.设置新密码<img src="/client/images/fondpass.png"></span>
          <span class="isok">3.设置成功</span>
        </p>
        <div class="clear"></div>
        
		 <!-- 完成 -->
          <dl class="fondpassstep3">
            <p class="fondpasssuc"><img src="/client/images/fondpassok.png">新密码设置成功<a style="color:#e8323d;"  href="/distributor/account">返回账号管理</a></p>
            <p class="fondpassnotice">为了您的账户安全，请牢记新密码~</p>
          </dl>
          <div class="clear h30"></div>

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
