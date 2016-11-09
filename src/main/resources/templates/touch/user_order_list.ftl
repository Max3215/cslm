<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>联超商城</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/order.js"></script>

<script src="/touch/js/message.js"></script>
<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />
<script src="/touch/js/search.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
    var url = '/touch/user/order/list_more/<#if status_id??>${status_id}<#else>0</#if>';
    $('#order_list').refresh(url,"#order_list",0);
});

function cancelConfirm() {
    if (!confirm("未付款可直接取消，是否确认取消订单？")) {
        window.event.returnValue = false;
    }
}

function receiveConfirm() {
    if (!confirm("检查货物质量并确认收货？")) {
        window.event.returnValue = false;
    }
}

function orderReceive(id)
{
     $.ajax({
            type:"post",
            url:"/touch/user/order/receive",
            data:{
                "id":id
            },
            success:function(res) {
                if (0 == res.code)
                {
                    //alert(res.message);
                  //  ct.alert({
                  //      text: res.message,
                  //      type: "alert"
                  //  });
                    window.location.reload();
                }
                else
                {
                    //alert(res.message);
                    ct.alert({
                        text: res.message,
                        type: "alert"
                    });
                    if (res.message = "请登录！！")
                    {
                    window.location.href="/touch/login"
                    }
                }
            }
        });
}

function weixinpay(orderId){
   document.location =  "weixinpay?orderId="+orderId;
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="/touch/user" class="back"></a>
		<p>我的订单</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 我的订单 -->
  <menu class="order_tit">
    <a <#if !status_id?? || status_id==0>class="act"</#if> href="/touch/user/order/list/0"><p>全部订单</p></a>
    <a <#if status_id?? && status_id==2>class="act"</#if> href="/touch/user/order/list/2"><p>待付款</p></a>
    <a <#if status_id?? && status_id==4>class="act"</#if> href="/touch/user/order/list/4"><p>待收货</p></a>
    <a <#if status_id?? && status_id==6>class="act"</#if> href="/touch/user/order/list/6"><p>已完成</p></a>
  </menu>
  <div style="height:0.8rem;"></div>

    <div  id="order_list">
  <#if order_page??>
    <#list order_page.content as order>
  <section class="order_list" >
    <ul>
      <a href="/touch/user/order?id=${order.id?c}"> <p class="number">订单号：${order.orderNumber!''}</p></a>
      <#list order.orderGoodsList as og>
      <li>
        <a href="/touch/goods/${og.goodsId?c}" class="pic"><img src="${og.goodsCoverImageUri!''}" /></a>
        <div class="info">
          <a href="/touch/goods/${og.goodsId?c}">${og.goodsTitle!''}</a>
          <#if og.specName??><p>规格：${og.specName!''}</p></#if>
          <p>价格：￥${og.price?string('0.00')}</p>
          <p>数量：${og.quantity!'0'}</p>
        </div>
        <div class="clear"></div>
      </li>
     </#list>
    </ul>
    <div class="btns">
      <#if order.statusId??>
      <#switch order.statusId>
      <#case 1>
           <span>待确认</span>
           <menu>
            <a href="/touch/user/cancel/direct?id=${order.id?c}" onClick="cancelConfirm()" id="">取消订单</a>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 2>
           <span>待付款</span>
           <menu>
                <a href="/touch/user/cancel/direct?id=${order.id?c}" onClick="cancelConfirm()" id="">取消订单</a>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
                <#if order.payTypeId == 2 && isIOS?? && isIOS==true>
                <a href="javascript:;" onclick="weixinpay(${order.id?c})" class="cur">去付款</a>
                <#else>
                <a href="/touch/order/dopay/${order.id?c}" class="cur">去付款</a>
                </#if>
                <#--
                <a href="javascript:;" onclick="gopay(${order.id?c})" class="cur">去付款</a>
                -->
           </menu>
      <#break>
      <#case 3>
           <span>待发货</span>
           <menu>
           <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 4>
           <span>待收货</span>
           <menu>
                <a href="javascript:orderReceive(${order.id?c});" onClick="receiveConfirm()" class="cur">确认收货</a>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 5>
           <span>待评价</span>
           <menu>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
                <a href="/touch/user/comment/list?keywords=${order.orderNumber!''}" class="cur">去评价</a>
           </menu>
      <#break>
      <#case 6>
           <span>已完成</span>
           <menu>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 7>
           <span>已取消</span>
           <menu>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#default>
            <span>支付取消</span>
           <menu>
           </menu>
        </#switch>
      </#if>
    </div>
  </section>
   </#list>
   </#if>
   </div>
  <!-- 我的订单 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1" href="/touch/disout">平台首页</a>
	        <#if DISTRIBUTOR_ID??>
            <a class="a5" href="/touch">店铺首页</a>
            </#if>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
