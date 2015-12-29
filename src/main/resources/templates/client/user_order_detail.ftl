<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

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
<script type="text/javascript">
$(document).ready(function(){
    $("#orderService").click(function(){orderService();});

     $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
    
})

//确认收货
function orderService() {
    var dialog = $.dialog.confirm('操作提示信息：<br />确认已经收到商品？', function () {
    var oid = $("#orderId").val(); 
        
        //发送AJAX请求
        $.ajax({
            type : "post",
            data : {"orderId":oid},
            url : "/user/order/param",
            dataType : "json",
             error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, dialog);
             },
             success: function (data) {
            
                if (data.code == 0) {
                    dialog.close();
                    $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                } else {
                    $.dialog.alert('错误提示：' + data.message, function () { }, dialog);
                }
            }
        });
        return false;
    });
}


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
                    待确认
                <#elseif order.statusId==2>
                    待付款
                <#elseif order.statusId==3>
                    待发货
                <#elseif order.statusId==4>
                    待收货
                <#elseif order.statusId==5>
                    待评价
                <#elseif order.statusId==6>
                    已完成
                <#elseif order.statusId==8>
                    支付失败
                </#if>
            </#if>
            &nbsp;&nbsp;&nbsp;&nbsp; 支付总额：<span>￥<#if order??>${order.totalPrice?string("0.00")}</#if></span></dt>
        <dd>
            <#if order??>
                <#if order.statusId==1>
                    请稍等，我们将尽快确认您的订单。
                <#elseif order.statusId==2>
                    亲爱的客户，此订单还未支付，您可以<a href="/order/dopay/${order.id?c}" style="color: #F00;">去支付</a>
                <#elseif order.statusId==3>
                    亲爱的客户，您以成功支付，我们将尽快为您发货。
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
        </dd>
      </dl>
      <div class="mymember_green">
         <#if order??>
            <#if order.statusId==3>
                <p class="mysel"><i></i>订单付款</p>
                <p><i></i><b></b>发货</p>
                <p><i></i><b></b>确认收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==4>
                <p class="mysel"><i></i>订单付款</p>
                <p class="mysel"><i></i><b></b>发货</p>
                <p><i></i><b></b>确认收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==5>
                <p class="mysel"><i></i>订单付款</p>
                <p class="mysel"><i></i><b></b>发货</p>
                <p class="mysel"><i></i><b></b>确认收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==6>
                <p class="mysel"><i></i>订单付款</p>
                <p class="mysel"><i></i><b></b>发货</p>
                <p class="mysel"><i></i><b></b>确认收货</p>
                <p class="mysel"><i></i><b></b>完成</p>
            </#if>
        </#if>
        <div class="clear"></div>
      </div>
    </div><!--mymember_info END-->
    
    <#--
    <div class="mymember_info mymember_info04">
      <h3>物流追踪<a id="mymember_order02" href="javascript:myOrderShow('mymember_order02','mymember_ordersum02');">展开</a></h3>
      
      <table id="mymember_ordersum02">
        <tr>
          <th width="150">处理时间</th>
          <th>处理信息</th>
          <th width="100">操作人</th>
        </tr>
        <tr>
          <td>2015-02-22 18:22</td>
          <td>您的货物已发出</td>
          <td>系统</td>
        </tr>
        <tr>
          <td>2015-02-22 18:22</td>
          <td>到北京中转站</td>
          <td>客户</td>
        </tr>
        <tr>
          <td>2015-02-22 18:22</td>
          <td>您的申请已取消！</td>
          <td>客户</td>
        </tr>
      </table>
    </div>  
    -->
    <div class="mymember_info mymember_info04">
        <h3>订单详细信息<a id="mymember_order01" href="javascript:myOrderShow('mymember_order01','mymember_ordersum01');">展开</a></h3>
      
        <table id="mymember_ordersum01" class="mymember_sq_tab">
            <tr>
                <th width="100">订单编号</th>
                <td><#if order??>${order.orderNumber!''}</#if></td>
            </tr>
            <tr>
                <th>支付明细</th>
                <td>支付总额：<font color="#ff1000">￥<#if order??>${order.totalPrice?string("0.00")}</#if></font></td>
            </tr>
            <tr>
                <th>联系方式</th>
                <td>联系人：<#if order??>${order.shippingName!''}</#if> &nbsp;&nbsp; 联系电话：${order.shippingPhone!''}</td>
            </tr>
        </table>
    </div><!--mymember_info END-->
    
    <div class="mymember_info mymember_info02">
        <table>
            <tr class="mymember_infotab_tit01">
                  <th colspan="2">订单信息</th>
                  <th width="70">收货人</th>
                  <th width="80">订单金额</th>
                  <th>数量</th>
                  <th width="80">时间</th>
                  <th width="80">状态</th>
            </tr>  
            <#if order?? && order.orderGoodsList??>
            <#list order.orderGoodsList as og>   
            <tr>
                <td width="60" class="td001">
                    <a href="/goods/${og.goodsId?c}"><img src="${og.goodsCoverImageUri}" /></a>
                </td>
                <td>
                    <a href="/goods/${og.goodsId?c}">${og.goodsTitle}</a>
                </td>
                <td>
                    ${order.shippingName!''}
                </td>
                <td>
                    <p>￥${og.price?string("0.00")}</p>
               </td> 
                <td>
                    ${og.quantity!''}
                </td>
                <td class="td003">
                    <p>${order.orderTime!''}</p>
                </td>
                <td>
                    <#if order.statusId?? && order.statusId == 1>     
                         <p>待确认</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 2>
                        <p>待付款</p>
                        <a href="/order/dopay/${order.id?c}">去支付</a>
                    </#if>
                    <#if order.statusId?? && order.statusId == 3>
                        <p>待发货</p>
                     </#if>
                    <#if order.statusId?? && order.statusId == 4>
                        <p>待收货</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 5>
                        <a href="/user/comment/list">去评价</a><br>
                        <a href="/user/return/${order.id?c}?id="${og.id?c}">退货</a>
                    </#if>
                    <#if order.statusId?? && order.statusId == 6>
                        <p>已完成</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 7>
                        <p>已取消</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 8>
                        <p>支付取消（失败）</p>
                    </#if>
                </td>
            </tr>
            </#list>
            </#if>
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




  











