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
function checkUser(){
	var username = $("#username").val();
	if(undefined == username || "" == username || isNull(username)){
		layer.msg('请输入转入会员账号', {icon: 2 ,time: 1000});
		$("#btn_check").attr("checked",false);
		return;
	}
	
	$.ajax({
		type : "post",
		url : "/distributor/transferCheck",
		data : {"data":username,"type":"username"},
		success: function (data) { 
			if(data.code==0){
				layer.msg(data.msg, {icon: 5 ,time: 2000});
				$("#btn_check").attr("checked",false);
			}else{
				layer.confirm('转入账号信息:<br>账号：'+data.user.username+",手机号："
					+data.user.mobile, {
					  btn: ['确定','取消'] //按钮
					}, function(){
					  	layer.closeAll();
					}, function(){
						$("#btn_check").attr("checked",false);
						$("#username").val('');
					});
			}
		}
	})
}

function goTransferNext(){
	var username = $("#username").val();
	var price = $("#price").val();
	if(undefined == username || "" == username || isNull(username)){
		layer.msg('请输入转入会员账号', {icon: 2 ,time: 1000});
		$("#btn_check").attr("checked",false);
		return;
	}
	
	 var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if(undefined == price || ""==price || !reg.test(price))
    {
        layer.msg('请正确输入转账金额', {icon: 2 ,time: 1000});
        return ;
    }
	
	$.ajax({
		type : "post",
		url : "/distributor/transferCheck",
		data : {"data":price,"type":"price"},
		success: function (data) { 
			if(data.code==0){
				layer.msg(data.msg, {icon: 5 ,time: 2000});
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
            <span>安全验证<img src="/client/images/fondpass.png"></span>
            <span>转账完成</span>
          </p>
          <div class="transfer_con">
            <p class="top">填写转账信息</p>
      		<form action="/distributor/transfer2" method="post" id="form">
            <dl class="part">
              <dt>对方账户：</dt>
              <dd>
                <input type="text" class="text" name="username"  id="username" />
                <label><input type="checkbox" id="btn_check" onclick="checkUser()"/>&nbsp;校验收款人账户</label>
              </dd>
            </dl>
            <dl class="part">
              <dt>转账金额：</dt>
              <dd><input type="text" class="text" name="price"  id="price"/>&nbsp;&nbsp;元</dd>
            </dl>
            <div class="h10"></div>
            <dl class="part">
              <dt></dt>
              <dd><a href="javascript:;" onclick="goTransferNext();" class="btn">下一步</a></dd>
            </dl>
            </form>
            <div class="prompt">
              <p class="top">温馨提示：</p>
              <p>1. 转账功能适用于超市商家给普通会员线上转款；</p>
              <p>2. 转账金额以实际到账金额为准； </p>
              <p>3. 请仔细核对转入账户是否正确，因商家账户输入错误造成的转账错误平台不负任何责认；</p>
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




  











