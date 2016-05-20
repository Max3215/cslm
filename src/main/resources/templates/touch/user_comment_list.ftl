<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>
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

<script src="/client/js/Validform_v5.3.2_min.js"></script>

<script src="/touch/js/message.js"></script>
<link href="/touch/css/message.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
	 //初始化表单验证
    $(".commentForm").Validform({
        tiptype: 4,
        ajaxPost:true,
        callback: function(data) {
            if (data.code==0)
            {
                //alert("提交评论成功");
                ct.alert({
                    text: "提交评论成功",
                    type: "alert"
                });
                
                window.location.reload();
            }
            else
            {
                //alert(data.message);
                ct.alert({
                    text: data.message,
                    type: "alert"
                });
            }
        }
    });
});
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>评价</p>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 评价 -->
  <#if order_page??>
        <#list order_page.content as order>
              <section class="evaluate">
                <ul class="order_list">
                    <p class="number">订单编号：${order.orderNumber!''}</p>
                    <#if order.orderGoodsList??>
                          <#list order.orderGoodsList as og>
                              <#if og.isCommented?? && og.isCommented>
                              <#else>
                              <li>
                                    <form class="commentForm" action="/touch/user/comment/add" method="post">
                                    <input type="hidden" name="orderId" value=${order.id?c} />
                                    <input type="hidden" name="ogId" value=${og.id?c} />
                                    <input type="hidden" name="goodsId" value=${og.goodsId?c} />
                                    <input type="hidden" name="quantity" value=${og.quantity} />
                                    <a href="/touch/goods/${og.goodsId?c}" class="pic"><img src="${og.goodsCoverImageUri!''}" /></a>
                                    <div class="info">
                                      <a href="/touch/goods/${og.goodsId?c}">${og.goodsTitle!''}</a>
                                      <p>价格：￥${og.price?string('0.00')}</p>
                                      <p>数量：${og.quantity!'0'}</p>
                                    </div>
                                <div class="clear"></div>
                                <textarea name="content" datatype="*"></textarea>
                                <input type="submit" class="sub" value="评价" />
                                </form>
                              </li>
                              </#if>
                          </#list>
                      </#if>
                </ul>
              </section>
        </#list>
  </#if>
  <!-- 评价 END -->
  
  <div style="height:0.88rem;"></div>
  <section class="comfooter tabfix">
        <menu>
            <a class="a1" href="/touch/disout">平台首页</a>
            <a class="a2" href="/touch/category/list">商品分类</a>
            <a class="a3" href="/touch/cart">购物车</a>
            <a class="a4 sel" href="/touch/user">会员中心</a>
      </menu>
  </section>
</body>
</html>
