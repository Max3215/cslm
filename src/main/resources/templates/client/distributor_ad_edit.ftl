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
          <h3>编辑广告</h3>
        </div>
        <div class="edit_banner">
        <form id ="form1" action="/distributor/ad/save" method="post">
           <input type="hidden" value="<#if ad??>${ad.id?c}</#if>" name="id">
          <table>
            <tr>
              <th>所属广告位</th>
              <td>
                <select name="typeId" datatype="*" >
                   <option value="" <#if !ad??>selected="selected"</#if>>请选择</option>
                    <#if ad_type_list??>
                        <#list ad_type_list as type>
                            <#if type_index lt 8 >
                            <option value="${type.id?c}" <#if ad?? && ad.typeId == type.id>selected="selected"</#if>>${type.title}</option>
                            </#if>
                        </#list>
                    </#if>
                </select>
              </td>
            </tr>
            <tr>
              <th>状态</th>
              <td>
                <input type="radio" name="isEnable" value="1" <#if !ad?? || ad?? && ad.isEnable>checked="checked"</#if>>
                <label>正常</label>
                <input type="radio" name="isEnable" value="0" <#if ad?? && ad.isEnable?? && !ad.isEnable>checked="checked"</#if>>
                <label>下架</label>
                <div class="clear"></div>
              </td>
            </tr>
            <tr>
              <th>广告名称</th>
              <td><input type="text" class="text" name="title" datatype="*" value="<#if ad??>${ad.title!""}</#if>"/></td>
            </tr>
            <tr>
              <th>开始时间</th>
              <td>
              <input name="startTime" type="text" value="<#if ad??>${ad.startTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" class="text date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
              </td>
            </tr>
            <tr>
              <th>到期时间</th>
              <td>
              <input name="endTime" type="text" value="<#if ad??>${ad.endTime?string('yyyy-MM-dd HH:mm:ss')}</#if>" class="text date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'zh-cn'})" datatype="/^\s*$|^\d{4}\-\d{1,2}\-\d{1,2}\s{1}(\d{1,2}:){2}\d{1,2}$/" errormsg="请选择正确的日期" sucmsg=" ">
              </td>
            </tr>
            <tr>
              <th>链接地址</th>
              <td>
              <input name="linkUri" type="text" value="<#if ad??>${ad.linkUri!""}</#if>" class="text date" datatype="*" errormsg="" sucmsg=" ">
              </td>
            </tr>
            <tr >
              <th >广告图</th>
              <td>
                <input id="txtImgUrl" name="fileUri" type="text" datatype="*" value="<#if ad??>${ad.fileUri!""}</#if>" class="text normal upload-path">
                <div class="upload-box upload-img"></div>
                <div class="photo-list thumb_ImgUrl_show">
                    <ul>
                        <li>
                            <div class="img-box"></div>
                        </li>
                    </ul>
                </div>
                <span class="Validform_checktip"></span>
              </td>
            </tr>
            <tr>
              <th></th>
              <td><input type="submit" class="sub" value="保存"></td>
            </tr>
          </table>
          </form>
        </div>
        
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