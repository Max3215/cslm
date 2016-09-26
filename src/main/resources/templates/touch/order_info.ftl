<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>确认订单</title>
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
<script src="/touch/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
    
    $("#form1").Validform({
         btnSubmit:"#btn_sub",
         tiptype: 1
    });
    
    <#if msg??>
     alert("${msg!''}");
    </#if>

});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>确认订单</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
<form id="form1" action="/touch/order/submit" method="post">  
  <!-- 购物车确认 -->
  <#include "/touch/order_info_addr.ftl">
  
<script type="text/javascript">

function selectDeliveryTyp(tag,type){
    $(tag).addClass("act").siblings(".choose").removeClass("act");
    $("#deliveryType").attr("value",type)
    
    var postPrice = 0;
    <#if postPrice??>
        postPrice = ${postPrice};
    </#if>
    var price = parseFloat($("#price").val());
    
    if(type==0)
    {
        //$("#totalPrice").html(price+postPrice);
        $("#postPrice").val(postPrice);
    }else{
        //$("#totalPrice").html(price);
        $("#postPrice").val(0);
    }
    subPrice();
}
</script>

    <div class="dis_ways down_ways">
    <p class="tit">配送方式<span></span></p>
    <menu class="down">
       <input type="hidden" id="postPrice" value="0">
        <input type="hidden" value="0" id="deliveryType" name="deliveryType" datatypr="n" nullmsg="请选择配送方式!">
      <a href="javascript:void(0)" class="choose act" onclick="selectDeliveryTyp($(this),0)">物流
        <#if postPrice??><span style="font-size:0.15rem;line-height:0.58rem;">&nbsp;￥${postPrice?string("0.00")}</span></#if>
        <#if post??>&emsp;&emsp;<span style="font-size:0.15rem;line-height:0.58rem;">${post!''}</span></#if>
      </a>
      <a href="javascript:void(0)" class="choose" onclick="selectDeliveryTyp($(this),1)">自提</a>
      <#if addressList??>
        <select name="shipAddressId">
            <#list addressList as addr>
          <option value="${addr.id?c}">${addr.disctrict!''}${addr.detailAddress!''}</option>
            </#list>
        </select>
    </#if>
    </menu>
  </div>

<script type="text/javascript">
function selectPayType(tag,type)
{
    $(tag).addClass("act").siblings(".choose").removeClass("act");
    $(".pay_ways menu a").removeClass("act");
    $("#payTypeId").attr("value",type);
    showPwdBtn();
}

function selectType(tag,type)
{
     $(".pay_ways .choose").removeClass("act");
     $("#server").addClass("act");
     $("#payTypeId").attr("value",type);
     $(tag).addClass("act").siblings().removeClass("act"); 
     showSub();
}

function showPwdBtn(){
        $("#btn_sub").css("display","none");
        $("#btn_show").css("display","block");
   }
function showSub(){
        $("#btn_sub").css("display","block");
        $("#btn_show").css("display","none");
   }
</script>
    
    <div class="pay_ways down_ways">
        <p class="tit">选择支付方式<span></span></p>
        <div class="down">
           <input type="hidden" value="0" name="payTypeId" id="payTypeId" datatype="n" nullmsg="请选择支付方式!">
          <a href="javascript:void(0)" class="choose act" onclick="selectPayType($(this),0)" id="vir">余额支付</a>
          <span>余额：<#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if></span>
          <div class="clear"></div>
            <a href="javascript:void(0)" class="choose" onclick="selectType($(this),1)"><img src="/client/images/ali.png" style=" height: 0.65rem;" /></a>
            
            <#if app?? && app ==1>
            <a href="javascript:void(0)" class="choose" onclick="selectType($(this),2)"><img src="/client/images/wx.png" style=" height: 0.65rem;" /></a>
            </#if>
            <#--
            <#if pay_type_list??>
            <#list pay_type_list as pay_type>
              <a href="javascript:void(0)" class="choose" onclick="selectType($(this),${pay_type.id?c})"><img src="${pay_type.coverImageUri!''}" width="90" /></a>
            </#list>
            </#if>
            -->
        </div>
      </div>
      
<script type="text/javascript">
$(".down_ways .tit").click(function(){
  $(this).toggleClass("on");
  $(this).next(".down").slideToggle(100);
})

function checkNumber()
{
   var registerSharePoints = ${site.registerSharePoints?c!'1'}; // 兑换比
   var totalPoints = ${user.totalPoints?c!'0'}; // 可使用积分
   var pointUse = parseFloat($("#pointUse").val());
   var goodsPrice = ${totalPrice?string('0.00')};
   var totalUse = registerSharePoints*goodsPrice;

    if (pointUse==''|| pointUse=='0' || isNaN(pointUse)) 
    {
        $("#pointUse").val(0);
        return ;
    }

   if (pointUse > totalPoints){
        alert("积分不足")
        $("#pointUse").val(0);
        return ;
   }
   if(pointUse > totalUse){
        alert("最多可使用"+totalUse+"积分")
        $("#pointUse").val(0);
        return ;
   }
   $("#totalUse").val(pointUse/registerSharePoints);
   subPrice();
}

function subPrice(){
    var postPrice = parseFloat($("#postPrice").val());
    var totalUse = parseFloat($("#totalUse").val());
    var totalPrice = parseFloat(${totalPrice})
    
    $("#totalPrice").html((totalPrice+postPrice-totalUse).toFixed(2));
}
</script>
    
    <div class="use_integral">
        <span>积分抵扣</span>
        <input type="text" class="text" name="pointUse"  value="0"  onblur="checkNumber()" onfocus="if(value==''||value=='0') {value='0'}" onkeyup="value=value.replace(/[^0-9]/g,'')" id="pointUse"/>
        <div class="clear"></div>
        <input type="hidden" value="0" id="totalUse">
        <p>可使用积分总额：${user.totalPoints!'0'}&nbsp;&nbsp;&nbsp;&nbsp;${site.registerSharePoints!'1'}积分兑换1元</p>
     </div>
    
  <div class="pay_massege"><textarea placeholder="给商家留言" name="userMessage"></textarea></div>

  <section class="order_list" style="margin-bottom:0.4rem;border:none;">
    <ul>
      <#if selected_goods_list??>
           <#list selected_goods_list as cg>
                <li>
                    <a href="/touch/goods/${cg.distributorGoodsId?c}" class="pic"><img src="${cg.goodsCoverImageUri!''}"></a>
                    <div class="info">
                      <a href="/touch/goods/${cg.distributorGoodsId?c}">${cg.goodsTitle!''}</a>
                      <p>价格：￥${cg.price?string("0.00")}</p>
                      <p>数量：${cg.quantity!''}</p>
                    </div>
                    <div class="clear"></div>
                  </li>
           </#list>
       </#if> 
    </ul>  
  </section>
   <p class="pay_total">共<span>${totalQuantity!'0'}</span>件，合计<span>¥${totalPrice?string('0.00')}</span></p>
   <#assign price=0>
    <#if postPrice??>
        <#assign price=totalPrice+postPrice>
    <#else>
        <#assign price=totalPrice>
    </#if>
   
  <div style="height:0.58rem;"></div>
  <section class="cart_foot">
    <input type="hidden" value="<#if totalPrice??>${totalPrice}</#if>" id="price">
    <p>总金额：￥<span id="totalPrice">${price?string('0.00')}</span>元</p>
    <a href="javascript:;" id="btn_show" onclick="showPwd();" class="btn">确定</a>
    <a href="javascript:;" id="btn_sub" class="btn" style="display:none;">确定</a>
  </section>
  <!-- 购物车确认 END -->
</form>
  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1 " href="/touch/disout">平台首页</a>
	        <a class="a2" href="/touch/category/list">商品分类</a>
	        <a class="a3 sel" href="/touch/cart">购物车</a>
	        <a class="a4" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
 <script>
 function showPwd(){
    $("#pwd_div").css("display","block");
 }
 function hidePwd(){
    $("#payPwd").val('');
    $("#pwd_div").css("display","none");
 }
 
 function checkPassword(){
     var paypwd=$("#payPwd").val();
      $.ajax({
            url : "/order/check/password",
            type : 'post',
            data : {"paypwd" : paypwd},
            success : function(data){
                if(data.code == 0){
                    alert(data.msg);
                    return;
                }else{
                    $("#form1").submit();
                }
           }
     })
} 
 </script> 
  
  <aside class="pay_password" style="display:none;" id="pwd_div">
    <div class="con">
      <p class="tit">填写支付密码</p>
      <input type="password" class="text" id="payPwd"/>
      <menu class="btns">
        <a href="javascript:;" onclick="checkPassword();">确定</a>
        <a href="javascript:void(0)" onclick="hidePwd();">取消</a>
      </menu>
    </div>
  </aside>
</body>
</html>
