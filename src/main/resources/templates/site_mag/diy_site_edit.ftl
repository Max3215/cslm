<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑超市信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script src="/mag/js/jquery.cityselect.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
$(function () {
      $("#btnEditRemark").click(function () { addVirtualMoney(); }); 
      $("#btnEditMoney").click(function () { delVirtualMoney(); }); 

    //初始化表单验证
    $("#form1").initValidform();
    
    $("#address").citySelect({
        nodata:"none",
        <#if diy_site?? && diy_site.province??>prov: "${diy_site.province!''}",</#if>
        <#if diy_site?? && diy_site.city??>city: "${diy_site.city!''}",</#if>
        <#if diy_site?? && diy_site.disctrict??>dist: "${diy_site.disctrict!''}",</#if>
        required:false
    });
    
    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $(".thumb_ImgUrl_show").hide();
    }
    else {
        $(".thumb_ImgUrl_show").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $(".thumb_ImgUrl_show").show();
    }
    
    $(".upload-show360").each(function () {
        $(this).InitSWFUpload_show360({ 
            btntext: "批量上传", 
            btnwidth: 66, 
            single: false, 
            water: true, 
            thumbnail: true, 
            filesize: "5120", 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf", 
            filetypes: "*.jpg;*.jpge;*.png;*.gif;" 
        });
    });
});

// 充值
function addVirtualMoney() {
    var dialog = $.dialog({
        title: '输入充值金额：',
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
            var diysiteId = $.trim($("#diysiteId").val());
            var postData = { "diysiteId": diysiteId,  "data": mon ,"type":"add"};
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/Verwalter/order/diy_site/edit");
            return false;
        },
        cancel: true
    });
}

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
            var diysiteId = $.trim($("#diysiteId").val());
            var postData = { "diysiteId": diysiteId,  "data": mon ,"type":"del"};
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/Verwalter/order/diy_site/edit");
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
<form name="form1" method="post" action="/Verwalter/order/setting/diysite/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="diySiteId" type="text" value='<#if diy_site??>${diy_site.id?c}</#if>' style="display:none" id="diysiteId">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/order/setting/diysite/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/order/setting/diysite/list"><span>超市</span></a>
  <i class="arrow"></i>
  <span>编辑超市</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a></li>
        <li><a href="javascript:;" onclick="tabs(this);" class="">交易记录</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>超市名称</dt>
    <dd>
        <input type="hidden" name="id"  value="<#if diy_site??>${diy_site.id?c}</#if>">
        <input name="title" type="text" value="<#if diy_site??>${diy_site.title!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> 
        <span class="Validform_checktip">*分销商名称</span>
    </dd>
  </dl>
  <dl>
    <dt>登录名</dt>
    <dd>
        <input name="username" type="text" value="<#if diy_site??>${diy_site.username!""}</#if>" class="input normal" ajaxurl="/Verwalter/order/setting/diysite/check<#if diy_site??>?id=${diy_site.id?c}</#if>"  datatype="*6-100" sucmsg=" "> 
        <span class="Validform_checktip">*登录账号</span>
    </dd>
  </dl>
  <dl>
    <dt>登录密码</dt>
    <dd>
        <input name="password" type="password" value="<#if diy_site??>${diy_site.password!""}</#if>" class="input normal" datatype="*6-20" sucmsg=" "> 
        <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  <dl>
    <dt>重复密码</dt>
    <dd>
        <input type="password" value="<#if diy_site??>${diy_site.password!""}</#if>" class="input normal" datatype="*" recheck="password" sucmsg=" "> 
        <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  <dl>
    <dt>手机号</dt>
    <dd>
        <input name="mobile" type="text" value="<#if diy_site??>${diy_site.mobile!""}</#if>" class="input normal" datatype="m" sucmsg=" "> 
        <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <dl>
    <dt>超市虚拟账号</dt>
    <dd>
        <input name="virtualAccount" type="text" value="<#if diy_site??>${diy_site.virtualAccount!""}</#if>" class="input normal"  sucmsg=" "> 
        <span class="Validform_checktip">*虚拟账号</span>
    </dd>
  </dl>
  <dl>
    <dt>虚拟账号余额</dt>
    <dd>
        <input name="virtualMoney" type="text" value="<#if diy_site?? && diy_site.virtualMoney??>${diy_site.virtualMoney?string("0.00")}<#else>0</#if>" <#if diy_site?? && diy_site.virtualMoney??>readonly="readonly"</#if> class="input normal" sucmsg=" "> 
        <span class="Validform_checktip">*账号余额</span>
        <#if diy_site??>
        &emsp;&emsp;&emsp;<a id="btnEditRemark" style="color:red">充值</a>
        &emsp;&emsp;&emsp;<a id="btnEditMoney" style="color:red">扣款</a>
        </#if>
    </dd>
  </dl>
  <dl>
    <dt>支付密码</dt>
    <dd>
        <input name="payPassword" type="password" value="<#if diy_site?? && diy_site.payPassword??>${diy_site.payPassword!''}</#if>" class="input normal" > 
        <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>店面图片</dt>
    <dd>
        <input id="txtImgUrl" name="imageUri" type="text" datatype="*" value="<#if diy_site?? && diy_site.imageUri??>${diy_site.imageUri!""}</#if>" class="input normal upload-path">
        <div class="upload-box upload-img"></div>
        <div class="photo-list thumb_ImgUrl_show">
            <ul>
                <li>
                    <div class="img-box1"></div>
                </li>
            </ul>
        </div>
        <span class="Validform_checktip"></span>
    </dd>
  </dl>
  -->
  <dl>
    <dt>营业执照</dt>
    <dd>
        <input id="txtImgUrl" name="fileUri" type="text" datatype="*" value="<#if diy_site?? && diy_site.fileUri??>${diy_site.fileUri!""}</#if>" class="input normal upload-path">
        <div class="upload-box upload-img"></div>
        <div class="photo-list thumb_ImgUrl_show">
            <ul>
                <li>
                    <div class="img-box1"></div>
                </li>
            </ul>
        </div>
        <span class="Validform_checktip"></span>
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
    <dt>超市详细位置</dt>
    <dd>
      <input name="address" type="text" value="<#if diy_site??>${diy_site.address!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip">该信息可以帮助用户选择最合适的同盟店</span>
    </dd>
  </dl>
  
  <dl>
        <dt>经度</dt>
        <dd>
          <input name="longitude" type="text" value="<#if diy_site?? && diy_site.longitude??>${diy_site.longitude?string("#.######")}</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="" sucmsg=" ">
          <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
          <span class="Validform_checktip"></span>
        </dd>
          </dl>
          
          <dl>
        <dt>纬度</dt>
        <dd>
          <input name="latitude" type="text" value="<#if diy_site?? && diy_site.latitude??>${diy_site.latitude?string("#.######")}</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="" sucmsg=" ">
          <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
          <span class="Validform_checktip"></span>
        </dd>
  </dl>
  <#--
  <dl>
    <dt>付款方式</dt>
    <dd>
      <input name="payType" type="text" value="<#if diy_site??>${diy_site.payType!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip">现金/刷卡</span>
    </dd>
  </dl>
  
  <dl>
    <dt>营业时间</dt>
    <dd>
      <input name="openTimeSpan" type="text" value="<#if diy_site??>${diy_site.openTimeSpan!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  -->
  <dl>
    <dt>客服电话</dt>
    <dd>
      <input name="serviceTele" type="text" value="<#if diy_site??>${diy_site.serviceTele!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  <#--
  <dl>
    <dt>投诉电话</dt>
    <dd>
      <input name="complainTele" type="text" value="<#if diy_site??>${diy_site.complainTele!""}</#if>" class="input normal" datatype="*" errormsg="" sucmsg=" ">
      <span class="Validform_checktip"></span>
    </dd>
  </dl>
  -->
  <dl>
    <dt>平台服务比例</dt>
    <dd>
        <input name="serviceRation" type="text" value="<#if diy_site?? && diy_site.serviceRation??>${diy_site.serviceRation?string("0.00")}<#else>0.01</#if>" class="input normal" sucmsg=" "> 
        <span class="Validform_checktip">*商家每次交易时平台抽取的比例</span>
    </dd>
  </dl>
  <dl>
    <dt>第三方使用费比例</dt>
    <dd>
        <input name="aliRation" type="text" value="<#if diy_site?? && diy_site.aliRation??>${diy_site.aliRation?string("0.00")}<#else>0</#if>" class="input normal" sucmsg=" "> 
        <span class="Validform_checktip">*</span>
    </dd>
  </dl>
  <dl>
    <dt>商家邮费收取</dt>
    <dd>
        <input name="postPrice" type="text" value="<#if diy_site?? && diy_site.postPrice??>${diy_site.postPrice?string("0.00")}</#if>" datatype="/^[+-]?\d+(\.\d+)?$/"  class="input normal" sucmsg=" "> 
        <span class="Validform_checktip">*商家每次交易时收取邮费</span>
    </dd>
  </dl>
  <dl>
    <dt>商家满额免邮</dt>
    <dd>
        <input name="maxPostPrice" type="text" value="<#if diy_site?? && diy_site.maxPostPrice??>${diy_site.maxPostPrice?string("0.00")}</#if>" class="input normal" sucmsg=" "> 
        <span class="Validform_checktip">*商品总额满额包邮</span>
    </dd>
  </dl>
  <dl>
    <dt>开启权限</dt>
    <dd>
        <div class="rule-multi-checkbox multi-checkbox">
            <span>
                <input id="cblItem_0" type="checkbox" name="isSupply" <#if diy_site?? && diy_site.isSupply?? && diy_site.isSupply==true>checked="checked"</#if>>
                <label for="cblItem_0">代理权</label>
                <input id="cblItem_1" type="checkbox" name="isStock" <#if diy_site?? && diy_site.isStock?? && diy_site.isStock==true>checked="checked"</#if>>
                <label for="cblItem_1">进货权</label>
            </span>
        </div>
    </dd>
</dl>
  <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isEnable" value="1" <#if !diy_site?? || !diy_site.isEnable?? || diy_site?? && diy_site.isEnable?? && diy_site.isEnable>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isEnable" value="0" <#if diy_site?? && diy_site.isEnable?? && !diy_site.isEnable>checked="checked"</#if>>
            <label>否</label>
        </span>
      </div>
      <span class="Validform_checktip">*不启用则不显示该门店</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if diy_site?? && diy_site.sortId??>${diy_site.sortId!""}<#else>99</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  
  <dl>
    <dt>描述说明</dt>
    <dd>
      <textarea name="info" rows="2" cols="20" class="input normal"><#if diy_site??>${diy_site.info!""}</#if></textarea>
      <span class="Validform_checktip"></span>
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
                        <th>第三方使用费</th>
                        <th>商品总额</th>
                        <th>订单总金额</th>
                        <th>实际入账/支出</th>
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
                                  <td><#if re.aliPrice??>${re.aliPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.totalGoodsPrice??>${re.totalGoodsPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.provice??>${re.provice?string('0.00')}<#else>0.00</#if></td>
                                  <td><#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if></td>
                                  <td>${re.cont!''}</td> 
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
  <div class="clear"></div>
</div>
<!--/工具栏-->
</form>


</body></html>