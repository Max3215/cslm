<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑供应商</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script src="/mag/js/jquery.cityselect.js"></script>
<script type="text/javascript">
$(function () {
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
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/provider/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" >
<input type="hidden" name="id" value="<#if provider??>${provider.id?c!""}</#if>" >
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
                <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑批发商</a></li>
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
            <input name="username" type="text" value="<#if provider??>${provider.username!""}</#if>" class="input normal" <#if provider??><#else>ajaxurl="/Verwalter/provider/check/username"</#if> datatype="s" sucmsg=" ">
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
        <dt>批发商手机</dt>
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
            <input name="virtualMoney" type="text" value="<#if provider?? && provider.virtualMoney??>${provider.virtualMoney?string('0.00')}</#if>" class="input normal"   sucmsg=" ">
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