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
    
    //（抢拍缩略图）
    var flashPic = $("#flashSaleImage").val();
    if (flashPic == "" || flashPic == null) {
        $("#thumb_ImgUrl_show3").hide();
    }
    else {
        $("#thumb_ImgUrl_show3").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show3").show();
    }

    $("#flashSaleImage").blur(function () {
        var flashPic = $("#flashSaleImage").val();
        if (flashPic == "" || flashPic == null) {
            $("#thumb_ImgUrl_show3").hide();
        }
        else {
            $("#thumb_ImgUrl_show3").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show3").show();
        }
    });
    
    //（团购缩略图）
    var groupPic = $("#groupSaleImage").val();
    if (groupPic == "" || groupPic == null) {
        $("#thumb_ImgUrl_show2").hide();
    }
    else {
        $("#thumb_ImgUrl_show2").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
        $("#thumb_ImgUrl_show2").show();
    }

    $("#groupSaleImage").blur(function () {
        var groupPic = $("#groupSaleImage").val();
        if (groupPic == "" || groupPic == null) {
            $("#thumb_ImgUrl_show2").hide();
        }
        else {
            $("#thumb_ImgUrl_show2").html("<ul><li><div class='img-box1'><img src='" + groupPic + "' bigsrc='" + groupPic + "' /></div></li></ul>");
            $("#thumb_ImgUrl_show2").show();
        }
    });
    
    //设置封面图片的样式
    $(".photo-list ul li .img-box img").each(function () {
        if ($(this).attr("src") == $("#hidFocusPhoto").val()) {
            $(this).parent().addClass("selected");
        }
    });
    
    // 根据类型载入参数
    $("#categoryId").change(function(){
        $.ajax({
            url : '/Verwalter/goods/edit/parameter/'+$(this).val(),
            type : 'POST',
            success : function(res) {
                $("#id-param-sec").html(res);
            }
        });
    });
    
    // 添加赠品
    $("#addGift").click(function(){
        showDialogGift();
    });
    
    // 添加组合
    $("#addCombination").click(function(){
        showDialogCombination();
    });
    
    //创建促销赠品窗口
    function showDialogGift(obj) {
        var objNum = arguments.length;
        
        var giftDialog = $.dialog({
            id: 'giftDialogId',
            lock: true,
            max: false,
            min: false,
            title: "赠品",
            content: 'url:/Verwalter/goods/list/dialog/gift?total=' + $("#var_box_gift").children("tr").length,
            width: 800,
            height: 350
        });
        
        //如果是修改状态，将对象传进去
        if (objNum == 1) {
            giftDialog.data = obj;
        }
    }
    
    
    // 判断粮草购买限额不能大于最高返现额
    $("#pointLimited").change(function(){
        var point = $.trim($('#pointLimited').val());
        var price = $.trim($('#returnPrice').val())
        if (isNaN(point) || point=="") { p1 = 0 }
        if (isNaN(price) || price== "") { p2 = 0 }
        
        if (parseFloat(price) < parseFloat(point))
        {
            alert("购买粮草限额不能大于最高返现额!");
            $(this).val("0");
        }
    });
});

  
</script>
</head>
<body class="mainbody">
<form method="post" action="/Verwalter/distributor/goods/save" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>
<input name="id" type="text" value='<#if goods??>${goods.id?c}</#if>' style="display:none">
<#--
<input name="distributorId" type="text" value='<#if distributorId??>${distributorId}</#if>' style="display:none">
<input name="goodsId" type="text" value='<#if goods??>${goods.goodsId?c}</#if>' style="display:none">
<input name="categoryIdTree" type="text" value='<#if goods??>${goods.categoryIdTree!''}</#if>' style="display:none">

<input name="paramValueCollect" type="text" value='<#if goods??>${goods.paramValueCollect!''}</#if>' style="display:none">
<input name="brandTitle" type="text" value='<#if goods??>${goods.brandTitle!''}</#if>' style="display:none">
<input name="brandId" type="text" value='<#if goods??>${goods.brandId!''}</#if>' style="display:none">
-->
<!--导航栏-->
<div class="location">
    <a href="/Verwalter/distributor/goods/list" class="back"><i></i><span>
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
                    <#--
                    <li><a href="javascript:;" onclick="tabs(this);" class="">扩展选项</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">详细描述</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">价格与库存</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">促销</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">赠品</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">组合商品</a></li>
                    <li><a href="javascript:;" onclick="tabs(this);" class="">SEO选项</a></li>
                    -->
                </ul>
            </div>
        </div>
    </div>
    <div id="id-first-tab" class="tab-content" style="display: block;">
        <dl>
            <dt>所属类别</dt>
            <dd>
                <div class="rule-single-select">
                    <select name="categoryId" id="categoryId" datatype="*" sucmsg=" ">
                        <#if !goods??>
                        <option value="">请选择类别...</option>
                        </#if>
                        <#if category_list?? >
                            <#list category_list as c>
                                <option value="${c.id?c}" <#if goods?? && goods.categoryId==c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            </dd>
        </dl>
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
                <input name="subGoodsTitle" type="text" value="<#if goods??>${goods.subGoodsTitle!""}</#if>" class="input normal" datatype="*1-255" sucmsg=" ">
                <span class="Validform_checktip">*标题最多255个字符</span>
            </dd>
        </dl>
        <dl>
            <dt>编码</dt>
            <dd>
                <input id="code"  name="code" type="text" value="<#if goods?? && goods.code??>${goods.code!''}</#if>" class="input normal" >
                <span class="Validform_checktip">*商品编码</span>
            </dd>
        </dl>
        <dl>
            <dt>市场价</dt>
            <dd>
                <input id="outFactoryPrice"   name="outFactoryPrice" type="text" value="<#if goods?? && goods.goodsMarketPrice??>${goods.goodsMarketPrice?string("0.##")}<#else>0</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
                <span class="Validform_checktip">*商品市场价</span>
            </dd>
        </dl>
        <dl>
            <dt>销售价</dt>
            <dd>
                <input id="outFactoryPrice"  name="goodsPrice" type="text" value="<#if goods?? && goods.goodsPrice??>${goods.goodsPrice?string("0.##")}<#else>0</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
                <span class="Validform_checktip">*商品供货价</span>
            </dd>
        </dl>
        <dl>
            <dt>销售状态</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isOnSale" value="1" <#if goods??==false || goods.isOnSale==true>checked="checked"</#if> >
                        <label>上架</label>
                        <input type="radio" name="isOnSale" value="0" <#if goods?? && goods.isOnSale?? && goods.isOnSale==false>checked="checked"</#if> >
                        <label>下架</label>
                    </span>
                </div>
            </dd>
        </dl>
        <dl>
            <dt>推荐类型</dt>
            <dd>
                <div class="rule-multi-checkbox multi-checkbox">
                    <span>
                        <input id="cblItem_0" type="checkbox" name="isRecommendIndex" <#if goods?? && goods.isRecommendIndex?? && goods.isRecommendIndex==true>checked="checked"</#if>>
                        <label for="cblItem_0">首页推荐</label>
                        <input id="cblItem_1" type="checkbox" name="isSetRecommend" <#if goods?? && goods.isSetRecommend?? && goods.isSetRecommend==true>checked="checked"</#if>>
                        <label for="cblItem_1">平台推荐</label>
                        <input id="cblItem_1" type="checkbox" name="isRecommendType" <#if goods?? && goods.isRecommendType?? && goods.isRecommendType==true>checked="checked"</#if>>
                        <label for="cblItem_1">分类推荐</label>
                        <input id="cblItem_1" type="checkbox" name="isTouchHot" <#if goods?? && goods.isTouchHot?? && goods.isTouchHot==true>checked="checked"</#if>>
                        <label for="cblItem_1">触屏首页热卖</label>
                    </span>
                </div>
            </dd>
        </dl>
        <#if goods.isAudit??>
        <dl>
            <dt>审核状态</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isAudit" value="1" <#if goods?? || goods.isAudit==true>checked="checked"</#if> >
                        <label>已审核</label>
                        <input type="radio" name="isAudit" value="0" <#if goods?? && goods.isAudit?? && goods.isAudit==false>checked="checked"</#if> >
                        <label>待审核</label>
                    </span>
                </div>
            </dd>
        </dl>
        </#if>
        <#if goods.isDistribution??>
        <dl>
            <dt>分销状态</dt>
            <dd>
                <div class="rule-multi-radio multi-radio">
                    <span>
                        <input type="radio" name="isDistribution" value="1" <#if goods?? || goods.isDistribution==true>checked="checked"</#if> >
                        <label>分销</label>
                        <input type="radio" name="isDistribution" value="0" <#if goods?? && goods.isAudit?? && goods.isDistribution==false>checked="checked"</#if> >
                        <label>未分销</label>
                    </span>
                </div>
            </dd>
        </dl>
        </#if>
        <dl>
            <dt>赠送积分</dt>
            <dd>
                <input name="returnPoints"  type="text" value="<#if goods?? && goods.returnPoints??>${goods.returnPoints!'0'}<#else>0</#if>" class="input normal" sucmsg="">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>封面图片</dt>
            <dd>
                <input id="txtImgUrl" name="coverImageUri" type="text" value="<#if goods??>${goods.coverImageUri!""}</#if>" class="input normal upload-path">
                <div class="upload-box upload-img"></div>
                <div id="thumb_ImgUrl_show1" class="photo-list thumb_ImgUrl_show">
                </div>
            </dd>
        </dl>
        
        <dl>
            <dt>上架时间</dt>
            <dd>
                <div class="input-date">
                    <input name="onSaleTime" type="text" value="<#if goods??>${goods.onSaleTime!""}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
                    <i>日期</i>
                </div>
                <span class="Validform_checktip">不选择默认为当前时间</span>
            </dd>
        </dl>
        <dl>
            <dt>销量</dt>
            <dd>
                <input name="soldNumber" type="text" value="<#if goods?? && goods.soldNumber??>${goods.soldNumber?c!"0"}<#else>0</#if>" class="input normal" datatype="n" sucmsg=" ">
                <span class="Validform_checktip"></span>
            </dd>
        </dl>
        <dl>
            <dt>库存余量</dt>
            <dd>
                <input name="leftNumber" type="text" value="<#if goods?? && goods.leftNumber??>${goods.leftNumber?c!"99"}<#else>99</#if>" class="input normal" datatype="n" sucmsg=" ">
                <span class="Validform_checktip">库存为0时自动下架</span>
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