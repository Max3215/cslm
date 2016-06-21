<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>用户管理</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/setting/cash/list" id="form1">
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
  <span>充值于提现</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar" style="position: static; top: 42px;">
    <div class="l-list">
      <ul class="icon-list">
        <li><a onclick="return ExePostBack('btnVerify','通过审核表示已经给该会员打款，是否继续？');" class="save" href="javascript:__doPostBack('btnVerify','')"><i></i><span>审核</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
        <li><a class="all" href="javascript:__doPostBack('export','')"><span>导出本页</span></a></li>
        <li><a class="all" href="javascript:__doPostBack('exportAll','')"><span>导出全部</span></a></li>
      </ul>
       <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name="shopType" onchange="javascript:setTimeout(__doPostBack('shopType',''), 0)" style="display: none;">
            <option <#if !shopType??>selected="selected"</#if> value="">所有角色</option>
            <option <#if shopType?? && shopType==1>selected="selected"</#if> value="1">超市</option>
            <option <#if shopType?? && shopType==2>selected="selected"</#if> value="2">批发商</option>
            <option <#if shopType?? && shopType==3>selected="selected"</#if> value="3">分销商</option>
            <option <#if shopType?? && shopType==4>selected="selected"</#if> value="4">会员</option>
        </select>
        </div>
      </div>
      <#--
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name="status" onchange="javascript:setTimeout(__doPostBack('status',''), 0)" style="display: none;">
            <option <#if !status??>selected="selected"</#if> value="">所有状态</option>
            <option <#if status?? && status==1>selected="selected"</#if> value="1">未完成</option>
            <option <#if status?? && status==2>selected="selected"</#if> value="2">已完成</option>
        </select>
        </div>
      </div>
      -->
      <div class="menu-list">
        <div class="rule-single-select single-select">
        <select name="type" onchange="javascript:setTimeout(__doPostBack('type',''), 0)" style="display: none;">
            <option <#if !type??>selected="selected"</#if> value="">所有类型</option>
            <option <#if type?? && type==1>selected="selected"</#if> value="1">充值</option>
            <option <#if type?? && type==2>selected="selected"</#if> value="2">提现</option>
        </select>
        </div>
      </div>
      <ul class="icon-list">
            <li>
                <input name="start" type="text" value="<#if startime??>${startime?string('yyyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
            </li>
            <li><a>至</a></li>
            <li>
                <input name="end" type="text" value="<#if endTime??>${endTime?string('yyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
            </li>
        </ul>
    </div>
    <div class="a-list">
       <#--
      <input name="keywords" type="text" class="keyword" value="${keywords!""}">
      -->
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
  <tbody>
  <tr class="odd_bg">
    <th >选择</th>
    <th  width="16%">单号</th>
    <th  width="10%">名称</th>
    <th  width="8%">账号</th>
    <th  width="12%">提交时间</th>
    <th width="8%">金额</th>
    <th width="18%">卡号（仅提现）</th>
    <th width="8%">会员类型</th>
    <th width="9%">类型</th>
    <th width="7%">状态</th>
  </tr>

    <#if cash_page??>
        <#list cash_page.content as cash>
            <tr>
                <td align="center">
                    <span class="checkall" style="vertical-align:middle;">
                        <input id="listChkId" type="checkbox" name="listChkId" value="${cash_index}" >
                    </span>
                    <input type="hidden" name="listId" id="listId" value="${cash.id?c}">
                </td>
                <td align="center">${cash.cashNumber!""}</td>
                <td align="center">${cash.shopTitle!""}</td>
                <td align="center">${cash.username!""}</td>
                <td align="center">${cash.createTime!""}</td>
                <td align="center">${cash.price?string("#.##")}</td>
                <td align="center">${cash.card!""}</td>
                <td align="center"><#switch cash.shopType>
                                        <#case 1>
                                            超市
                                        <#break>
                                        <#case 2>
                                            批发商
                                        <#break>
                                        <#case 3>
                                            分销商
                                        <#break>
                                        <#case 4>
                                            会员
                                        <#break>
                                    </#switch>
                </td>
                <td align="center">
                    <#if cash.type?? && cash.type==1>
                        充值
                    <#elseif cash.type?? && cash.type==2>
                        提现
                    </#if>
                </td>
                <td align="center">
                    <#if cash.status?? && cash.status==1>
                        未完成
                    <#elseif cash.status?? && cash.status==2>
                        已完成
                    </#if>
                </td>
            </tr>
        </#list>
    </#if>
     
</tbody>
</table>

<!--/列表-->

<!--内容底部-->
<#assign PAGE_DATA=cash_page />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>