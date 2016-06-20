<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Language" content="zh-CN">
<title><#if dis_goods??>${dis_goods.goodsTitle!''}-</#if>商品详情</title>
<meta name="keywords" content="${goods.seoKeywords!''}">
<meta name="description" content="${goods.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/touch/images/cslm.ico" rel="shortcut icon">
<meta name="viewport" content="initial-scale=1,maximum-scale=1,minimum-scale=1">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">

<link href="/touch/css/common.css" rel="stylesheet" type="text/css" />

<script src="/touch/js/jquery-1.9.1.min.js"></script>
<script src="/touch/js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	indexBanner("pro_box","pro_sum",300,5000,"pro_num");//Banner
  menuCheckShow("check_menu","a","check_con",".text","act","")//选项卡
});

// 收藏商品
function addCollect(goodsId)
{
    if (undefined == goodsId)
    {
        return;
    }
    
    $.ajax({
        type:"post",
        url:"/touch/user/collect/add",
        data:{"disgId": goodsId},
        dataType: "json",
        success:function(res){
           //  if(res.code==0){
            //    $("#addCollect").removeClass("pro_share");
           //     $("#addCollect").addClass("pro_share1");
           //     
           // }
            alert(res.message);
            
            // 需登录
            if (res.code==1)
            {
                setTimeout(function(){
                    window.location.href = "/touch/login";
                }, 1000); 
            }
        }
    });
}
// 收藏店铺
function collectShop(disId){
    if(undefined == disId){
        return ;
    }
    
    $.ajax({
        type:"post",
        url:"/touch/user/collect/shop",
        data:{"disId": disId},
        dataType: "json",
        success:function(res){
            alert(res.message);
            // 需登录
            if (res.code==1)
            {
                setTimeout(function(){
                    window.location.href = "/touch/login";
                }, 1000); 
            }
        }
    });
    
}
<!-- 减少商品数量的方法 -->
function minusNum(){
    var q = parseInt($("#quantity").val());
        
    if (q > 1){
        $("#quantity").val(q-1);
        $("#number").val(q-1);
    }
    $("#addCart").attr("href", "/cart/init?id=${dis_goods.id?c}&quantity=" + $("#quantity").val());
}

function addNum(){
    var q = parseInt($("#quantity").val());
    <#if dis_goods?? && dis_goods.leftNumber??>
        if (q < ${dis_goods.leftNumber!'0'})
        {
            $("#quantity").val(q+1);
        }
        else
        {
            alert("已达到库存最大值");
        }
   <#else>
        $("#quantity").val(q+1);
        $("#number").val(q+1);
   </#if>
    $("#addCart").attr("href", "/touch/cart/init?id=${dis_goods.id?c}&quantity=" + $("#quantity").val());
}

<!--  加入购物车   -->
function cartInit(dId){
    var quantity = document.getElementById("quantity").value;
    if(quantity==0){
        return;
    }
 <!--   var newTab=window.open('about:blank');-->
    $.ajax({
        type: "get",
        url: "/touch/goods/incart",
        data: {"id":dId,"quantity":quantity},
        success: function (data) { 
            if(data.msg){
                alert(data.msg);
                return;
            }
            window.location.href="/touch/cart/init?id="+dId+"&quantity="+quantity;
        }
    });
}

<!--  立即购买   -->
function byNow(dId){
    var quantity = document.getElementById("quantity").value;
    var leftNumber = parseInt($("#leftNumber").val());
    
    if(quantity <= 0 || "" == quantity || quantity > leftNumber){
        alert("请选择正确的数量");
        return;
    }
  //  window.open("/order/byNow/"+dId+"?quantity="+quantity);
    window.location.href = "/touch/order/byNow/"+dId+"?quantity="+quantity;
}


function proGoods(dId){
    var quantity = document.getElementById("quantity").value;
    var leftNumber = parseInt($("#leftNumber").val());
    
    if(quantity <= 0 || "" == quantity || quantity > leftNumber){
        alert("请选择正确的数量");
        return;
    }
  //  window.open("/order/byNow/"+dId+"?quantity="+quantity);
    window.location.href = "/touch/order/proGoods/"+dId+"?quantity="+quantity;
}
</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<div class="search_box">
      <input type="text" class="text" placeholder="搜索" />  
    </div>
		<a href="/touch" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 商品滑动图片 -->
  <section id="pro_box" class="bannerbox">
    <ul id="pro_sum" class="bannersum">
    <#if goods.showPictures??>
        <#list goods.showPictures?split(",") as uri>
            <#if ""!=uri>
                <li><img src="${uri!''}"/></li>
            </#if>
        </#list>
    </#if>
    </ul>
    <div class="clear"></div>
  </section>
  <!-- 商品滑动图片 END -->

  <!-- 商品信息 -->
  <section class="pro_info">
    <div class="con">
      <h3>${dis_goods.goodsTitle!''}</h3>
      <p class="f_tit">${dis_goods.subGoodsTitle!''}</p>
      <p class="num">商品编号：${goods.code!''}&nbsp;&nbsp;&nbsp;&nbsp;商品品牌：${goods.brandTitle!''}</p>
      <p class="price">￥<#if dis_goods??>${dis_goods.goodsPrice?string('0.00')}</#if><span>￥<#if dis_goods?? && dis_goods.goodsMarketPrice??>${dis_goods.goodsMarketPrice?string('0.00')}<#else>${goods.marketPrice?string("0.00")}</#if></span></p>
       
        <div class="sc_shop">
            <p>商品来源：</p>
            <p class="name">${dis_goods.distributorTitle!''}<a href="javascript:collectShop(${dis_goods.disId?c});">收藏店铺</a></p>
       </div>
       <div class="postage">
            <p>邮费说明：</p>
            <p>${distributor.postPrice?string('0.00')}元/满${distributor.maxPostPrice?string('0.00')}包邮</p>
          </div>
    </div>
    <a href="javascript:void(0)" class="choose" onclick="$('.pro_eject').fadeIn(300);">选择   数量</a>
  </section>
  <!-- 商品信息 END -->

  <!-- 选择商品参数弹出 -->
  <aside class="pro_eject">
    <div class="part">
      <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
      <#--
      <dl>
        <dt>商品来源：</dt>
        <dd>${dis_goods.distributorTitle!''}</dd>
      </dl>
      <dl>
        <dt>邮费说明</dt>
        <dd>邮费：${distributor.postPrice?string('0.00')}元/满${distributor.maxPostPrice?string('0.00')}包邮</dd>
      </dl>
      -->
      <div class="number">
        <span>购买数量</span>
        <a id="id-minus" href="javascript:addNum();">+</a>
        <input class="text" type="text" id="quantity" value="1" />
        <a id="id-plus"  href="javascript:minusNum();">-</a>
        <input type="hidden" id="leftNumber" value="${dis_goods.leftNumber!'0'}">
      </div>
    </div>
  </aside>
  <!-- 选择商品参数弹出 END -->

  <!-- 详情和评价 -->
  <menu id="check_menu">
    <a href="javascript:void(0)">商品详情</a>
    <a href="javascript:void(0)">商品评价</a>
  </menu>
  <section id="check_con">
    <div class="text">
      ${goods.detail!''}
    </div>
    <ul class="text">
        <#if comment_page?? && comment_page.content?size gt 0>
            <#list comment_page.content as item>
              <li>
                    <p>${item.username!''}<span>${item.commentTime?string("yyyy-MM-dd HH:hh:ss")}</span></p>
                    <p>${item.content!''}</p>
                    <#if item.isReplied?? && item.isReplied>
                        <p >商家回复：${item.reply!''}</p>
                    </#if>
              </li>
            </#list>
         <#else>
         <li>暂时无人评价</li>
        </#if> 
    </ul>
  </section>
  <!-- 详情和评价 END -->

  <!-- 底部 -->
  <div style="height:0.78rem;"></div>
  <section class="pro_foot">
    <a href="javascript:addCollect(${dis_goods.id?c})"></a>
    <#if dis_goods.isDistribution?? && dis_goods.isDistribution>
       <a href="javascript:proGoods(${dis_goods.id?c});" target="_blank" title="立即购买" >立即预购</a>
   <#else>
    <a href="javascript:byNow(${dis_goods.id?c});" target="_blank" title="立即购买" >立即购买</a>
   </#if>
    <a href="/touch/cart/init?id=${dis_goods.id?c}"  id="addCart">加入购物车</a>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
