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

<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>

<script type="text/javascript" src="/client/js/point_goods.js"></script>
<script src="/layer/layer.js"></script>
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
    <!--  顶部  -->
    <#include "/client/common_header.ftl" />

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

    <!-- 左侧 -->
  <#include "/client/common_user_menu.ftl" />
  
  <div class="mymember_mainbox">
    <div class="mymember_info mymember_info04">
      <h3>订单详细</h3>
      <dl>
        <dt>订单编号：${order.orderNumber!''}<input type="hidden" value="${order.id?c}" id="orderId">&nbsp;&nbsp;&nbsp;&nbsp; 当前进度：
            <#if order??>
                <#if order.statusId==1>
                    待发货
                <#elseif order.statusId==2>
                    待收货
                <#elseif order.statusId==3>
                    已完成
                <#else>
                    已取消
                </#if>
            </#if>
            &nbsp;&nbsp;&nbsp;&nbsp; 使用积分：<span><#if order??>${order.point!''}</#if></span>
            </dt>
        <#--><dd>
            <#if order??>
                <#if order.statusId==1>
                    请稍等，我们将尽快确认您的订单。
                <#elseif order.statusId==2>
                    亲爱的客户，此订单还未支付，您可以<a href="/order/dopay/${order.id?c}" style="color: #F00;" target="_blank">去支付</a>
                <#elseif order.statusId==3>
                    亲爱的客户，您已成功支付，我们将尽快为您发货。
                <#elseif order.statusId==4>
                    亲爱的客户，已经为您发货，请您&nbsp;<span><a style="color:#F00;" id="orderService">确认收货</a></span>。
                <#elseif order.statusId==5>
                    亲爱的客户，您已消费成功，您可以&nbsp;<span><a style="color:#F00;" href="/user/comment/list">发表评论</a></span>。
                <#elseif order.statusId==6>
                    亲爱的客户，此订单已交易成功。
                <#elseif order.statusId==8>
                    亲爱的客户，此订单支付失败,已取消。
                </#if>
            </#if>
        </dd>-->
      </dl>
      <div class="mymember_green">
         <#if order??>
            <#if order.statusId==1>
                <p class="mysel"><i></i>待发货</p>
                <p><i></i><b></b>待收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==2>
                <p class="mysel"><i></i>待发货</p>
                <p class="mysel"><i></i><b></b>待收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==3>
                <p class="mysel"><i></i>待发货</p>
                <p class="mysel"><i></i><b></b>待收货</p>
                <p class="mysel"><i></i><b></b>完成</p>
            <#else>
                <p class="mysel"><i></i>待发货</p>
                <p class="mysel"><i></i><b></b>已取消</p>
            </#if>
        </#if>
        <div class="clear"></div>
      </div>
    </div><!--mymember_info END-->
    
    <div class="mymember_info mymember_info04">
        <h3>订单详细信息<a id="mymember_order01" href="javascript:myOrderShow('mymember_order01','mymember_ordersum01');">展开</a></h3>
      
        <table id="mymember_ordersum01" class="mymember_sq_tab" style="display: table">
            <tr>
                <th width="100">订单编号</th>
                <td><#if order??>${order.orderNumber!''}</#if></td>
            </tr>
            <tr>
                <th>使用积分</th>
                <td>
                    <font color="#ff1000"><#if order??>${order.point!''}</#if></font>
                </td>
            </tr>
            <tr>
                <th>联系方式</th>
                <td>联系人：<#if order??>${order.shippingName!''}</#if> &nbsp;&nbsp; 联系电话：${order.shippingPhone!''}</td>
            </tr>
            <tr>
                <th>地址</th>
                <td>${order.shippingAddress!''}</td>
            </tr>
        </table>
    </div><!--mymember_info END-->
    
    <div class="mymember_info mymember_info02">
        <table>
            <tr class="mymember_infotab_tit01">
                  <th colspan="2">商品信息</th>
                  <th width="70">收货人</th>
                  <th width="80">兑换积分</th>
                  <th width="80">时间</th>
                  <th width="80">状态</th>
            </tr>  
            <tr>
                <td width="60" class="td001">
                    <a href="/point/goods/detail?id=${order.pointId?c}"><img src="${order.goodsImg!''}" /></a>
                </td>
                <td width="40%" style="text-align: left;">
                    <a href="/point/goods/detail?id=${order.pointId?c}">${order.goodsTitle}</a>
                </td>
                <td>
                    ${order.shippingName!''}
                </td>
                <td>
                    <p>${order.point}</p>
               </td> 
                <td class="td003">
                    <p>${order.createTime!''}</p>
                </td>
                <td>
                    <#if order.statusId?? && order.statusId == 1>     
                         <p>待发货</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 2>
                        <p>待收货</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 3>
                        <p>已完成</p>
                     </#if>
                    <#if order.statusId?? && order.statusId == 4>
                        <p>已取消</p>
                    </#if>
                </td>
            </tr>
            <tr>
                <div class="btn-list">
                    <#if order.statusId==2>
                        <input type="button" id="btnOrderService" onclick="orderFinish(${order.id?c})" value="确认收货" class="btn green">
                    </#if>
                </div>
            </tr>
        </table>
    </div><!--mymember_info END-->

    </div><!--mymember_mainbox END-->

  <div class="myclear"></div>  
</div><!--mymember_main END-->
<div class="myclear"></div>
</div>
<!--mymember END-->

<div class="clear"></div>

    <!--底部footer-->
    <#include "/client/common_footer.ftl" />
</body>
</html>




  











