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
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

     navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})

function topup(){
    var provice = $("#provice").val();
    
    var reg = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
    if(undefined == provice || "" == provice || !reg.test(provice)){
        alert("输入正确的充值金额");
        return;
    }else{
        if(provice > 50000 || provice < 10){
            alert("充值金额必须在10~50000之间");
            return;
        }else{
            $("#form1").submit();
        }
    }
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
    <#include "/client/common_header.ftl">

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  <#include "/client/common_user_menu.ftl">
  <form action="/user/topup2" method="post" id="form1">
  <input type="hidden" value="${user.username}" name="username"/>
  <div class="mymember_center add_width">
    <div class="member_recharge">
      <p class="p1">填写充值金额</p>
      <p class="p2">充值账户：<span> ${user.username!''}</span></p>
      <p class="p2">充值金额：<input type="text" id="provice" name="provice" class="text" />&nbsp;&nbsp;元&emsp;&emsp;</p>
      <div class="h10"></div>
      <p class="p1">选择支付方式</p>
        <li>
            <input type="radio" value="1" name="payTypeId" datatype="n" nullmsg="请选择支付方式!">
            <img src="/client/images/ali.png" width="160" height="40">
        </li>
        <#--
        <#if pay_type_list_third??>
                <#list pay_type_list_third as pay_type>
                <li>
                    <input type="radio" value="${pay_type.id?c}" name="payTypeId" datatype="n" nullmsg="请选择支付方式!">
                    <img src="${pay_type.coverImageUri!''}" width="160" height="40">
                </li>
                </#list>
            </#if>-->
      <div class="clear"></div>
      <a href="javascript:topup();" class="recharge_btn">立即充值</a>

      <p class="p3">请注意：支持国内主流银行储蓄卡充值，在线支付成功后，充值金额会在1分钟内到账。如遇充值异常，请致电客户处理。
客服电话：${site.telephone!''}    服务时间：周一至周日 0:00-24:00</p>

      <div class="prompt">
        <h3>温馨提示</h3>
        <p>1. 充值成功后，余额可能存在延迟现象，一般1到5分钟内到账，如有问题，请咨询客服；</p>
        <p>2. 充值金额输入值必须是不小于10且不大于50000的正整数；</p>
        <p>3. 您只能用储蓄卡进行充值，如遇到任何支付问题可以查看在线支付帮助；</p>
        <p>4. 充值完成后，您可以进入账户充值记录页面进行查看余额充值状态。</p>
      </div>  

      <div class="h30"></div>
    </div>
    
  </div>
   </form>

<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











