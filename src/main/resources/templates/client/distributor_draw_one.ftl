<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>

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
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  <#include "/client/common_distributor_menu.ftl">
  
  <div class="mymember_center add_width">
    <div class="with_money">
      <p class="tit">提现余额到银行卡</p>
      <table>
        <tr>
          <th>输入银行卡号：</th>
          <td><input type="text" class="text long" placeholder="请输入您的银行卡号" /></td>
        </tr>
        <tr>
          <th>提现金额：</th>
          <td><input type="text" class="text short" />&nbsp;&nbsp;元</td>
        </tr>
        <tr>
          <th>输入支付密码：</th>
          <td><input type="text" class="text long" /></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" value="确定" /></td>
        </tr>
      </table>
    </div>
    
  </div>


<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











