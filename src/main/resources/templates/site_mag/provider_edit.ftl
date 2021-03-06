<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑供应商</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script src="/mag/js/jquery.cityselect.js"></script>
<script type="text/javascript">
$(function () {
    $("#btnEditRemark").click(function () { addVirtualMoney(); }); 
     $("#btnEditMoney").click(function () { delVirtualMoney(); }); 
    //初始化表单验证
    $("#form1").initValidform();
    
     $("#address").citySelect({
        nodata:"none",
        <#if provider?? && provider.province??>prov: "${provider.province!''}",</#if>
        <#if provider?? && provider.city??>city: "${provider.city!''}",</#if>
        <#if provider?? && provider.disctrict??>dist: "${provider.disctrict!''}",</#if>
        required:false
    });
});

// 充值
    function addVirtualMoney() {
        var dialog = $.dialog({
            title: '输入充值记录：',
            content: '<input id="orderRemark" name="txtOrderRemark"  class="input"></input>',
            min: false,
            max: false,
            lock: true,
            ok: function () {
                var mon = $("#orderRemark", parent.document).val();
                var num = /^\d+(\.\d{2})?$/;
                if (mon == "" || !num.test(mon)) {
                    $.dialog.alert('对不起，输入的金额有误！', function () { }, dialog);
                    return false;
                }
                var id = $.trim($("#id").val());
                var postData = { "id": id,  "data": mon ,"type":"add"};
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/provider/virtualMoney/edit");
                return false;
            },
            cancel: true
        });
    }
    
    // 扣款
 function delVirtualMoney() {
        var dialog = $.dialog({
            title: '输入扣款金额：',
            content: '<input id="virtualMoney" name="txtOrderRemark"  class="input"></input>',
            min: false,
            max: false,
            lock: true,
            ok: function () {
                var mon = $("#virtualMoney", parent.document).val();
                var num = /^\d+(\.\d{2})?$/;
                if (mon == "" || !num.test(mon)) {
                    $.dialog.alert('对不起，输入的金额有误！', function () { }, dialog);
                    return false;
                }
                var id = $.trim($("#id").val());
                var postData = { "id": id,  "data": mon ,"type":"del"};
                //发送AJAX请求
                sendAjaxUrl(dialog, postData, "/Verwalter/provider/virtualMoney/edit");
                return false;
            },
            cancel: true
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
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/provider/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="id" value="<#if provider??>${provider.id?c!""}</#if>" id="id">
</div>
<!--导航栏-->
<div class="location" style="position: static; top: 0px;">
  <a href="/Verwalter/provider/list"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>批发商管理</span>
  <i class="arrow"></i>
  <span>编辑批发商</span>
</div>
<div class="line10"></div>
<!--导航栏-->
<!--内容-->
<div class="content-tab-wrap">
    <div id="floatHead" class="content-tab">
        <div class="content-tab-ul-wrap" >
            <ul>
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑商家</a></li>
                <li><a href="javascript:;" onclick="tabs(this);" class="">交易记录</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="tab-content" style="display: block;">
    <dl>
        <dt>批发商名称</dt>
        <dd>
            <input name="title" type="text" value="<#if provider??>${provider.title!""}</#if>" class="input normal" <#if provider??><#else>ajaxurl="/Verwalter/provider/check/title"</#if> datatype="s" sucmsg=" ">
            <span class="Validform_checktip">*批发商名称</span>
        </dd>
    </dl>
    <dl>
        <dt>登录名</dt>
        <dd>
            <input name="username" type="text" value="<#if provider??>${provider.username!""}</#if>" class="input normal" <#if provider??><#else>ajaxurl="/Verwalter/provider/check/username"</#if> datatype="s6-20" sucmsg=" ">
            <span class="Validform_checktip">*登录账号</span>
        </dd>
    </dl>
    <dl>
        <dt>登录密码</dt>
        <dd>
            <input name="password" type="password" value="<#if provider??>${provider.password!""}</#if>" class="input normal"  datatype="*6-20" sucmsg=" ">
            <span class="Validform_checktip">*</span>
        </dd>
    </dl>
    <dl>
        <dt>重复密码</dt>
        <dd>
            <input type="password" value="<#if provider??>${provider.password!""}</#if>" class="input normal" recheck="password" datatype="*" sucmsg=" ">
            <span class="Validform_checktip">*</span>
        </dd>
    </dl>
    <dl>
        <dt>账号类别</dt>
        <dd>
            <div class="rule-multi-radio multi-radio">
                <span id="rblStatus" style="display: none;">
                    <input type="radio" name="type" value="1" <#if !provider?? || provider?? && provider.type?? && provider.type==1>checked="checked"</#if> ><label>批发商</label>
                    <input type="radio" name="type" value="2" <#if provider?? && provider.type?? && provider.type==2>checked="checked"</#if>><label>分销商</label>
                </span>
            </div>
        </dd>
    </dl>
    <dl>
        <dt>手机号</dt>
        <dd>
            <input name="mobile" type="text" value="<#if provider??>${provider.mobile!""}</#if>" class="input normal"  datatype="m" sucmsg=" ">
            <span class="Validform_checktip">*</span>
        </dd>
    </dl>
    <dl>
        <dt>虚拟账号</dt>
        <dd>
            <input name="virtualAccount" type="text" value="<#if provider??>${provider.virtualAccount!""}</#if>" class="input normal" <#if provider??><#else>ajaxurl="/Verwalter/provider/check/virtualAccount"</#if>  datatype="*6-20" sucmsg=" ">
            <span class="Validform_checktip">*与平台进行交易结算</span>
        </dd>
    </dl>
    <dl>
        <dt>账号余额</dt>
        <dd>
            <input name="virtualMoney" type="text" value="<#if provider?? && provider.virtualMoney??>${provider.virtualMoney?string('0.00')}<#else>0</#if>" <#if provider?? && provider.virtualMoney??>readonly="readonly"</#if> class="input normal"   sucmsg=" ">
            <span class="Validform_checktip"></span>
            <#if provider??>
            &emsp;&emsp;&emsp;<a id="btnEditRemark" style="color:red">充值</a>
            &emsp;&emsp;&emsp;<a id="btnEditMoney" style="color:red">扣款</a>
            </#if>
        </dd>
    </dl>
    <dl>
    <dt>支付密码</dt>
        <dd>
            <input name="payPassword" type="text" value="<#if provider?? && provider.payPassword??>${provider.payPassword!''}</#if>" class="input normal" > 
            <span class="Validform_checktip"></span>
        </dd>
    </dl>
    <dl>
    <dt>平台服务费比例</dt>
        <dd>
            <input name="serviceRation" type="text" value="<#if provider?? && provider.serviceRation??>${provider.serviceRation?string("0.00")}<#else>0.01</#if>" class="input normal" sucmsg=" "> 
            <span class="Validform_checktip">*商家每次交易时平台抽取的比例</span>
        </dd>
    </dl>
    <dl>
        <dt>第三方使用费比例</dt>
        <dd>
            <input name="aliRation" type="text" value="<#if provider?? && provider.aliRation??>${provider.aliRation?string("0.00")}<#else>0</#if>" class="input normal" sucmsg=" "> 
            <span class="Validform_checktip">*</span>
        </dd>
      </dl>
    <dl>
        <dt>商家邮费收取比例</dt>
        <dd>
            <input name="postPrice" type="text" value="<#if provider?? && provider.postPrice??>${provider.postPrice?string("0.00")}<#else>0.01</#if>" class="input normal" sucmsg=" "> 
            <span class="Validform_checktip">*商家每次交易时根据商品总额按比例计算邮费</span>
        </dd>
      </dl>
    <dl>
        <dt>城市</dt>
        <dd>
            <div id="address">
               <select name="province" class="prov" style="width: 100px;" datatype="*"></select>
               <select name="city" class="city" style="width: 100px;" datatype="*"></select>
               <select name="disctrict" class="dist" style="width: 100px;" datatype="*0-10"></select>
            </div>
        </dd>
  </dl>
    <dl>
        <dt>详细地址</dt>
        <dd>
            <input name="address" type="text" value="<#if provider??>${provider.address!""}</#if>" class="input normal"  datatype="*" sucmsg=" ">
            <span class="Validform_checktip">*</span>
        </dd>
    </dl>
    <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isEnable" value="1" <#if !provider?? || !provider.isEnable?? || provider?? && provider.isEnable?? && provider.isEnable>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isEnable" value="0" <#if provider?? && provider.isEnable?? && !provider.isEnable>checked="checked"</#if>>
            <label>否</label>
        </span>
      </div>
      <span class="Validform_checktip">*不启用则改账号不能登录</span>
    </dd>
  </dl>
    <dl>
        <dt>排序数字</dt>
        <dd>
            <input name="sortId" type="text" value="<#if provider??>${provider.sortId!""}<#else>99</#if>" class="input txt100" datatype="n" sucmsg=" ">
            <span class="Validform_checktip">*数字，越小越向前</span>
        </dd>
    </dl>
</div>

<div class="tab-content" style="display: none;">
    <dl>
        <dt>交易记录</dt>
        <dd>
            <table border="0" cellspacing="0" cellpadding="0" class="border-table" width="98%">
                <thead>
                    <tr>
                        <th width="20%">订单编号</th>
                        <th>交易时间</th>
                        <th>服务费</th>
                        <th>物流费</th>
                        <th>商品总额</th>
                        <th>订单总金额</th>
                        <th>实际金额</th>
                        <th>说明</th>
                    </tr>
                </thead>
                <tbody>
                    <#if pay_tecord_list??>
                        <#list pay_tecord_list as re>
                            <tr class="td_c">
                                 <td >${re.orderNumber!''}</td>
                                  <td class="td003">
                                    <p>${re.createTime?string('yyyy-MM-dd')}</p>
                                  </td>
                                  <td><#if re.servicePrice??>${re.servicePrice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.postPrice??>${re.postPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.totalGoodsPrice??>${re.totalGoodsPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.provice??>${re.provice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td>${re.cont}</td> 
                            </tr>
                        </#list>
                    </#if>
                </tbody> 
            </table> 
        </dd> 
    </dl>

</div>
    
    
<!--/内容-->
<!--工具栏-->
<div class="page-footer">
    <div class="btn-list">
        <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
        <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
    </div>
    <div class="clear">
    </div>
</div>
<!--/工具栏-->
</form>
</body>
</html>