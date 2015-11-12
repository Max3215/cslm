<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>批发商中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  
  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})
</script>
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  <#include "/client/common_provider_menu.ftl">
  
  <div class="mymember_center add_width">
    <div class="recharge_success">
      <p class="p1">您已成功充值<#if record.provice??>${record.provice?string('0.00')}</#if>元！</p>
      <a href="/distributor/account">返回查看余额</a>
    </div>
    
  </div>


<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











