<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>交易记录</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter//setting/payrecord/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}">
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}">
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
<div class="location" style="position: static; top: 0px;">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>平台账户记录</span>  
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        <li><a class="all" href="javascript:__doPostBack('export','')"><span>导出本页</span></a></li>
        <li><a class="all" href="javascript:__doPostBack('exportAll','')"><span>导出全部</span></a></li>
      </ul>
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name="cont" onchange="javascript:setTimeout(__doPostBack('cont',''), 0)" style="display: none;">
            <option <#if !cont??>selected="selected"</#if> value="">所有交易</option>
            <option <#if cont?? && cont=="销售抽取">selected="selected"</#if> value="销售抽取">销售抽取</option>
            <option <#if cont?? && cont=="批发单抽取">selected="selected"</#if> value="批发单抽取">批发单抽取</option>
            <option <#if cont?? && cont=="充值">selected="selected"</#if> value="充值">手动充值</option>
        </select>
        </div>
      </div>
      <ul class="icon-list">
            <li>
                <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
            </li>
            <li><a>至</a></li>
            <li>
                <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
            </li>
        </ul>
    </div>
    
    <div class="a-list">
        <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th width="8%">选择</th>
    <th align="center">记录时间</th>
    <th align="center" >单号</th>
    <th align="center" >商家</th>
    <th align="center" width="8%">服务费</th>
    <th align="center" width="8%">物流费</th>
    <th align="center" width="8%">第三方使用费</th>
    <th align="center" width="8%">商品总额</th>
    <th width="8%">订单总金额</th>
    <th width="6%">实际入账/支出</th>
    <th>说明</th>
  </tr>

    <#if record_page??>
        <#list record_page.content as re>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${re_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${re.id?c}">
                </td>
                <td align="center">${re.createTime?string('yyyy-MM-dd')}</td>
                <td align="center">${re.orderNumber!''}</td>
                <td align="center">${re.distributorTitle!''}</td>
                <td align="center"><#if re.servicePrice??>${re.servicePrice?string('0.00')}<#else>0.00</#if></td>
                <td align="center"><#if re.postPrice??>${re.postPrice?string('0.00')}<#else>0.00</#if></td>
                <td align="center"><#if re.aliPrice??>${re.aliPrice?string('0.00')}<#else>0.00</#if></td>
                <td align="center"><#if re.totalGoodsPrice??>${re.totalGoodsPrice?string('0.00')}<#else>0.00</#if></td>
                <td align="center"><#if re.provice??>${re.provice?string('0.00')}<#else>0.00</#if></td>
                <td align="center">
                    <#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if>
                </td>
                <td align="center">
                    ${re.cont!''}
                </td>
            </tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=record_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>