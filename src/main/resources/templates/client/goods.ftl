<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<title><#if site??>${site.seoTitle!''}-</#if>超市联盟</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/client/js/goods.js"></script>
<script type="text/javascript" src="/client/js/goods_comment_consult.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	$(".click_a").click(function(){
		if($(this).next().is(":visible")==false){
			$(this).next().slideDown(300);
		}else{
			$(this).next().slideUp(300);
		}
	});//选择超市下拉效果

	$("#nav_down li").hover(function(){
		$(this).find(".nav_show").fadeIn(10);
	},function(){
		$(this).find(".nav_show").stop(true,true).fadeOut(10);
	})	

	$(".float_box .ewm").hover(function(){
		$(this).next().show();
	},function(){
		$(this).next().hide();
	})
})

function addNum(){
    var q = parseInt($("#quantity").val());
    <#if goods.leftNumber??>
        if (q < ${goods.leftNumber!'0'})
        {
            $("#quantity").val(q+1);
        }
        else
        {
            alert("已达到库存最大值");
        }
   <#else>
        $("#quantity").val(q+1);
   </#if>
    $("#addCart").attr("href", "/cart/init?id=${goods.id?c}&quantity=" + $("#quantity").val());
}

<#-- 减少商品数量的方法 -->
function minusNum(){
    var q = parseInt($("#quantity").val());
        
    if (q > 1){
        $("#quantity").val(q-1);
    }
    $("#addCart").attr("href", "/cart/init?id=${goods.id?c}&quantity=" + $("#quantity").val());
}

<!--  加入购物车   -->
function cartInit(){
    var quantity = document.getElementById("quantity").value;
    window.location.href = "/cart/init?id=${goods.id}&quantity="+quantity;
}

<!--  立即购买   -->
function byNow(){
    var quantity = document.getElementById("quantity").value;
    window.location.href = "/order/byNow/${goods.id}?quantity="+quantity;
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
                    <a href="/list/${item.id}" title="${item.title!''}" >${item.title!''}</a>
                </#list> 
            </#if>
            <#if goods??> 
                &nbsp;&nbsp;&gt;&nbsp;&nbsp;
                <a href="/goods/${goods.id}" title="${goods.title!''}">${goods.title!''}</a>
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
						    <img src="${goods.coverImageUri!''}">
					   </#if>
					</div>
					<span class="spe_leftBtn">&lt;</span>
					<span class="spe_rightBtn">&gt;</span>
					<div class="propic_num"> 
						<ul>
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
				<a href="javascript:addCollect(${goods.id})" class="love" title="收藏商品">收藏商品</a>
			</section>
			<section class="proinfo_right">
				<h2>${goods.title!''}</h2>
				<p class="subtitle">${goods.subTitle!''}</p>
				<p class="num"><#if goods.code??>商品编号：<span>${goods.code!''}</span></#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<#if goods.brandTitle??>商品品牌：<span>${goods.brandTitle!''}</span></#if></p>
				<div class="price">
					<p>特惠价：<span>￥${goods.salePrice?string("0.00")}</span></p>
					<p class="lth">市场价：￥${goods.marketPrice?string("0.00")}</p>
				</div>
				
				<!-- 参数开始   -->
				<#if total_select??> 
                    <#if 1==total_select>
        				<menu class="choose">
        					<span>${select_one_name!''}：</span>
        					<#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
        				</menu>
                    <#elseif 2==total_select>
                        <menu class="choose">
                            <span>${select_one_name!''}：</span>
                            <#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_two_name!''}：</span>
                            <#if select_two_goods_list??> 
                                 <#list select_two_goods_list as item> 
                                     <a <#if item.selectTwoValue==two_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectTwoValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                    <#elseif 3==total_select>
                        <menu class="choose">
                            <span>${select_one_name!''}：</span>
                            <#if select_one_goods_list??> 
                                 <#list select_one_goods_list as item> 
                                     <a <#if item.selectOneValue==one_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectOneValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_two_name!''}：</span>
                            <#if select_two_goods_list??> 
                                 <#list select_two_goods_list as item> 
                                     <a <#if item.selectTwoValue==two_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectTwoValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                        <menu class="choose">
                            <span>${select_three_name!''}：</span>
                            <#if select_three_goods_list??> 
                                 <#list select_three_goods_list as item> 
                                     <a <#if item.selectThreeValue==three_selected>class="sel"</#if>href="/goods/${item.id}">${item.selectThreeValue}</a> 
                                 </#list>
                            </#if>
                        </menu>
                    </#if>
                </#if>
				<!--  参数结束    -->
				
				<p class="digital">
					<span>数量：</span>
					<a id="id-minus" href="javascript:minusNum();">-</a>
					<input class="text" type="text" id="quantity" value="1">
					<a id="id-plus" href="javascript:addNum();">+</a>
					<label>库存${goods.leftNumber!'0'}件</label>
					<div class="clear"></div>
				</p>
				<div class="buy_btn">
					<div class="clear"></div>
					<!--
					<a href="javascript:byNow();" target="_blank" title="立即购买" class="buy">立即购买</a>
					-->
					<a href="javascript:cartInit();" target="_blank" title="加入购物车" class="car">加入购物车</a>
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
					<a href="#detail_tit" tid="2" class="stab">成交记录</a>
				</p>
				<!-- 商品详情  -->
				<div class="text_pic php_z" id="tab0">
					<ul>
						<p class="pro_cs">商品参数</p>
						<#if goods.paramList??>
                            <#list goods.paramList as param>
    						     <li>${param.paramName!''}：${param.value!''}</li>
    						</#list>
    				    </#if>
					</ul>
					<div class="clear"></div>
					<div class="con">
						${goods.detail!''}
					</div>
				</div><!--  END   -->
				
				<!--  商品评价    -->
				<div class="php_z" id="tab1" style="display:none">
    				<div class="evaluate">
                        <ul>
                            <#if comment_page??>
                                <#list comment_page.content as item>
                                    <li>
                                        <p class="p1">${item.content!''}<span>口味：其他口味：其他</span>${item.username!''}</p>
                                        <p class="p2">${item.commentTime?string("yyyy-MM-dd HH:hh:ss")}</p>
                                         <#if item.isReplied?? && item.isReplied>
                                            <p class="p3">商家回复：${item.reply!''}</p>
                                         </#if>
                                    </li>
                                </#list>
                            </#if> 
                        </ul>
                    </div>
                    <#if comment_page??>
                    <div class="pages">
                        <span>共${comment_page.content?size!''}条记录&nbsp;&nbsp;&nbsp;&nbsp;${comment_page.number}/${comment_page.totalPages!'0'}页</span>
                        <#assign continueEnter=false>
                        <#if comment_page?? && comment_page.number+1 == 1>
                            <a  href="javascript:;">上一页</a>
                        <#else>
                            <a href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${comment_page.number-1});">上一页</a>
                        </#if>
                        
                        <#if comment_page.totalPages gt 0>
                            <#list 1..comment_page.totalPages as page> 
                                <#if page <= 3 || (comment_page.totalPages-page) < 3 || (comment_page.number+1-page)?abs<3 >
                                    <#if page == comment_page.number+1>
                                        <a class="sel" href="javascript:;"">${page}</a>
                                    <#else>
                                        <a href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${page-1});">${page}</a>
                                    </#if>
                                    <#assign continueEnter=false>
                                <#else>
                                    <#if !continueEnter>
                                        <span> ... </span>
                                        <#assign continueEnter=true>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                        
                        <#if comment_page.number+1 == comment_page.totalPages || comment_page.totalPages==0>
                            <a href="javascript:;">下一页</a>
                        <#else>
                            <a  href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${comment_page.number+1});">下一页</a>
                        </#if>
                    </div>
                    </#if>
                </div><!--   END   -->
                
                <!--  成交记录  -->
                <div class="php_z" id="tab2" style="display:none">
                    <div class="deal_list">
                        <table>
                            <thead>
                                <tr>
                                    <td>买家</td>
                                    <td>款式/型号</td>
                                    <td>数量</td>
                                    <td>成交时间</td>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>152****5448</td>
                                    <td>口味：其他</td>
                                    <td>1</td>
                                    <td>2015-01-15<span>15:01:15</span></td>
                                </tr>
                                <tr>
                                    <td>152****5448</td>
                                    <td>口味：其他</td>
                                    <td>1</td>
                                    <td>2015-01-15<span>15:01:15</span></td>
                                </tr>
                                <tr>
                                    <td>152****5448</td>
                                    <td>口味：其他</td>
                                    <td>1</td>
                                    <td>2015-01-15<span>15:01:15</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="pages">
                        <span>共388条记录&nbsp;&nbsp;&nbsp;&nbsp;1/19页</span>
                        <a href="#">上一页</a>
                        <a href="#" class="sel">1</a>
                        <a href="#">2</a>
                        <a href="#">3</a>
                        <span>...</span>
                        <a href="#">19</a>
                        <a href="#">下一页</a>
                    </div>
                </div>
                
			</section>
			<div class="right_hot">
				<p class="tit">大家都在看</p>
				<ul>
				    <#if hot_list?? && hot_list?size gt 0> 
                        <#list hot_list as hot_good> 
                            <#if hot_good_index lt 6>
            					<li>
            						<a href="/goods/${hot_good.id?c}" title="${hot_good.title!''}" target="_blank">
            							<img src="${hot_good.coverImageUri!''}">
            							<p>${hot_good.title!''}</p>
            						</a>
            						<p class="price">￥${goods.salePrice?string("0.00")}<span>原价：￥${goods.marketPrice?string("0.00")}</span></p>
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