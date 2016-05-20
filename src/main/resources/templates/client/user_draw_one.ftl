<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<!--<link href="css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
        }
    });

  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果
；
     navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");

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
    <#include "/client/common_header.ftl">
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  <#include "/client/common_user_menu.ftl">
  
  <form id="form1" action="/user/drwa2" method="post">
  <div class="mymember_center add_width">
    <div class="with_money">
      <p class="tit">提现余额到银行卡</p>
      <table>
        <tr>
          <th>输入银行卡号：</th>
          <td><input type="text" name="card" class="text long" placeholder="请输入您的银行卡号" datatype="/^(\d{16}|\d{19})$/" errormsg="请输入正确的银行卡号" sucmsg=" " nullmsg="请输入账号"/></td>
        </tr>
        <tr>
          <th>提现金额：</th>
          <td><input type="text" name="price" class="text short" datatype="/(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/" sucmsg=" " nullmsg="请填写提现金额"/></td>
        </tr>
        <tr>
          <th>输入支付密码：</th>
          <td><input type="password" name="payPassword" class="text long" datatype="s6-20" errormsg="请输入密码！" sucmsg=" " nullmsg = "请输入密码"/></td>
        </tr>
        <tr>
          <th></th>
          <td><span style="color:red;">*每次提现最小金额100元</span></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" value="确定" /></td>
        </tr>
      </table>
    </div>
  </div>
  </form>

<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











