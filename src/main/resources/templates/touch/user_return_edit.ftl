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
<script type="text/javascript" src="/touch/js/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    });

})
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>申请退货</p>
		<a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  

  <div class="thpro_info">
  <#if order_goods??>
    <a href="/touch/goods/${order_goods.goodsId?c}" class="pic">
        <img title="${order_goods.goodsTitle!''}" src="${order_goods.goodsCoverImageUri!''}" alt="${order_goods.goodsTitle!''}">
    </a>
    <div class="info">
      <a href="/touch/goods/${order_goods.goodsId?c}">${order_goods.goodsTitle!''}</a>
      <p>价格：￥${order_goods.price?string('0.00')}</p>
      <p>数量：${order_goods.quantity}</p>
    </div>
    </#if>
    <div class="clear"></div>
  </div>

    <form method="post" action="/touch/user/return/save" id="form1">
    <div class="thdescribe">
      <p><span>*</span>问题描述</p>
      <textarea name="reason" datatype="*5-255" placeholder="请输入最少5个字的退货原因"  nullmsg=" " errormsg=" " sucmsg =" "></textarea>
    </div>
   
    <div class="mer_info">
      <p>商家信息：${shop.title!''}</p>
      <p>商家地址：${shop.address!''}</p>
    </div>
      
    <div class="contact_number">
        <p><span>*</span>联系电话</p>
        <input type="text" class="text" name="telephone" placeholder="请输入手机号" datatype="n8-20" sucmsg =" " errormsg="请输入正确的电话格式"/>
    </div>
    <input type="hidden" name="goodsId" value="${order_goods.goodsId?c!''}" />
    <input type="hidden" name="id" value="${order.id?c}" />
    <input type="hidden" name="shopId" value="${shop.id?c}" />
    <input type="hidden" name="shopTitle" value="${shop.title!''}" />

    <div class="sqth_btn"><input type="submit" class="sub" value="提交" /></div>	
    </form>
</body>
</html>
