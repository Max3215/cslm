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

<script src="/layer/layer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//indexBanner("box","sum",300,5000,"num");//Banner
	var url = "/touch/user/search/more/point";
    $('#point_page').refresh(url,"#point_page",0);
});

function convert(){
	var totalPoint = ${user.totalPoints!'0'};
	var point = $("#point").val();
	
	if(!/^\+?[1-9][0-9]*$/.test(point)){
		layer.msg('请输入正确的兑换积分数');
	}
	if(totalPoint < point){
		layer.msg('可兑换积分不足'+point);
	}
	
	$.ajax({
            url : "/user/convert",
            async : true,
            type : 'post',
            data : {"point" : point},
            success : function(data){
            	layer.open({
					  title: '提示',
					  content: data.msg,
					  time: 3000
					});
				if(data.code==1){
					window.location.reload();
				} 
            }
      })
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>我的积分</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 积分 -->
   <section class="my_integral">
    <p class="total">总积分<span><#if user?? && user.totalPoints??>${user.totalPoints!'0'}<#else>0</#if></span></p>
    <div class="change">
      <p>积分兑换现金</p>
      <p>*兑换规则：<#if site??>${site.registerSharePoints!'10'}</#if>积分=1元</p>
      <input type="text" id="point"  class="text" placeholder="输入兑换的积分值" />
      <input type="submit" onclick="convert()"  class="sub" value="我要兑换" />
    </div>
    
    <div class="detail">
      <p>积分明细</p>
      <table id="point_page">
        <tr>
          <th>日期</th>
          <th>积分变化</th>
          <th>说明</th>
        </tr>
        <#if point_page?? && point_page.content??>
            <#list point_page.content as re>
                <tr>
                  <td>${re.pointTime?string('yyyy-MM-dd')}</td>
                  <td><span>${re.point!'0'}</span></td>
                  <td>${re.detail}</td>
                </tr>
            </#list>
        </#if>
      </table>
    </div>
  </section>
  <!-- 积分 END -->

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
