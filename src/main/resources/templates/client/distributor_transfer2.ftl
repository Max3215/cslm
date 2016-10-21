<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>

<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})

function isNull( str ){
	if ( str == "" ) return true;
	var regu = "^[ ]+$";
	var re = new RegExp(regu);
	return re.test(str);
}
function checkPwd(){
	var payPwd = $("#payPwd").val();
	if(undefined == payPwd || "" == payPwd || isNull(payPwd)){
		layer.msg('请输入支付密码', {icon: 2 ,time: 1000});
		return;
	}
	
	$.ajax({
		type : "post",
		url : "/distributor/transferCheck",
		data : {"data":payPwd,"type":"payPwd"},
		success: function (data) { 
			if(data.code==0){
				layer.msg(data.msg, {icon: 5 ,time: 2000});
				$("#btn_check").attr("checked",false);
			}else{
				$("#form").submit();
			}
		}
	})
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
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  <#include "/client/common_distributor_menu.ftl">
  
  <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="transfer_money">
          <p class="tit">
            <span class="isok">填写转账信息<img src="/client/images/fondpass.png"></span>
            <span class="isok">安全验证<img src="/client/images/fondpass.png"></span>
            <span>转账完成</span>
          </p>
          <div class="transfer_con">
      		<form action="/distributor/transfer3" method="post" id="form">
      			<input type="hidden" name="username" value="${name!''}"/>
      			<input type="hidden" name="price" value="<#if price??>${price?string('0.00')}</#if>"/>
	            <p class="top">请输入支付密码 </p>
	            <div class="box">
		            <input type="password" class="text" name="payPwd" id="payPwd"/>
		            <a href="/distributor/retrieve_step1">忘记密码？</a>
	            </div>
	            <a href="javascript:;" onclick="checkPwd();" class="confirm">确定</a>
            </form>
            <div class="prompt">
              <p class="top">温馨提示：</p>
              <p>1. 转账功能适用于超市商家给普通会员线上转款；</p>
              <p>2. 转账金额以实际到账金额为准； </p>
              <p>3. 请仔细核对转入账户是否正确，因商家账户输入错误造成的转账错误平台不负任何责任；</p>
              <p>4. 转账完成后可到交易记录查看转账记录。</p>
            </div>
          </div>
        </div>
      </div>
      <!--mymember_info END-->
    </div>

<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











