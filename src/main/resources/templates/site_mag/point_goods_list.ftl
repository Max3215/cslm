<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>内容列表</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/jquery.lazyload.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    $(function () {
        imgLayout();
        $(window).resize(function () {
            imgLayout();
        });
        //图片延迟加载
        $(".pic img").lazyload({ load: AutoResizeImage, effect: "fadeIn" });
        //点击图片链接
        $(".pic img").click(function () {
            //$.dialog({ lock: true, title: "查看大图", content: "<img src=\"" + $(this).attr("src") + "\" />", padding: 0 });
            var linkUrl = $(this).parent().parent().find(".foot a").attr("href");
            if (linkUrl != "") {
                location.href = linkUrl; //跳转到修改页面
            }
        });
    });
    //排列图文列表
    function imgLayout() {
        var imgWidth = $(".imglist").width();
        var lineCount = Math.floor(imgWidth / 222);
        var lineNum = imgWidth % 222 / (lineCount - 1);
        $(".imglist ul").width(imgWidth + Math.ceil(lineNum));
        $(".imglist ul li").css("margin-right", parseFloat(lineNum));
    }
    //等比例缩放图片大小
    function AutoResizeImage(e, s) {
        var img = new Image();
        img.src = $(this).attr("src")
        var w = img.width;
        var h = img.height;
        var wRatio = w / h;
        if ((220 / wRatio) >= 165) {
            $(this).width(220); $(this).height(220 / wRatio);
        } else {
            $(this).width(165 * wRatio); $(this).height(165);
        }
    }
    
</script>
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/pointGoods/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
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
  <i class="arrow"></i
  <span>内容列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/pointGoods/edit?__VIEWSTATE=${__VIEWSTATE!""}"><i></i><span>新增</span></a></li>
       <!-- <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>  -->
        <#--<li><a class="all" href="javascript:__doPostBack('exportAll','')"><span>导出全部</span></a></li>-->
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      <div class="menu-list">
         <div class="rule-single-select">
            <select name="isEnable" onchange="javascript:setTimeout(__doPostBack('inEnable',''), 0)">
                <option value="">兑换状态</option>
                <option value="true" <#if inEnable?? && inEnable==true>selected="selected"</#if>>上架</option>
                <option value="false" <#if inEnable?? && inEnable==false>selected="selected"</#if>>下架</option>
            </select>
        </div>
        
      </div>
    </div>
    <div class="r-list">
      <input name="keywords" type="text" class="keyword" value="${keywords!''}">
      <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('lbtnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--文字列表-->

<!--/文字列表-->

<!--图片列表-->

<div class="imglist">
<ul style="width: 1189px;">

    <#if goodPage?? && goodPage.content??>
    <#list goodPage.content as goods>
    <li style="margin-right: 15.75px;">
        <div class="details <#if !goods.imgUrl??>nopic</#if>">
            <div class="check">
                <span class="checkall">
                    <input type="checkbox" name="listChkId" value="${goods_index?c}">
                </span>
                <input type="hidden" name="listId" id="listId" value="${goods.id?c}">
            </div>
            <#if goods.imgUrl??>
            <div class="pic">
                <img src="${goods.imgUrl!""}" data-original="" style="display: inline; width: 247.5px; height: 165px;">
            </div>
            <i class="absbg"></i>
            </#if>
            <h1><span><a href="/Verwalter/pointGoods/edit?cid=${cid!""}&mid=${mid!""}&id=${goods.id?c}&__VIEWSTATE=${__VIEWSTATE!""}">${goods.goodsTitle!""}</a></span></h1>
            <div class="remark" >${goods.subGoodsTitle!""}</div>
            <div class="tools">
               <input type="text" style="border:none;color:skyblue;" value="${goods.code!""}" readonly="readonly" >
            </div>
            <div class="foot">
                <p class="time"><#if goods.onSaleTime??>${goods.onSaleTime?string("yyyy-MM-dd HH:mm:ss")}</#if></p>
                <a href="/Verwalter/pointGoods/edit?cid=${cid!""}&mid=${mid!""}&id=${goods.id?c}&__VIEWSTATE=${__VIEWSTATE!""}" title="编辑" class="edit">编辑</a>
            </div>
        </div>
    </li>
    </#list>
    </#if>    
</ul>
</div>

<!--/图片列表-->

<!--内容底部-->
<#assign PAGE_DATA=goodPage />
<#include "/site_mag/list_footer.ftl" />
<!--/内容底部-->
</form>


</body></html>