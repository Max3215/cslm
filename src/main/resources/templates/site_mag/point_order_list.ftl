<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>待付款订单</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>
<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/pointOrder/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="">
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}

</script>

    <!--导航栏-->
    <div class="location">
        <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
        <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
        <i class="arrow"></i>
        <a><span>订单管理</span></a>
        <i class="arrow"></i>
            <#if statusId??>
                <#if 1==statusId>
                    <span>待发货订单</span>
                <#elseif 2==statusId>
                    <span>待收货订单</span>
                <#elseif 3==statusId>
                    <span>已完成订单</span>
                <#elseif 4==statusId>
                    <span>已取消订单</span>
                </#if>
             <#else>
                    <span>全部订单</span>
            </#if>
    </div>
    <!--/导航栏-->
    <!--工具栏-->
    <div class="toolbar-wrap">
        <div id="floatHead" class="toolbar">
            <div class="l-list">
                <ul class="icon-list">
                    <li>
                        <a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a>
                    </li>
                    <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
                    <li><a class="all" href="javascript:__doPostBack('exportAll','')"><span>导出全部</span></a></li>
                </ul>
                <div class="menu-list">
                    <div class="rule-single-select">
                         <select name="statusId" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                            <option value="" <#if !statusId?? || statusId==0>selected="selected"</#if>>所有订单</option>
                            <option value="1" <#if statusId?? && statusId==1>selected="selected"</#if>>待发货</option>
                            <option value="2" <#if statusId?? && statusId==2>selected="selected"</#if>>待收货</option>
                            <option value="3" <#if statusId?? && statusId==3>selected="selected"</#if>>已完成</option>
                            <option value="4" <#if statusId?? && statusId==4>selected="selected"</#if>>已取消</option>
                         </select>
                    </div>
                </div>
                <ul class="icon-list">
                    <li>
                        <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                    </li>
                    <li><a>至</a></li>
                    <li>
                        <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyy-MM-dd HH:mm')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                    </li>
                </ul>
            </div>
            <div class="r-list">
                <input name="keywords" type="text" class="keyword" value="${keywords!''}">
                <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
            </div>
        </div>
    </div>
    <!--/工具栏-->
    <!--列表-->
    
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
<tbody>
    <tr class="odd_bg">
        <th width="4%">
            选择
        </th>
        <th align="left">
            订单号
        </th>
        <th align="left" width="10%">
            会员账号
        </th>
        <th align="left" >
            兑换商品
        </th>
        <th align="left" width="10%">
            配送地址
        </th>
        <th width="8%">
            订单状态
        </th>
        <th width="10%">
            兑换积分
        </th>
        <th align="left" width="12%">
            兑换时间
        </th>
        <th width="5%">
            操作
        </th>
    </tr>

    <#if order_page??>
        <#list order_page.content as order>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${order_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${order.id?c}">
                </td>
                <td>
                    <a href="/Verwalter/pointOrder/edit?id=${order.id?c}">${order.orderNumber!""}</a></td>
                <td>${order.username!""}</td>
                <td>${order.goodsTitle!""}</td>
                <td>${order.shippingAddress!''}</td>
                <td align="center">
                     <#if order.statusId?? && order.statusId == 1>     
                         <span>待发货</span>
                    </#if>
                    <#if order.statusId?? && order.statusId == 2>
                        <span>待收货</span>
                    </#if>
                    <#if order.statusId?? && order.statusId == 3>
                        <span>已完成</span>
                     </#if>
                    <#if order.statusId?? && order.statusId == 4>
                        <span>已取消</span>
                    </#if>
                </td>
                <td align="center" width="10%"><font color="#C30000">${order.point!''}</font></td>
                <td>${order.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                <td align="center">
                    <a href="/Verwalter/pointOrder/edit?id=${order.id?c}&statusId=${statusId!"0"}">详细</a>
                </td>
            </tr>
        </#list>
    </#if>
</tbody>
</table>
        
<!--/列表-->
<!--内容底部-->
<#assign PAGE_DATA=order_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>
