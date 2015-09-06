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
	//menuDownList("mainnavdown","#nav_down",".a2","sel");

	adChange("banner_box","banner_sum","banner_num",3000,1000);
	//楼层计算
	indexFloor("indexfloor","a");

	$(".float_box .ewm").hover(function(){
		$(this).next().show();
	},function(){
		$(this).next().hide();
	})
})

function submitSearch(){
    var search = document.getElementById("search_form");
    search.submit();
}
</script>

</head>

<body>
	<header class="main_top">
		<div class="main">
			<h1>您好！欢迎光临王明辉超市！</h1>
			<#if username??>
			    <a href="/user">${username}</a>
			    <a href="/logout">退出</a>
			<#else>
    			<a href="/login">请登陆</a>
    			<a href="/reg">注册</a>
			</#if>
			<menu class="top_menu">
				<a href="#">我的订单<span>丨</span></a>
				<a href="#">我的购物车<span>丨</span></a>
				<a href="#">超市会员<span>丨</span></a>
				<a href="#">客户服务<span>丨</span></a>
				<a href="/user/collect/list">我的收藏</a>
			</menu>
			<div class="clear"></div>
		</div>
	</header>
	<!--logo 搜索框部分-->
	<section class="main">
		<a href="/" class="logo"><img src="<#if site??>${site.logoUri!''}</#if>" /></a>
		<div class="choose_mar">
			<a href="javascript:void(0);" class="click_a" onclick="$('#mar_box').fadeIn(300);">选择地区超市</a>
		</div>
		<div class="m_box">
			<div class="search_box">
			     <form action="/search" method="get" id="search_form" >
        			 <input class="text" type="text" id="keywords" name="keywords" value="<#if keywords_list?? && keywords_list[0]??>${keywords_list[0].title!''}</#if>">
        			 <a href="javascript:submitSearch()">搜索</a>
				 </form>
			</div>
			<menu class="hot_search">
			     <#if keywords_list??>
                    <#list keywords_list as item>
                        <#if item_index gt 0>
                            <a href="/search?keywords=${item.title}"  >${item.title}<span>丨</span></a>
                        </#if>
                    </#list>
                </#if>
			</menu>
		</div>
		<div class="gu_car">
			<a href="/cart">去购物车结算<span>5</span></a>
		</div>
		<div class="clear"></div>
	</section>

	<!--选择超市弹出框-->
	<aside class="winbox" id="mar_box">
		<div class="mar_box">
			<p class="tit">请选择超市<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().fadeOut(300);"></a></p>
			<div class="select">
				<span>云南省</span>
				<select>
					<option>请选择&nbsp;&nbsp;市</option>
					<option>昆明市</option>
				</select>
				<select>
					<option>请选择&nbsp;&nbsp;区</option>
					<option>五华区</option>
				</select>
				<select>
					<option>请选择&nbsp;&nbsp;超市</option>
					<option>超市名字</option>
				</select>
				<input class="sub" type="submit" value="确定">
			</div>
			<div class="mar_list">
				<table>
					<tr>
						<th width="100">昆明市：</th>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
					</tr>
					<tr>
						<th width="100">昆明市：</th>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
					</tr>
				</table>
			</div>

		</div>
	</aside>

	<!--导航部分-->
	<nav class="nav_box">
		<div class="main">
			<section class="nav_list" id="mainnavdown">
				<a class="a2">全部商品分类</a>
				<ul id="nav_down" class="nav_down">
				    <#if top_cat_list??>
                        <#list top_cat_list as item>
					       <li>
						      <a href="/list/${item.id?c}" class="list">${item.title!''}</a><span>></span>
						      <div class="nav_show">
						          <#if ("second_level_"+item_index+"_cat_list")?eval?? >
							           <table>
							               <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
								            <tr>
									             <th width="60"><a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a></th>
									             <td>
									                  <#if ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval?? >
                                                            <#list ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval as thirdLevelItem>
                                                                <a href="/list/${thirdLevelItem.id?c}">${thirdLevelItem.title!''}</a>
                                                            </#list>
                                                      </#if>
									
									               </td>
								            </tr>
								            </#list>
								       </table>
								   </#if>
							    </div>
							  </li>
					      </#list>
					 </#if>
			     </ul>
			</section>
			<#if navi_item_list??>
                <#list navi_item_list as item>
                    <a class="a1" href="${item.linkUri!''}">${item.title!''}</a>
                </#list>
             </#if> 
             <!--
			<a href="#" class="a1 sel">首页</a>
            -->
			<div class="right_kx">
				<h3>超市快讯<a href="#">更多></a></h3>
				<ul>
				    <#if news_page??>
				        <#list news_page.content as item>
				            <#if item_index < 5 >
					            <li><a target="_blank" href="/info/content/${item.id}?mid=12&catId=${item.categoryId}">${item.title!''}</a></li>
					        </#if>
					    </#list>
					</#if>
				</ul>
				<div class="kx_banner"></div>
				<#if site.wxQrCode??>
    				<div class="ewm">
    					<div class="clear"></div>
        					<img src="${site.wxQrCode!''}"  width="93" height="93">
        					<p class="pt20">微信扫描</p>
        					<p>二维码</p>
    					<div class="clear"></div>
    				</div>
				</#if>
			</div>
		</div>
	</nav>
	<!--END nav-->
	<!--banner-->
	<section id="banner_box">
		<ul id="banner_sum">
		   <#if big_scroll_ad_list??>
                <#list big_scroll_ad_list as item>
                    <#if item_index < 3 >
            			<li style="display:block;">
            				<a href="${item.linkUri!''}" target="_blank">
                                  <img src="${item.fileUri!''}" />
                            </a>
            			</li>
            		 </#if>
                 </#list>
            </#if>
		</ul>
	</section>

	<!--左边楼层浮动-->
	<aside class="index_floor" id="indexfloor">
	   <#if top_category_list?? && top_category_list?size gt 0 >
           <#list top_category_list as item>
                <#if item_index == 0 >
        	       <a class="sel" href="javascript:void(0);"><span>${item.title!""}</span><span>${item.title!""}</span></a>
        	    <#elseif item_index lt 8 >
        	       <a href="javascript:void(0);"><span>${item.title!""}</span><span>${item.title!""}</span></a>
        	    </#if>
           </#list>
	   </#if>
	</aside>

	<!--右边悬浮框-->
	<#include "/client/common_float_box.ftl" />

	<!--main-->
	<section class="main">
		<div class="index_tj">
			<h3>今日推荐</h3>
			<div class="left_pic">
			    <#if new_goods_ad_list?? >
        			<a href="${new_goods_ad_list[0].linkUri!''}">
        			     <img src="${new_goods_ad_list[0].fileUri!''}" width="400px" height="400px">
        			</a>
    			</#if>
			</div>
			<ul class="right_pro">
			     <#if recommed_index_page?? && recommed_index_page.content?size gt 0 >
			         <#list recommed_index_page.content as item>
			             <#if item_index < 8 >
            				<li>
            					<a href="/goods/${item.id?c}">
            						<img src="${item.coverImageUri!''}" width="130px" height="130px">
            						<p class="p1">￥${item.salePrice?string('0.00')}</p>
            						<p class="p2">${item.stitle!""}</p>
            					</a>
            				</li>
            		      </#if>
            		  </#list>
            	  </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
        
        <!-- 一层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_one">
			    <#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 0>
				            <p class="tit">${item.title!''}</p>
            				<menu>
            				    <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                					<div class="clear"></div>
                					<#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                					    <#if secondLevelItem_index < 8 >
                					       <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                					    </#if>
                					</#list>
                					<div class="clear"></div>
            					</#if>
            				</menu>
            			</#if>
				    </#list>
				</#if>
				<#if index_1F_ad_list?? && index_1F_ad_list?size gt 0>
				    <a href="${index_1F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_1F_ad_list[0].fileUri!''}" ></a>
				</#if>
			</div>
			<ul class="right_list">
			 <#if top_cat_goods_page0?? && top_cat_goods_page0.content?size gt 0 >
			     <#list top_cat_goods_page0.content as item>
			         <#if item_index < 10 >
            			<li>
            				<a href="/goods/${item.id?c!''}" target="_blank">
            					<img src="${item.coverImageUri!''}" width="130px" height="130px";>
            					<p class="p1">￥${item.salePrice?string('0.00')}</p>
            					<p class="p2">${item.title!''}</p>
            				</a>
            			</li>
				     </#if>
				 </#list>
			 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
        
        <!--  二层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_two">
				<#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 1>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_2F_ad_list?? && index_2F_ad_list?size gt 0>
                    <a href="${index_2F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_2F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
    			 <#if top_cat_goods_page1?? && top_cat_goods_page1.content?size gt 0 >
                     <#list top_cat_goods_page1.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
        
        <!--  三层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_three">
				<#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 2>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_3F_ad_list?? && index_3F_ad_list?size gt 0>
                    <a href="${index_3F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_3F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
    			 <#if top_cat_goods_page2?? && top_cat_goods_page2.content?size gt 0 >
                      <#list top_cat_goods_page2.content as item>
                             <#if item_index < 10 >
                                <li>
                                    <a href="/goods/${item.id?c!''}" target="_blank">
                                        <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                        <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                        <p class="p2">${item.title!''}</p>
                                    </a>
                                </li>
                             </#if>
                       </#list>
                  </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

        <!-- 四层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_four">
			     <#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 3>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_4F_ad_list?? && index_4F_ad_list?size gt 0>
                    <a href="${index_4F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_4F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
			     <#if top_cat_goods_page3?? && top_cat_goods_page3.content?size gt 0 >
                     <#list top_cat_goods_page3.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

        <!--  五层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_five">
			     <#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 4>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_5F_ad_list?? && index_5F_ad_list?size gt 0>
                    <a href="${index_5F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_5F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list"> 
			   <#if top_cat_goods_page4?? && top_cat_goods_page4.content?size gt 0 >
                     <#list top_cat_goods_page4.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

        <!--  六层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_six">
				<#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 5>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_6F_ad_list?? && index_6F_ad_list?size gt 0>
                    <a href="${index_6F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_6F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
			     <#if top_cat_goods_page5?? && top_cat_goods_page5.content?size gt 0 >
                     <#list top_cat_goods_page5.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

        <!-- 七层  -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_seven">
				<#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 6>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_7F_ad_list?? && index_7F_ad_list?size gt 0>
                    <a href="${index_7F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_7F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
			     <#if top_cat_goods_page6?? && top_cat_goods_page6.content?size gt 0 >
                     <#list top_cat_goods_page6.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

        <!--  八层    -->
		<div class="index_part">
			<div class="clear"></div>
			<div class="left_eight">
				<#if top_category_list?? && top_category_list?size gt 0 >
                    <#list top_cat_list as item>
                        <#if item_index == 7>
                            <p class="tit">${item.title!''}</p>
                            <menu>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <div class="clear"></div>
                                    <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                        <#if secondLevelItem_index < 8 >
                                            <a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a>
                                        </#if>
                                    </#list>
                                    <div class="clear"></div>
                                </#if>
                            </menu>
                        </#if>
                    </#list>
                </#if>
                <#if index_8F_ad_list?? && index_8F_ad_list?size gt 0>
                    <a href="${index_8F_ad_list[0].linkUri!''}" class="b_pic"><img src="${index_8F_ad_list[0].fileUri!''}" ></a>
                </#if>
			</div>
			<ul class="right_list">
			     <#if top_cat_goods_page7?? && top_cat_goods_page7.content?size gt 0 >
                     <#list top_cat_goods_page7.content as item>
                         <#if item_index < 10 >
                            <li>
                                <a href="/goods/${item.id?c!''}" target="_blank">
                                    <img src="${item.coverImageUri!''}" width="130px" height="130px";>
                                    <p class="p1">￥${item.salePrice?string('0.00')}</p>
                                    <p class="p2">${item.title!''}</p>
                                </a>
                            </li>
                         </#if>
                     </#list>
                 </#if>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
	</section>

    <!--底部footer-->
    <#include "/client/common_footer.ftl" />
</body>
</html>