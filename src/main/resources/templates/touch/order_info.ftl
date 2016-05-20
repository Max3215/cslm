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


 // $(".address_list a").click(function(){
 //   $(this).addClass("act").siblings().removeClass("act");
 // })

//  $(".dis_ways .choose").click(function(){
//    $(this).addClass("act").siblings(".choose").removeClass("act");
//  })

//  $(".pay_ways .choose").click(function(){
//    $(this).addClass("act").siblings(".choose").removeClass("act");
//  })

 // $(".pay_ways menu a").click(function(){
 //   $(this).addClass("act").siblings().removeClass("act");
//  })

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
<!--
function selectAddr(tag,type){
    $(tag).addClass("act").siblings().removeClass("act");
    $("#addressId").attr("value",type)
}
-->

function selectDeliveryTyp(tag,type){
    $(tag).addClass("act").siblings(".choose").removeClass("act");
    $("#deliveryType").attr("value",type)
    
    var postPrice = parseFloat($("#postPrice").val());
    var price = parseFloat($("#price").val());
    
    if(type==0)
    {
        $("#totalPrice").html(price+postPrice);
    }else{
        $("#totalPrice").html(price);
    }
}
</script>
  <div class="dis_ways">
    <p class="tit">配送方式</p>
    <input type="hidden" id="postPrice" value="<#if postPrice??>${postPrice}<#else>0</#if>">
    <input type="hidden" value="0" id="deliveryType" name="deliveryType" datatypr="n" nullmsg="请选择配送方式!">
    <a href="javascript:;" class="choose act" onclick="selectDeliveryTyp($(this),0)">物流</a>
    <#if post??>&emsp;&emsp;<span style="font-size:0.15rem;line-height:0.58rem;">${post!''}</span></#if>
    <div class="clear"></div>
    <a href="javascript:;" class="choose" onclick="selectDeliveryTyp($(this),1)">自提</a>
    <#if addressList??>
        <select name="shipAddressId">
            <#list addressList as addr>
          <option value="${addr.id?c}">${addr.disctrict!''}${addr.detailAddress!''}</option>
            </#list>
        </select>
    </#if>
  </div>
<#--
  <div class="use_integral">
    <span>积分抵扣</span>
    <input type="text" class="text" />
    <input type="submit" class="sub" value="抵扣" />
    <div class="clear"></div>
    <p>积分总额：45000&nbsp;&nbsp;&nbsp;&nbsp;100积分兑换1元</p>
  </div>
-->
<script type="text/javascript">
function selectPayType(tag,type)
{
    $(tag).addClass("act").siblings(".choose").removeClass("act");
    $(".pay_ways menu a").removeClass("act");
    $("#payTypeId").attr("value",type);
}

function selectType(tag,type)
{
     $(".pay_ways .choose").removeClass("act");
     $("#server").addClass("act");
     $("#payTypeId").attr("value",type);
     $(tag).addClass("act").siblings().removeClass("act"); 
}
</script>
  <div class="pay_ways">
    <p class="tit">选择支付方式</p>
    <input type="hidden" value="0" name="payTypeId" id="payTypeId" datatype="n" nullmsg="请选择支付方式!">
    <a href="javascript:void(0)" class="choose act" onclick="selectPayType($(this),0)" id="vir">余额支付</a>
    <span>&emsp;&emsp;&emsp;余额：<#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;</span>
    <#--
    <a href="javascript:void(0)" class="choose" id="server">快捷支付</a>
    -->
    <div class="clear"></div>
    <menu>
        <#if pay_type_list??>
            <#list pay_type_list as pay_type>
              <a href="javascript:void(0)" onclick="selectType($(this),${pay_type.id?c})"><img src="${pay_type.coverImageUri!''}" width="90" /></a>
            </#list>
        </#if>
    </menu>
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
    <a href="javascript:;" id="btn_sub" class="btn">确定</a>
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
  
</body>
</html>
