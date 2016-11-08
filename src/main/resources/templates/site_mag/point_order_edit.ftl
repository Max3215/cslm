<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>查看订单信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        $(function () {
            $("#btnPaymentLeft").click(function () { OrderPaymentLeft(); });   // 发货
            $("#btnPayment").click(function () { OrderPayment(); });   // 收货
            $("#btnEditRemark").click(function () { EditOrderRemark(); });   // 修改备注
            $("#btnCancel").click(function () { OrderCancel(); });   // 取消

        });

        //确认收货
        function OrderPayment() {
            var dialog = $.dialog.confirm('该步骤将确认收货，确认要继续吗？', function () {
                var orderId = $("#orderId").val();
                var postData = { "orderId": orderId, "type": "orderReceive" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/pointOrder/param/edit");
                return false;
            });
        }

        
        // 确认发货
        function OrderPaymentLeft() {
            var dialog = $.dialog.confirm('操作提示信息：<br />1、此订单为积分兑换订单。提交后自动进入待发货状态；<br />2、确认后订单将进入待收货状态，确认要继续吗？', function () {
                var orderId = $("#orderId").val();
                var postData = { "orderId": orderId, "type": "orderSeed" };
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/pointOrder/param/edit");
                return false;
            });
        }
        
        

        //取消订单
        function OrderCancel() {
            var dialog = $.dialog({
                title: '取消订单',
                content: '操作提示信息：<br />1、取消后会员使用积分会返回；<br />2、取消后可进行删除操作;',
                min: false,
                max: false,
                lock: true,
                icon: 'confirm.gif',
                button: [{
                    name: '直接取消',
                    callback: function () {
                        var orderId = $("#orderId").val();
                        var postData = { "orderId": orderId, "type": "orderCancel" };
                        //发送AJAX请求
                        sendAjaxUrl(dialog, postData, "/Verwalter/pointOrder/param/edit");
                        return false;
                    },
                    focus: true
                }, {
                    name: '关闭'
                }]
            });

        }
        //修改订单备注
        function EditOrderRemark() {
            var dialog = $.dialog({
                title: '订单备注',
                content: '<textarea id="orderRemark" name="txtOrderRemark" rows="2" cols="20" class="input">${order.remarkInfo!''}</textarea>',
                min: false,
                max: false,
                lock: true,
                ok: function () {
                    var remark = $("#orderRemark", parent.document).val();
                    if (remark == "") {
                        $.dialog.alert('对不起，请输入订单备注内容！', function () { }, dialog);
                        return false;
                    }
                    var orderId = $("#orderId").val();
                    var postData = { "orderId": orderId, "type": "editMark", "data": remark };
                    //发送AJAX请求
                    sendAjaxUrl(dialog, postData, "/Verwalter/pointOrder/param/edit");
                    return false;
                },
                cancel: true
            });
        }

        //=================================工具类的JS函数====================================
        //检查是否货币格式
        function checkIsMoney(val) {
            var regtxt = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/;
            if (!regtxt.test(val)) {
                return false;
            }
            return true;
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
</head>
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/order/save" id="form1">
    <!--导航栏-->
    <div class="location" style="position: fixed; top: 0px;">
        <a href="/Verwalter/pointOrder/list" class="back"><i></i><span>返回列表页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a href="/Verwalter/pointOrder/list"><span>订单管理</span></a>
        <i class="arrow"></i><span>订单详细</span>
    </div>
    <div class="line10">
    </div>
    <!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab" style="position: static; top: 52px;">
            <div class="content-tab-ul-wrap">
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">订单详细信息</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="tab-content">
        <dl>
            <dd style="margin-left: 50px; text-align: center;">
                <div class="order-flow" style="width: 850px">
                    <#if order.statusId == 1>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待发货</p>
                            </span>
                        </div>
                        <div class="order-flow-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待收货</p>
                            </span>
                        </div>
                        <div class="order-flow-right-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">未完成</p>
                                <p></p>
                            </span>
                        </div>
                    
                    <#elseif order.statusId == 2>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待发货</p>
                            </span>
                        </div>
                        <div class="order-flow-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待收货</p>
                            </span>
                        </div>
                        <div class="order-flow-right-wait">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">未完成</p>
                                <p></p>
                            </span>
                        </div>
                    <#elseif order.statusId == 3>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待发货</p>
                            </span>
                        </div>
                        <div class="order-flow-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">待收货</p>
                            </span>
                        </div>
                        <div class="order-flow-right-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">完成</p>
                                <p></p>
                            </span>
                        </div>
                    <#elseif order.statusId == 4>
                        <div class="order-flow-left">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">订单已生成</p>
                            </span>
                        </div>
                        <div class="order-flow-right-arrive">
                            <a class="order-flow-input"></a>
                            <span>
                                <p class="name">已取消</p>
                            </span>
                        </div>
                    </#if>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>订单号</dt>
            <dd>
                <input type="hidden" value="${order.id?c}" name="orderId" id="orderId">
                <span id="spanOrderNumber">${order.orderNumber!""}</span>
            </dd>
        </dl>
        
        <dl>
            <dt>商品信息</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <thead>
                        <tr>
                            <th width="12%">
                                商品ID
                            </th>
                            <th>
                                商品名称
                            </th>
                            <th width="12%">
                               副标题
                            </th>
                            <th width="10%">
                                编码
                            </th>
                            <th width="12%">
                                积分
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                            <tr class="td_c">
                                <td>${order.pointId!""}</td>
                                <td style="text-align: left; white-space: normal;">
                                    ${order.goodsTitle!""} 
                                </td>
                                <td>${order.subTitle!''}</td>
                                <td>${order.code!""}</td>
                                <td>${order.point!''}</td>
                            </tr>
                    </tbody> 
                </table> 
            </dd> 
        </dl>
        
        <dl>
            <dt>兑换信息</dt>
            <dd>
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody>
                    <tr>
                        <th>
                            兑换积分
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanExpressFeeValue">${order.point!''}</span> 
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th width="20%">
                            收件人
                        </th>
                        <td>
                            <div class="position">
                                <span id="spanAcceptName">${order.shippingName!""}</span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            发货地址
                        </th>
                        <td>
                            <span id="spanArea"></span> 
                            <span id="spanAddress">${order.shippingAddress!""}</span>
                        </td>
                    </tr>
                    <#--
                    <tr>
                        <th>
                            邮政编码
                        </th>
                        <td><span id="spanPostCode">${order.postalCode!""}</span></td>
                    </tr>
                    -->
                    <tr>
                        <th>
                            电话
                        </th>
                        <td><span id="spanMobile">${order.shippingPhone!""}</span></td>
                    </tr>
                </tbody></table>
            </dd>
        </dl>
        
        <dl>
            <dt>备注</dt>
            <dd>
                
                <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                    <tbody>
                    <tr>
                        <th  width="20%">
                            用户留言
                        </th>
                        <td>${order.userRemarke!""}</td>
                    </tr>
                    <tr>
                        <th valign="top">
                            订单备注
                        </th>
                        <td>
                            <div class="position">
                                <div>${order.siteRemarke!""}</div>
                                <input name="btnEditRemark" type="button" id="btnEditRemark" class="ibtn" value="修改" style="margin-top: -3px;">
                            </div>
                        </td>
                    </tr>
                </tbody></table>
            </dd>
        </dl>
        
    </div>
    <!--/内容-->
    <!--工具栏-->
    <div class="page-footer">
        <div class="btn-list">
            <#if order.statusId==1>
                <input type="button" id="btnPaymentLeft" value="点击发货" class="btn">
                <input type="button" id="btnCancel" value="取消订单" class="btn green">
            <#elseif order.statusId==2>
                <input type="button" id="btnPayment" value="确认收货" class="btn">
            </#if>
            <input type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
        </div>
        <div class="clear">
        </div>
    </div>
    <!--/工具栏-->
    </form>


</body></html>
