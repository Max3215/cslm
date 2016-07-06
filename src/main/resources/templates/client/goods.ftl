<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<title>${dis_goods.goodsTitle!''}-<#if site??>${site.seoTitle!''}</#if></title>
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/client/js/big_photo.js"></script>
<script type="text/javascript" src="/client/js/goods.js"></script>
<script type="text/javascript" src="/client/js/goods_comment_consult.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
    
    //调用方法
//$("#quantity").keypress(function () {
//    $("#quantity").val(stripscript($(this).val()));
//    })
})

//替换特殊字符
function stripscript(s) {
    var pattern = new RegExp("[`~%!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）;—|{}【】‘；：”“'。，、？]")
    var rs = "";
    for (var i = 0; i < s.length; i++) {
        rs = rs + s.substr(i, 1).replace(pattern, '');
    }
  
    return rs;
}


function addNum(){
    var q = parseInt($("#quantity").val());
    var ln = parseInt($("#leftNumber").val());
    console.debug(ln)
 //   <#if dis_goods?? && dis_goods.leftNumber??>
        if (q < ${dis_goods.leftNumber?c!'0'})
        {
            $("#quantity").val(q+1);
        }
        else
        {
            alert("已达到库存最大值");
        }
//   <#else>
//        $("#quantity").val(q+1);
//        $("#number").val(q+1);
//  </#if>
    $("#addCart").attr("href", "/cart/init?id=${dis_goods.id?c}&quantity=" + $("#quantity").val());
    $("#proGoods").attr("href", "/order/proGoods/${dis_goods.id?c}?quantity=" + $("#quantity").val());
    $("#buyNow").attr("href", "/order/byNow/${dis_goods.id?c}?quantity=" + $("#quantity").val());
}

<#-- 减少商品数量的方法 -->
function minusNum(){
    var q = parseInt($("#quantity").val());
        
    if (q > 1){
        $("#quantity").val(q-1);
        $("#number").val(q-1);
    }
    $("#addCart").attr("href", "/cart/init?id=${dis_goods.id?c}&quantity=" + $("#quantity").val());
    $("#proGoods").attr("href", "/order/proGoods/${dis_goods.id?c}?quantity=" + $("#quantity").val());
    $("#buyNow").attr("href", "/order/byNow/${dis_goods.id?c}?quantity=" + $("#quantity").val());
}

<!--  加入购物车   -->
function cartInit(dId){
    var quantity = document.getElementById("quantity").value;
    if(quantity <= 0 || "" == quantity ){
        return;
    }
 <!--   var newTab=window.open('about:blank');-->
    $.ajax({
        type: "get",
        url: "/goods/incart",
        data: {"id":dId,"quantity":quantity},
        success: function (data) { 
            if(data.msg){
                alert(data.msg);
                return;
            }
            window.location.href="/cart/init?id="+dId+"&quantity="+quantity;
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
 //   window.open("/order/byNow/"+dId+"?quantity="+quantity);
    window.location.href = "/order/byNow/"+dId+"?quantity="+quantity;
}
function showmsg(){
    alert("库存不足！");
    return;
}

function proGoods(did)
{
    var quantity = document.getElementById("quantity").value;
    
    // window.location.href = "/order/proGoods/"+did+"?quantity="+quantity;
    window.open("/order/proGoods/"+did+"?quantity="+quantity);
}

</script>

</head>

<body>
    <!--  顶部  -->
    <#include "/client/common_header.ftl" />
    
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/" title="首页">首页</a>
            <#if category_tree_list??> 
                <#list category_tree_list as item> 
                    &nbsp;&nbsp;&gt;&nbsp;&nbsp; 
                    <a href="/list/${item.id?c}" title="${item.title!''}" >${item.title!''}</a>
                </#list> 
            </#if>
            <#if goods??> 
                &nbsp;&nbsp;&gt;&nbsp;&nbsp;
                <a href="/goods/${dis_goods.id?c}" title="${dis_goods.title!''}">${dis_goods.title!''}</a>
            </#if>
        </p>
	</section>

	<!--右边悬浮框-->
    <#include "/client/common_float_box.ftl" />
    
	<!--main-->
	<div class="main">
		<div class="pro_box">
			<div class="clear"></div>
			<section class="proinfo_left">
				<div id="proinfo_left">
					<div class="propic_main">
					   <#if goods.coverImageUri??>
						    <img src="${goods.coverImageUri!''}" width="498px" height="498px">
					   </#if>
					</div>
					<span class="spe_leftBtn">&lt;</span>
					<span class="spe_rightBtn">&gt;</span>
					<div class="propic_num"> 
						<ul>
						<#if goods.coverImageUri??>
                            <li style="display:none"><img src="${goods.coverImageUri!''}"></li>
                       </#if>
						<#if goods.showPictures??> 
                            <#list goods.showPictures?split(",") as uri>
                                <#if uri!="">
                                    <li><img src="${uri!''}"/></li>
                                </#if>
                            </#list>
                        </#if>
						</ul>
					</div>
				</div>
				<div class="clear"></div>
				<#--
				<div class="share">点击分享：</div>
				-->
				<#if dis_goods??><a href="javascript:addCollect(${dis_goods.id?c})" class="love" title="收藏商品">收藏商品</a></#if>
			</section>
			<section class="proinfo_right">
				<h2>${dis_goods.goodsTitle!''}</h2>
				<p class="subtitle">${dis_goods.subGoodsTitle!''}</p>
				<p class="num"><#if goods.code??>商品编号：<span>${goods.code!''}</span></#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#if goods.brandTitle??>商品品牌：<span>${goods.brandTitle!''}</span></#if></p>
				<div class="price">
					<#if dis_goods??><p>特惠价：<span>￥${dis_goods.goodsPrice?string('0.00')}</span><#if dis_goods.unit??>/${dis_goods.unit!''}</#if></p></#if>
					<p class="lth">实体店价：￥<#if dis_goods?? && dis_goods.goodsMarketPrice??>${dis_goods.goodsMarketPrice?string('0.00')}<#else>${goods.marketPrice?string("0.00")}</#if><#if dis_goods.unit??>/${dis_goods.unit!''}</#if></p>
				</div>
				
				<!-- 参数开始   -->
				<#if total_select??> 
                    <#if 1==total_select>
        				<menu class="choose">
        					<span>${select_one_name!''}：</span>
        					<#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
        				</menu>
                    <#elseif 2==total_select>
                        <menu class="choose">
                            <span>${select_one_name!''}：</span>
                            <#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_two_name!''}：</span>
                            <#if select_two_goods_list??> 
                                 <#list select_two_goods_list as item> 
                                     <a <#if item.selectTwoValue==two_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectTwoValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                    <#elseif 3==total_select>
                        <menu class="choose">
                            <span>${select_one_name!''}：</span>
                            <#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_two_name!''}：</span>
                            <#if select_two_goods_list??> 
                                 <#list select_two_goods_list as item> 
                                     <a <#if item.selectTwoValue==two_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectTwoValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_three_name!''}：</span>
                            <#if select_three_goods_list??> 
                                 <#list select_three_goods_list as item> 
                                     <a <#if item.selectThreeValue==three_selected>class="sel"</#if>href="/goods/${item.id?c}">${item.selectThreeValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                    </#if>
                </#if>
				<!--  参数结束    -->
				<script>
				function checkNumber(num)
				{
				    if (num==''|| num=='0') 
				    {
				        $("#quantity").val(1);
				        return ;
				    }
				    // 验证手动输入数量库存
				    $.ajax({
                            type: "get",
                            url: "/goods/incart",
                            data: {"id":${dis_goods.id?c},"quantity":num},
                            success: function (data) { 
                                if(data.code== 0 ){
                                    alert(data.msg);
                                    $("#quantity").val(1);
                                    return;
                                }else{
                                    $("#quantity").val(num);
                                    $("#addCart").attr("href", "/cart/init?id=${dis_goods.id?c}&quantity=" +num);
                                    $("#proGoods").attr("href", "/order/proGoods/${dis_goods.id?c}?quantity=" + num);
                                    $("#buyNow").attr("href", "/order/byNow/${dis_goods.id?c}?quantity=" + num);
                                }
                            }
                        });
				}
				
				</script>
				<p class="digital">
					<#if dis_goods??>
					<span>数量：</span>
					<a id="id-minus" href="javascript:minusNum();">-</a>
					<input class="text" type="text" id="quantity" value="1" onfocus="if(value=='1'||value=='0') {value='1'}" onblur="checkNumber(this.value)" onkeyup="value=value.replace(/[^0-9]/g,'')"/>
					<a id="id-plus" href="javascript:addNum();">+</a>
					<label>库存${dis_goods.leftNumber!'0'}</label>
					<input type="hidden" id="leftNumber" value="${dis_goods.leftNumber?c!'0'}">
					<#else>
					<lable sytle="color:red">*当前超市没有此商品，您可以选择其他超市继续购买</label>
					</#if>
					<div class="clear"></div>
				
                <p>&nbsp;  </p>
                <p class="num"><#if distributor.postPrice??>本店（${distributor.title!''}）配送费<span style="color:#ff5b7d">￥${distributor.postPrice?string('0.00')}</span>&nbsp;</#if>
                    <#if distributor.maxPostPrice??>满<span style="color:#ff5b7d">￥${distributor.maxPostPrice?string('0.00')}</span>&nbsp;包邮</#if></p>
                 <p ><span style="color:#ff5b7d"><#if distributor.postInfo??>*${distributor.postInfo!''}</#if></span></p>
				<div class="buy_btn">
					<div class="clear"></div>
					<#if dis_goods??>
				          <#if dis_goods.leftNumber?? && dis_goods.leftNumber gt 0>
        					   <#if dis_goods.isDistribution?? && dis_goods.isDistribution>
        					       <a href="/order/proGoods/${dis_goods.id?c}" target="_blank"  title="预购商品" class="car" id="proGoods">立即预购</a>
        					   <#else>
            					   <a href="/order/byNow/${dis_goods.id?c}" id="buyNow" target="_blank" title="立即购买" class="buy">立即购买</a>
        					   </#if>
    					   <#else>
    					       <a href="javascript:; " onclick="showmsg();"  title="立即购买" class="buy">立即购买</a>
    					   </#if>
					       <#if dis_goods.leftNumber?? && dis_goods.leftNumber gt 0>
        					<a href="/cart/init?id=${dis_goods.id?c}" target="_blank" title="加入购物车" class="car" id="addCart">加入购物车</a>
        					<#else>
        					<a href="javascript:; " onclick="showmsg();"  title="加入购物车" class="car" id="addCart">加入购物车</a>
        					</#if>
					</#if>
					<div class="clear"></div>
				</div>
			</section>
			<div class="clear"></div>
		</div>

		<div class="info_box">
			<div class="clear"></div>
			<section class="info_left">
				<p class="tit">
					<a href="#detail_tit" tid="0" class="sel stab">商品详情</a>
					<a href="#detail_tit" tid="1" class="stab">商品评价</a>
					<a href="#detail_tit" tid="2" class="stab">销售记录</a>
				</p>
				<!-- 商品详情  -->
				<div class="text_pic php_z" id="tab0">
					<#--
					<ul class="pic_ul">
						<p class="pro_cs"></p>
						<#if goods.paramList??>
                            <#list goods.paramList as param>
                                <#if param.value??>
    						     <li>${param.paramName!''}：${param.value!''}</li>
    						     </#if>
    						</#list>
    				    </#if>
					</ul>
					<div class="clear"></div>
					-->
					<div class="con">
						${goods.detail!''}
					</div>
				</div><!--  END   -->
				
				<!--  商品评价    -->
				<div class="php_z" id="tab1" style="display:none">
    				<#include "/client/goods_comment.ftl">
                </div><!--   END   -->
                
                <!--  成交记录  -->
                <div class="php_z" id="tab2" style="display:none">
                    <#include "/client/goods_record.ftl">
                </div>
                
			</section>
			<div class="right_hot">
				<p class="tit">大家都在看</p>
				<ul>
            	    <#if dis_hot_list?? && dis_hot_list?size gt 0> 
                        <#list dis_hot_list as hot_good> 
                            <#if hot_good_index lt 6>
                                <li>
                                    <a href="/goods/${hot_good.id?c}" title="${hot_good.goodsTitle!''}" target="_blank">
                                        <img src="${hot_good.coverImageUri!''}">
                                        <p>${hot_good.goodsTitle!''}</p>
                                    </a>
                                    <p class="price">￥${hot_good.goodsPrice?string("0.00")}</p>
                                </li>
                             </#if>
                         </#list>
                    </#if>
				</ul>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<!--  底部    -->
    <#include "/client/common_footer.ftl" />
</body>
</html>