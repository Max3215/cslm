<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<script type="text/javascript" src="/client/js/swfupload.js"></script>
<script type="text/javascript" src="/client/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/client/js/swfupload.handlers.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/mag/js/zh_CN.js"></script>
<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype: 3,
    });
    
    // 初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            btntext : "上传图片",
            sendurl: "/client/upload", 
            flashurl: "/mag/js/swfupload.swf"
        });
    });
    
    
    //初始化编辑器
    var editor = KindEditor.create('.editor', {
        width: '100%',
        height: '500px',
        resizeType: 1,
        uploadJson: '/Verwalter/editor/upload?action=EditorFile',
        fileManagerJson: '/Verwalter/editor/upload?action=EditorFile',
        allowFileManager: true
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
    
})
</script>
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/client/js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    <#include "/client/common_distributor_menu.ftl">
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
          <h3>编辑文章</h3>
        </div>
        
        <form id ="form1" action="/distributor/article/save" method="post">
        <div class="edit_banner">
           <input type="hidden" value="<#if info??>${info.id?c}</#if>" name="id">
           <input type="hidden" value="<#if info??>${info.menuId!'0'}</#if>" name="menuId">
           <input type="hidden" value="<#if info??>${info.categoryId!'0'}</#if>" name="categoryId">
           <input type="hidden" value="<#if info??>${info.channelId!'0'}</#if>" name="channelId">
           <input type="hidden" value="<#if info?? && info.distributorId??>${info.distributorId!'0'}</#if>" name="distributorId">
          <table>
            <tr>
              <th>文章名称</th>
              <td><input type="text" class="text" name="title" datatype="*" value="<#if info??>${info.title!""}</#if>"/></td>
            </tr>
            <tr>
              <th>状态</th>
              <td>
                <input type="radio" name="statusId" value="0" <#if info?? && info.statusId==0>checked="checked"</#if>>
                <label>显示</label>
                <input type="radio" name="statusId" value="2" <#if info?? && info.statusId ==2>checked="checked"</#if>>
                <label>不显示</label>
                <div class="clear"></div>
              </td>
            </tr>
            <tr>
              <th>浏览次数</th>
              <td><input type="text" class="text" name="viewCount" datatype="n" value="<#if info??>${info.viewCount!"0"}<#else>0</#if>"/></td>
            </tr>
            <tr>
              <th>文章摘要</th>
              <td><input type="text" class="text" name="brief" value="<#if info??>${info.brief!""}</#if>"/></td>
            </tr>
            <tr>
                <th>内容描述</th>
                <td>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea name="content" class="editor" style="visibility:hidden;"><#if info??>${info.content!""}</#if></textarea>
                </td>
            </tr>
            <tr>
              <th></th>
              <td><input type="submit" class="sub" value="保存"></td>
            </tr>
          </table>
        </div>
          
      </form>
        
      </div>

    </div>
 
    <div class="myclear"></div>
  </div>
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">

 
</body>
</html>