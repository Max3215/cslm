<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
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
<!--  后台文件  -->
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
})
</script>
<script type="text/javascript">
    $(function () {
        $("#btnPaymentLeft").click(function () { OrderPaymentLeft(); });   //确认发货
        $("#btnService").click(function () { OrderService(); });   //确认收货
        $("#btnPrint").click(function () { OrderPrint(); });       //打印订单
    });
    
    //确认发货
    function OrderPaymentLeft() {
        var dialog = $.dialog.confirm('操作提示信息：<br />商品已经进行派送可使用，确定要继续吗？', function () {
            var orderNumber = $.trim($("#spanOrderNumber").text());
            var postData = { "orderNumber": orderNumber, "type": "orderPayLeft" };
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/distributor/inOrder/param/edit");
            return false;
        });
    }
    //确认收货
    function OrderService() {
        var dialog = $.dialog.confirm('操作提示信息：<br />确认已经收到商品？', function () {
            var orderNumber = $.trim($("#spanOrderNumber").text());
            var postData = { "orderNumber": orderNumber, "type": "orderService" };
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/distributor/inOrder/param/edit");
            return false;
        });
    } 
    //打印订单
    function OrderPrint() {
        var dialog = $.dialog({
            title: '打印订单',
            content: 'url:/Verwalter/order/dialog/print?orderNumber=' + $.trim($("#spanOrderNumber").text()),
            min: false,
            max: false,
            lock: true,
            width: 850//,
            // height: 500
        });
    }
    
    //发送AJAX请求
        function sendAjaxUrl(winObj, postData, sendUrl) {
            $.ajax({
                type: "post",
                url: sendUrl,
                data: postData,
                dataType: "json",
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
                },
                success: function (data) {
                
                    if (data.code == 0) {
                        winObj.close();
                        $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                    } else {
                        $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                    }
                }
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

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

    <!-- 左侧 -->
  <#include "/client/common_distributor_menu.ftl" />
  
  <div class="mymember_mainbox">
    <div class="mymember_info mymember_info04">
      <h3>订单详细</h3>
      <dl>
        <dt>订单编号：<span id="spanOrderNumber">${order.orderNumber!''}</span>&nbsp;&nbsp;&nbsp;&nbsp; 当前进度：
            <#if order??>
                <#if order.statusId==3>
                    待发货
                <#elseif order.statusId==4>
                    待收货
                <#elseif order.statusId==6>
                    已完成
                </#if>
            </#if>
            &nbsp;&nbsp;&nbsp;&nbsp; 支付总额：<span>￥<#if order??>${order.totalPrice?string("0.00")}</#if></span></dt>
      </dl>
      <div class="mymember_green">
         <#if order??>
            <#if order.statusId==3>
                <p class="mysel"><i></i>待发货 </p>
                <p><i></i><b></b>确认收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==4>
                <p class="mysel"><i></i><b></b>待发货</p>
                <p class="mysel"><i></i><b></b>确认收货</p>
                <p><i></i><b></b>完成</p>
            <#elseif order.statusId==6>
                <p class="mysel"><i></i><b></b>待发货</p>
                <p class="mysel"><i></i><b></b>确认收货</p>
                <p class="mysel"><i></i><b></b>完成</p>
            </#if>
        </#if>
        <div class="clear"></div>
      </div>
    </div><!--mymember_info END-->
    
    <div class="mymember_info mymember_info04">
        <h3>订单详细信息<a id="mymember_order01" href="javascript:myOrderShow('mymember_order01','mymember_ordersum01');">展开</a></h3>
      
        <table id="mymember_ordersum01" class="mymember_sq_tab">
            <tr>
                <th width="100">订单编号</th>
                <td><#if order??>${order.orderNumber!''}</#if></td>
            </tr>
            <tr>
                <th>支付明细</th>
                <td>
                    支付总额：<font color="#ff1000">￥<#if order??>${order.totalPrice?string("0.00")}</#if></font>
                    &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
                运费：<font color="#ff1000">￥<#if order.postPrice??>${order.postPrice?string("0.00")}<#else>0</#if></font>      
               </td>
            </tr>
            <tr>
                <th>联系方式</th>
                <td>批发超市：<#if order??>${order.shippingName!''}</#if> &nbsp;&nbsp; 联系电话：${order.shippingPhone!''}</td>
            </tr>
            <tr>
                <th>收货地址</th>
                <td><#if order??>${order.shippingAddress!''}</#if></td>
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
                    <a href=""><img src="${og.goodsCoverImageUri}" /></a>
                </td>
                <td>
                    <a href="">${og.goodsTitle}</a>
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
                    <#if order.statusId?? && order.statusId == 3>     
                         <p>待发货</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 4>
                        <p>待收货</p>
                    </#if>
                    <#if order.statusId?? && order.statusId == 6>
                        <p>已完成</p>
                    </#if>
                </td>
            </tr>
            </#list>
            </#if>
            <tr>
                <div class="btn-list">
                    <#if order.statusId==3>
                        <input type="button" id="btnPaymentLeft" value="确认发货" class="btn">
                    <#elseif order.statusId==4>
                        <input type="button" id="btnService" value="确认收货" class="btn green">
                    </#if>
                    <input type="button" id="btnPrint" value="打印订单" class="btn green">
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




  











