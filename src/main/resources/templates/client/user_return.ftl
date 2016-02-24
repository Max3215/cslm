
<!DOCTYPE html>
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
</script>
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/client/js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>
    <#include "/client/common_header.ftl">
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    <#include "/client/common_user_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search">申请退货
          <div class="clear"></div>
        </div>
        
        <table align="left">
             <tbody>
                <tr class="mymember_infotab_tit01">
                    <th width="70">订单号</th>
                    <th>商品信息</th>
                    <th width="80">下单时间</th>
                 </tr>
          <#if order_page?? && order_page.content?size gt 0>
               <#list order_page.content as order>
                    <tr>
                        <td><a href="/user/order?id=${order.id?c}">${order.orderNumber!''}</a></td>
                        <td align="left">
                            <table width="100" border="0" align="left">
                                <tbody>
                                    <tr>
                                    <#if order.orderGoodsList??>
                                    <#list order.orderGoodsList as og>
                                        <td>
                                            <img width="50" height="50" title="${og.goodsTitle!''}" src="${og.goodsCoverImageUri!''}"> <br>
                                            <#if og.isReturnApplied><a>已申请退货</a><#else><a href="/user/return/${order.id?c}?id=${og.id?c}">退货</a></#if>
                                        </td>
                                    </#list>
                                    </#if>
                                    </tr>
                                </tbody>
                              </table>
                          </td>
                            <td>
                                <p>${order.orderTime?string('yyyy-MM-dd')}</p>
                                <p>${order.orderTime?string('HH:mm:ss')}</p>
                            </td>
                      </tr>
               </#list>
          </#if>
            </tbody>
        </table>
        <div class="myclear h10"></div>
        <div class="mymember_page">
         <#if order_page??>
                <#assign continueEnter=false>
                <#if order_page.totalPages gt 0>
                    <#list 1..order_page.totalPages as page>
                        <#if page <= 3 || (order_page.totalPages-page) < 3 || (order_page.number+1-page)?abs<3 >
                            <#if page == order_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/user/goods/return/?page=${page-1}">${page}</a>
                            </#if>
                            <#assign continueEnter=false>
                        <#else>
                            <#if !continueEnter>
                                <b class="pn-break">&hellip;</b>
                                <#assign continueEnter=true>
                            </#if>
                        </#if>
                    </#list>
                </#if>
            </#if>
         </div>
		      
	     </div>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>