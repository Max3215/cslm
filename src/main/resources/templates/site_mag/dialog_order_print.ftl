<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml"><head>
<title>打印订单窗口</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js?skin=idialog"></script>
<script type="text/javascript">
    //窗口API
    var api = frameElement.api, W = api.opener;
    api.button({
        name: '确认打印',
        focus: true,
        callback: function () {
            printWin();
        }
    }, {
        name: '取消'
    });
    //打印方法
    function printWin() {
        var oWin = window.open("", "_blank");
        oWin.document.write(document.getElementById("content").innerHTML);
        oWin.focus();
        oWin.document.close();
        oWin.print()
        oWin.close()
    }
</script>
</head>
<body style="margin: 0;">
    <form name="form1" method="post" action="dialog_print.aspx?order_no=B15041121411832" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="">
</div>

<div>

    <input type="hidden" name="__VIEWSTATEGENERATOR" id="__VIEWSTATEGENERATOR" value="86EF5C69">
</div>
    <div id="content">
        <table width="800" border="0" align="center" cellpadding="3" cellspacing="0" style="font-size: 12px; font-family: '微软雅黑'; background: #fff;">
            <tbody>
            <tr>
                <td width="346" height="50" style="font-size: 20px;">
                    商品订单</td>
                <td width="216">操&nbsp;作&nbsp;人：管理员</td>
                <td width="220">
                    打印时间：${now?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            <tr>
                <td width="346">
                   订单号：${order.orderNumber!''}</td>
                <td width="216">
                    状&nbsp;&nbsp; 态：<#if order.statusId??>
                        <#if 1==order.statusId>
                           待确认订单
                        <#elseif 2==order.statusId>
                            待付款订单
                        <#elseif 3==order.statusId>
                            待发货订单
                        <#elseif 4==order.statusId>
                            待收货订单
                        <#elseif 5==order.statusId>
                            待评价订单
                        <#elseif 6==order.statusId>
                            已完成订单
                        <#elseif 7==order.statusId>
                            已取消订单
                        </#if>
                    </#if></td>
                <td width="220">
                    下单时间：${order.orderTime?string("yyyy-MM-dd HH:mm:ss")}</td>
            </tr>
            <tr>
                <td colspan="3" style="padding: 10px 0; border-top: 1px solid #000;">
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="5" style="font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody><tr>
                            <td width="55%" align="left" style="background: #ccc;">商品名称</td>
                            <td width="11%" align="left" style="background: #ccc;">商品编码</td>
                            <td width="15%" align="center" style="background: #ccc;">成交价</td>
                            <td width="10%" align="left" style="background: #ccc;">数量</td>
                            <td width="10%" align="left" style="background: #ccc;">金额合计</td>
                        </tr>
                    <#if order??>
                        <#list order.orderGoodsList as og>
                            <tr>
                                <td  width="55%">
                                    ${og.goodsTitle!''} (${og.specName!''}) 
                                </td>
                                <td>${og.goodsCode!''}</td>
                                <td align="center">${og.price?string("0.00")}<#if og.unit??>/${og.unit!''}</#if></td>
                                <td>${og.quantity!'0'}</td>
                                <td>${(og.price*og.quantity)?string("0.00")}</td>
                            </tr>
                        </#list>
                    </#if>
                    </tbody>
                </table>
                        
                </td>
            </tr>
            <tr>
                <td colspan="3" style="border-top: 1px solid #000;">
                    <table width="100%" border="0" cellspacing="0" cellpadding="5" style="margin: 5px auto; font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody>
                        <#if order.typeId ==2>
                        <tr>
                            <td width="44%">售出店铺： ${order.shopTitle!''}
                            </td>
                            <td width="56%">店铺地址：${order.deliveryPerson!''}<br></td>
                        </tr>
                        </#if>
                        <tr>
                            <td width="44%">会员账户：
                                ${order.username}（注册手机：${order.mobile!''}）
                            </td>
                            </td>
                            <td width="56%">姓名：${order.shippingName!''}<br>                          
                            </td>
                        </tr>
                        
                        <tr>
                            <td valign="top">支付方式：${order.payTypeTitle!''}</td>
                            <#if order?? && order.deliveryMethod?? && order.deliveryMethod==1>
                            <td>门店自提：${order.shipAddress!''}</td>
                            <#else>
                            <td>送货地址：
                                ${order.shippingAddress!''}<br>
                            </td>
                            </#if>
                        </tr>
                        <tr>
                            <td valign="top">用户留言：${order.userRemarkInfo!''}</td>
                            <td valign="top">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：${order.shippingPhone!''}</td>
                        </tr>
                        <tr>
                            <td valign="top">订单备注：${order.remarkInfo!''}</td>
                        </tr>
                    </tbody></table>
                    <table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" style="border-top: 1px solid #000; font-size: 12px; font-family: '微软雅黑'; background: #fff;">
                        <tbody>
                            <tr>
                            <td width="20%"></td>
                            <td align="right">商品金额：￥${order.totalGoodsPrice?string("0.00")}
                                 <#if order.postPrice??>  + 配送费：￥${order.postPrice?string("0.00")}</#if>
                              <#--      + 支付手续费：￥${order.payTypeFee?string("0.00")}
                          -->   
                                = 订单总额：${order.totalPrice?string("0.00")}
                                		<#--（使用积分： <#if order.pointUse??>${order.pointUse!'0'}<#else>0</#if>） -->
                                </td>
                            </tr>
                    </tbody></table>
                </td>
            </tr>
        </tbody></table>
    </div>
    </form>


</body></html>