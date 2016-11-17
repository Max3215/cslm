<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑信息</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/mag/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/mag_goods.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/WdatePicker.css" rel="stylesheet" type="text/css">
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/default.css" rel="stylesheet">
<script type="text/javascript">
$(function () {
    //初始化表单验证
    $("#form1").initValidform();

    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '98%',
        height: '350px',
        resizeType: 1,
        uploadJson: '/Verwalter/editor/upload?action=EditorFile',
        fileManagerJson: '/Verwalter/editor/upload?action=EditorFile',
        allowFileManager: true
    });
    
    //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            sendurl: "/Verwalter/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
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

    //（缩略图）
    var txtPic = $("#txtImgUrl").val();
    if (txtPic == "" || txtPic == null) {
        $("#thumb_ImgUrl_show1").hide();
    }
    else {
        $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show1").show();
    }

    $("#txtImgUrl").blur(function () {
        var txtPic = $("#txtImgUrl").val();
        if (txtPic == "" || txtPic == null) {
            $("#thumb_ImgUrl_show1").hide();
        }
        else {
            $("#thumb_ImgUrl_show1").html("<ul><li><div class='img-box1'><img src='" + txtPic + "' bigsrc='" + txtPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show1").show();
        }
    });
    
    
    
    //设置封面图片的样式
    $(".photo-list ul li .img-box img").each(function () {
        if ($(this).attr("src") == $("#hidFocusPhoto").val()) {
            $(this).parent().addClass("selected");
        }
    });
    
});

    

  
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/pointGoods/save" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="menuId" type="text" value='${mid!""}' style="display:none;">
<input name="channelId" type="text" value='${cid!""}' style="display:none">
<input name="id" type="text" value='<#if goods??>${goods.id?c}</#if>' style="display:none">
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/pointGoods/list" class="back"><i></i><span>
        返回列表页</span></a> 
    <a href="/Verwalter/center" class="home">
    <i></i><span>首页</span></a>
    <i class="arrow"></i>
    <span>编辑信息</span>
</div>
<div class="line10">
</div>
<!--/导航栏-->
    <!--内容-->
    <div class="content-tab-wrap">
        <div id="floatHead" class="content-tab">
            <div class="content-tab-ul-wrap" >
                <ul>
                    <li><a href="javascript:;" onclick="tabs(this);" class="selected">基本信息</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">详细描述</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>商品标题</dt>
            <dd>
                <input name="goodsTitle" type="text" value="<#if goods??>${goods.goodsTitle!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" ">
                <span class="Validform_checktip">*标题最多100个字符</span>
            </dd>
        </dl>
        <dl>
            <dt>商品副标题</dt>
            <dd>
                <input name="subGoodsTitle" type="text" value="<#if goods??>${goods.subGoodsTitle!""}</#if>" class="input normal" >
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>编码</dt>
            <dd>
                <input name="code" type="text" value="<#if goods??>${goods.code!""}</#if>" class="input normal" datatype="*0-255" ajaxurl="/Verwalter/pointGoods/check<#if goods??>?id=${goods.id?c}</#if>">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>市场价</dt>
            <dd>
                <input name="goodsPrice" type="text" value="<#if goods?? && goods.goodsPrice??>${goods.goodsPrice?string("0.00")}<#else>0</#if>" class="input normal"  sucmsg=" ">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>兑换积分</dt>
            <dd>
                <input name="point" type="text" value="<#if goods??>${goods.point!'100'}<#else>100</#if>" class="input normal"  sucmsg=" " datatype="n">
                <span class="Validform_checktip">*</span>
            </dd>
        </dl>
        <dl>
            <dt>总数量</dt>
            <dd>
                <input name="leftNumber" type="text" value="<#if goods??>${goods.leftNumber!'0'}<#else>100</#if>" class="input normal"  sucmsg=" " datatype="n">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>已兑换数量</dt>
            <dd>
                <input name="saleNumber" type="text" value="<#if goods??>${goods.saleNumber!'0'}<#else>0</#if>" class="input normal"  sucmsg=" " datatype="n">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>销售状态</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isEnable" value="1" <#if goods??==false || goods.isEnable==true>checked="checked"</#if> >
                        <label>上架</label>
                        <input type="radio" name="isEnable" value="0" <#if goods?? && goods.isEnable?? && goods.isEnable==false>checked="checked"</#if> >
                        <label>下架</label>
                    </span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>添加时间</dt>
            <dd>
                <div class="input-date">
                    <input name="onSaleTime" type="text" value="<#if goods??>${goods.onSaleTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip">不选择默认为当前时间</span>
            </dd>
        </dl>
    </div>
    <div class="tab-content" style="display: none;">
         <dl>
            <dt>封面图片</dt>
            <dd>
                <input id="txtImgUrl" name="imgUrl" type="text" value="<#if goods??>${goods.imgUrl!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div id="thumb_ImgUrl_show1" class="photo-list thumb_ImgUrl_show">
                </div>
            </dd>
        </dl>
        <dl>
            <dt>兑换说明</dt>
            <dd>
                <textarea name="changeDetail" rows="2" cols="20"  class="input" datatype="*0-255" sucmsg=" "><#if goods??>${goods.changeDetail!""}</#if></textarea>
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
         <dl>
            <dt>详细描述</dt>
            <dd>
                <textarea name="detail" class="editor"><#if goods??>${goods.detail!""}</#if></textarea>
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