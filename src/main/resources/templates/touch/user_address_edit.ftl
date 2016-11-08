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
<link href="/touch/css/style.css" rel="stylesheet" type="text/css" />
<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script src="/touch/js/Validform_v5.3.2_min.js"></script>
<script src="/touch/js/jquery.cityselect.js"></script>
<script src="/touch/js/jquery.diysiteselect.js"></script>
<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	
	 //初始化表单验证
    $("#form1").Validform({
        tiptype: 1,
    });
	
	$("#pcd").citySelect({
        nodata:"none",
        <#if address?? && address.province??>prov: "${address.province!''}",</#if>
        <#if address?? && address.city??>city: "${address.city!''}",</#if>
        <#if address?? && address.disctrict??>dist: "${address.disctrict!''}",</#if>
        required:false
    }); 
});


</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>编辑收货地址</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 收货人信息填写 -->
  <form id="form1" action="/touch/user/address/save">
  <input type="hidden" value="${type!''}" name="type">
  <input type="hidden" value="${pointId!''}" name="pointId">
  <section class="add_consignee">
    <input class="mytext" name="addressId" type="hidden" value="<#if address??>${address.id?c}</#if>">  
    <div>
    <p>收货人：</p>
    <input type="text"  class="text" name="receiverName" datatype="*2-128" value="<#if address??>${address.receiverName!''}</#if>"/>
    </div>
    <div>
    <p>联系电话：</p>
    <input type="text" class="text" name="receiverMobile" "m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/" errormsg="请输入正确的电话号码格式！" value="<#if address??>${address.receiverMobile!''}</#if>"/>
    </div>
    <div>
    <p>邮政编码：</p>
    <input type="text" class="text" name="postcode"  datatype="p" value="<#if address??>${address.postcode!''}</#if>"/>
    </div>
    <p>收货地址：</p>
    <div class="address" id="pcd">
          <select class="fl prov" name="province" datatype="*"></select>
          <select class="fr city" name="city" datatype="*"></select>
          <div class="clear"></div>
          <select style="width:100%;" class="dist"  name="disctrict"></select>
          <p>详细地址</p>
          <textarea name="detailAddress"><#if address??>${address.detailAddress!''}</#if></textarea>
    </div>
    <div class="default"><label><input type="checkbox" name="isDefaultAddress" <#if address?? && address.isDefaultAddress?? && address.isDefaultAddress =true>checked=true</#if> value="true"/>&nbsp;设为默认</label></div>
    <input type="submit" class="sub" value="保存" />
  </section>
  </form>
  <!-- 收货人信息填写 END -->

  <!-- 底部 -->
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
    	<menu>
	        <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
