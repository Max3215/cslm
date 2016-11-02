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
<script src="/layer/layer.js"></script>
<script src="/touch/js/goods.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	indexBanner("pro_box","pro_sum",300,5000,"pro_num");//Banner
  menuCheckShow("check_menu","a","check_con",".text","act","")//选项卡
});

</script>
</head>

<body>
	<!-- 顶部 -->
	<header class="com_top">
		<a href="javascript:history.go(-1);" class="back"></a>
		<p>商品详情</p>
        <a href="/touch/" class="c_home"></a>
	</header>
	<div style="height:0.88rem;"></div>
	<!-- 顶部 END -->
  
  <!-- 商品滑动图片 -->
  <section id="pro_box" class="bannerbox">
    <ul id="pro_sum" class="bannersum">
    <#if goods.showPictures??>
        <#list goods.showPictures?split(",") as uri>
            <#if ""!=uri>
                <li><#if uri_index ==0><#if dis_goods.tagId??><i><img src="${dis_goods.tagImg!''}" /></i></#if></#if><img src="${uri!''}"/></li>
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
      <p class="price">￥<#if dis_goods??>${dis_goods.goodsPrice?string('0.00')}</#if><#if dis_goods.unit??><label style="font-size: 0.3rem;color: #999">/${dis_goods.unit!''}</label></#if><span>￥<#if dis_goods?? && dis_goods.goodsMarketPrice??>${dis_goods.goodsMarketPrice?string('0.00')}<#else>${goods.marketPrice?string("0.00")}</#if></span></p>
       
       <#if !spec_list?? || spec_list?size == 0> 
       <div class="buy_number" >
          <p class="fl">购买数量（库存${dis_goods.leftNumber?c!'0'}）</p>
            <a id="id-minus" onclick="addNum();">+</a>
            <input class="text" type="text" id="quantity" value="1" onfocus="if(value=='1'||value=='0') {value='1'}" onblur="checkNumber(this.value)" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
            <a id="id-plus" onclick="minusNum();">-</a>
            <input type="hidden" id="leftNumber" value="${dis_goods.leftNumber?c!'0'}">
        </div>
        <input type="hidden" value="false" id="isSpec">
       </#if>
       
       
       <#if spec_list?? && spec_list?size gt 0>
       <input type="hidden" value="true" id="isSpec">
    	<a href="javascript:void(0)" class="choose" onclick="$('.pro_eject').fadeIn(300);">选择 规格 数量</a>
    	</#if>
       
        <div class="sc_shop">
            <p>商品来源：</p>
            <p class="name">${dis_goods.distributorTitle!''}<a href="javascript:collectShop(${dis_goods.disId?c});">收藏店铺</a></p>
       </div>
       <div class="postage">
            <p>邮费说明：</p>
            <p>${distributor.postPrice?string('0.00')}元/满${distributor.maxPostPrice?string('0.00')}包邮</p>
       </div>
       <#if distributor.postInfo??>
       <div class="postage">
            <p>配送说明：</p>
            <p>${distributor.postInfo!''}</p>
       </div>
       </#if>
    </div>
    <input type="hidden" value="" id="specId">
  </section>
  <!-- 商品信息 END -->
  <!-- 选择商品参数弹出 -->
  <#if spec_list?? && spec_list?size gt 0>
   <aside class="pro_eject">
    <div class="part">
      <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
     
       <dl>
        <dt>选择规格</dt>
        <#list spec_list as item> 
        <dd> <a  onclick="chooseSpec($(this),${item.id?c})">${item.specifict!''}</a> </dd>
        </#list>
      </dl>
      <div class="number">
        <span>购买数量<font style="color:#999" id="left_font">（库存：${dis_goods.leftNumber?c!'0'}）</font></span>
        <a onclick="addNum();">+</a>
        <input type="text" class="text" value="1" id="quantity" onfocus="if(value=='1'||value=='0') {value='1'}" onblur="checkNumber(this.value)" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
        <a onclick="minusNum();">-</a>
        <input type="hidden" id="leftNumber" value="${dis_goods.leftNumber?c!'0'}">
      </div>
    </div>
  </aside>
  </#if>
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
              	<#assign len = item.username?length>	
                    <p>${item.username?substring(0,2)}****${item.username?substring(len-2,len)}<span>${item.commentTime?string("yyyy-MM-dd HH:hh:ss")}</span></p>
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
  
	<#if rele_list?? && rele_list?size gt 0>
  	<section class="product_list">
  		<p class="top">关联商品</p>
  	 	<ul id="goods-menu">
            <#list rele_list as goods>
            <#if goods_index lt 6>
        		<li>
			        <a href="/touch/goods/${goods.id?c}"><img src="${goods.coverImageUri!''}" /></a>
			        <a href="/touch/goods/${goods.id?c}" class="name">${goods.goodsTitle!""}</a>
			        <p class="price">¥ ${goods.goodsPrice?string("0.00")}<#if goods.unit?? && goods.unit != ''><span>/${goods.unit!''}</span></#if></p>
			        <div class="bot">
			        </div>
		        </li>
	        </#if>
            </#list>
  		</ul>
  	</section>
    </#if> 
	<style>
		.product_list{margin-top:0.1rem;}
		.product_list .top{height:0.7rem;background:#fff;padding-left:4%;line-height:0.7rem;color:#ff5b7d;margin-bottom:0.1rem;}
	</style>
  <!-- 底部 -->
  <div style="height:0.78rem;"></div>
  <section class="pro_foot">
    <a href="javascript:addCollect(${dis_goods.id?c})" <#if collect??>class="ed"</#if> onclick="$(this).toggleClass('ed')"></a>
    <#if dis_goods.isDistribution?? && dis_goods.isDistribution>
       <a onclick="byGoodsNow(${dis_goods.id?c})" title="立即购买" >立即预购</a>
   <#else>
    <a onclick="byGoodsNow(${dis_goods.id?c})" title="立即购买" >立即购买</a>
   </#if>
    <a onclick="addCart(${dis_goods.id?c});"  id="addCart">加入购物车</a>
  </section>
  <!-- 底部 END -->
  
</body>
</html>
