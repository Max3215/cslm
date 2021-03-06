﻿<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script type="text/javascript" src="/client/js/swfupload.js"></script>
<script type="text/javascript" src="/client/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/client/js/swfupload.handlers.js"></script>
<script type="text/javascript">
$(document).ready(function(){
 navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");
 

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})


$(function () {

    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            btntext : "更改头像",
            sendurl: "/client/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
});

</script>
<script type="text/javascript">
   function testImg(imageUrl)
   {
       var element = document.getElementsByName("imgUrl");
       var oneElement = element[0];
       imageUrl=$("#imageURL").attr("src");
       if (null == imageUrl)
        {
            return;
        }
        $.post("/user/headImageUrl",{"imgUrl": imageUrl},function(date){
            if(date.code == 1){
                alert(date.msg);
                window.location.href = "/login";
            }else{
                location.reload();
            }
        })
   }
</script>

<!--[if IE]>
   <script src="/cliemt/js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/client/js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>
    <!--  顶部  -->
    <#include "/client/common_header.ftl" />

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  
  
 <!-- 左侧 -->
  <#include "/client/common_user_menu.ftl" />
  
  <div class="mymember_center">
    <div class="mymember_info mymember_info01">
      <table>
        <tr>
            <th width="150" rowspan="2">
                <a class="mymember_header test_img" href="javascript:;">
                    <img class="test_img1"  src="${user.headImageUri!'/client/images/user_images.jpg'}"  width="120" height="120"/>
                </a>
                <div style="margin-left:25px;margin-top:10px;" class="upload-box upload-img"></div>
            </th>
            <#--
          <th width="150" rowspan="2">
               <a class="mymember_header" ><img src="${user.headImageUri!'/mag/style/user_avatar.png'}" /></a>
          </th>
          -->
          <td><a href="/user/order/list/2"><img src="/client/images/mymember/buy01.png" />待付款：<span>${total_unpayed!'0'}</span></a></td>
          <td><a href="/user/order/list/3"><img src="/client/images/mymember/buy02.png" />待发货：<span>${total_undelivered!'0'}</span></a></td>
          <th rowspan="2" class="mymember_fen">
            <a href="/user/point/list"><img src="/client/images/mymember/buy05.png" /><p>积分：<span>${user.totalPoints!'0'}</span></p></a>
          </th>
          <th rowspan="2" class="mymember_fen">
            <a href="/user/virtualMoney/list"><img src="/client/images/mymember/buy06.png" /><p>余额：<span><#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if></span></p></a>
          </th>
        </tr>
        <tr>
          <td><a href="/user/order/list/4"><img src="/client/images/mymember/buy03.png" />待收货：<span>${total_unreceived!'0'}</span></a></td>
          <td><a href="/user/order/list/6"><img src="/client/images/mymember/buy04.png" />已完成：<span>${total_finished!'0'}</span></a></td>
        </tr>
      </table>
    </div>
    
    <div class="mymember_info mymember_info02">
      <h3>我的订单<a href="/user/order/list/0">查看全部订单</a></h3>
      <table width="100%">   
           <#if order_page??>
               <#list order_page.content as order>
                    <#if order_index < 2 >         
                        <tr>
                            <th colspan="7">订单编号：<a href="/user/order?id=${order.id?c}" target="_blank">${order.orderNumber!''}</a></th>
                        </tr>
                        <tr>
                            <td align="left"  colspan="2">
                                <#if order.orderGoodsList??>
                                    <#list order.orderGoodsList as og>  
                                        <#if og_index < 1>
                                            <a href="/goods/${og.goodsId?c}"><img src="${og.goodsCoverImageUri!''}" alt="${og.goodsTitle!''}"  width="50" height="50px" align="left" /></a>
                                        </#if>
                                    </#list>
                                </#if>
                            </td>
                            <td>
                                ${order.shippingName!''}
                            </td>
                            <td>
                                <p>￥${order.totalPrice?string("0.00")}</p>
                            </td>
                            <td class="td003">
                                <p>${order.orderTime!''}</p>
                            </td>
                            <td>
                                <#if order.statusId?? && order.statusId==2>
                                      <p>待付款</p>
                                <#elseif order.statusId?? &&  order.statusId==3>
                                       <p>待发货</p>
                                <#elseif order.statusId?? &&  order.statusId==4>
                                       <p>待收货</p>
                                <#elseif order.statusId?? &&  order.statusId==5>
                                       <p>待评价</p>
                                <#elseif order.statusId?? &&  order.statusId==6>
                                       <p>已完成</p>
                                <#elseif order.statusId?? &&  order.statusId==7>
                                       <p>已取消</p>
                                       
                                </#if>
                            </td>
                            <td class="td003">
                                 <a href="/user/order?id=${order.id?c}" target="_blank">查看</a>          
                            </td>
                         </tr>
                    </#if>
                </#list>
            </#if>    
      </table>
    </div><!--mymember_info END-->
    
    <div class="mymember_info mymember_info02">
	   <h3>我的收藏</h3>
       <div id="mymember_gzbox">
        <ul>
            <li>
                <#if collect_page?? >
                    <#list collect_page.content as item>
                        <a class="mymember_gzlist" href="/goods/${item.distributorId?c!''}" target="_blank">
                            <img src="${item.goodsCoverImageUri!''}" alt="${item.goodsTitle!''}"  width="180px" height="180px"/>
                            <p>${item.goodsTitle!''}</p>
                            <h6>￥${item.goodsSalePrice?string('0.00')}</h6>
                        </a>
                    </#list>
                </#if>
            </li>
        </ul>
        <div class="myclear"></div>
            <a id="mymember_gznext" ><img src="/client/images/mymember/arrow02.jpg" /></a>
        </div>
    </div><!--mymember_info END--><!--mymember_info END-->
  </div><!--mymember_center END-->
  
  <div class="mymember_hot">
      <div class="mymember_hot_part">
            <h3>会员推荐</h3>
            <h4 id="mymember_right_menu">
                <a class="mysel" title="推荐">推荐 >> </a>
                <a title="热卖">热卖 >> </a>
            </h4>
            <ul id="mymember_right_check">
                  <li>
                      <#if recommend_goods_page??>
                          <#list recommend_goods_page.content as item>
                              <#if item_index < 4 >
                                  <a class="mymember_hot_list" href="/goods/${item.id?c}" target="_blank">
                                       <img src="${item.coverImageUri!''}"  width="75" height="75"/>
                                       <p>${item.goodsTitle!''}</p>
                                       <b>￥${item.goodsPrice?string('0.00')}</b>
                                   </a>
                              </#if>
                          </#list>
                      </#if>
                 </li>
                 <li>
                      <#if hot_goods_page??>
                           <#list hot_goods_page.content as item>
                               <#if item_index < 4 >
                                   <a class="mymember_hot_list" href="/goods/${item.id?c}" target="_blank">
                                       <img src="${item.coverImageUri!''}"  width="75" height="75"/>
                                       <p>${item.goodsTitle!''}</p>
                                       <b>￥${item.goodsPrice?string('0.00')}</b>
                                   </a>
                               </#if>
                           </#list>
                       </#if>
                  </li>
            </ul>
      </div><!--mymember_hot_part END-->
    
      <div class="mymember_hot_part">
            <h3>浏览历史</h3>
            <div id="mymember_storybox">
                <ul>
                    <li>
                        <#if recent_page??>
                            <#list recent_page.content as rgoods>
                                 <a class="mymember_hot_story" href="/goods/${rgoods.goodsId?c}" target="_blank">
                                    <img src="${rgoods.goodsCoverImageUri!''}"  width="65" height="65"/>
                                    <p>￥${rgoods.goodsSalePrice?string("0.00")}</p>
                                 </a>
                            </#list>
                         </#if>    
                    </li>
                </ul>
            <div class="myclear"></div>
                <a id="mymember_story_next" href="javascript:;"><img src="/client/images/mymember/arrow02.jpg" /></a>
            </div>
            <div class="myclear"></div>
       </div><!--mymember_hot_part END-->
  </div><!--mymember_hot END-->
<div class="clear"></div>
</div>
</div>
<!--底部footer-->
<#include "/client/common_footer.ftl" />

<script type="text/javascript">
      $(document).ready(function(){
         mymemberMenuCheck("mymember_right_menu","a","mymember_right_check","li","mysel");
		 mymemberRightMove("mymember_storybox",70,90,"mymember_story_next",15,3,"a");
		 mymemberRightMove("mymember_gzbox",205,241,"mymember_gznext",15,3,"a");
		 mymemberRightMove("mymember_shinebox",205,310,"mymember_shinenext",15,3,"div");
      });
</script>


</body>
</html>




  











