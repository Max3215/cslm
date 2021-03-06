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
<script src="/touch/js/search.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	var url = "/touch/user/search/more/virtualPage";
    $('#virtualPage_lsit').refresh(url,"#virtualPage_lsit",0);	
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>账户管理</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 账户管理 -->
  <section class="user_ruling">
    <a href="/touch/user/account/show" class="user">${user.username!''}<span>账号安全设置&nbsp;&nbsp;&nbsp;&gt;</span></a>
    <div class="user_balance">
      <p>账户余额<span>¥<#if user?? && user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if></span></p>
      <div class="btn">
        <a href="/touch/user/topup1">充值</a>
        <a href="/touch/user/draw1">提现</a>
      </div>
    </div>
    
    <section class="my_integral">
    <div class="detail">
    <table id="virtualPage_lsit">
        <tr>
          <th>日期</th>
          <th>金额</th>
          <th>说明</th>
        </tr>
        <#if virtualPage?? && virtualPage.content??>
            <#list virtualPage.content as re>
                <tr>
                  <td>${re.createTime?string('yyyy-MM-dd')}</td>
                  <td><span><#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if></span></td>
                  <td>${re.cont!''}</td>
                </tr>
            </#list>
        </#if>
      </table>
      </div>
     </section>
  </section>
  <!-- 账户管理 END -->
  
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
