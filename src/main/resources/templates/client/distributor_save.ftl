<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<title><#if site??>${site.seoTitle!''}-</#if>商家入驻</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<!--<script src="/client/js/city.min.js"></script>-->

<script type="text/javascript" src="/client/js/swfupload.js"></script>
<script type="text/javascript" src="/client/js/swfupload.queue.js"></script>
<script type="text/javascript" src="/client/js/swfupload.handlers.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    
    
    //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    })

    $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
    
     //初始化上传控件
    $(".upload-img").each(function () {
        $(this).InitSWFUpload({ 
            btntext : "上传营业执照",
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
	
	$("#address").citySelect({
        nodata:"none",
        <#if diy_site?? && diy_site.province??>prov: "${diy_site.province!''}",</#if>
        <#if diy_site?? && diy_site.city??>city: "${diy_site.city!''}",</#if>
        <#if diy_site?? && diy_site.disctrict??>dist: "${diy_site.disctrict!''}",</#if>
        required:false
    });
	
})

</script>

</head>

<body>
	<!--  顶部  -->
    <#include "/client/common_header.ftl" />

	
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="/">首页</a>>商家入驻</p>
	</section>

	<!--右边悬浮框-->
    <#include "/client/common_float_box.ftl" />

	<!--main-->
	<form id="form1" action="/distributor/save"  method="post">
	<div class="main">
		<section class="apply">
			<h3>商家入驻申请</h3>
			<table>
			 <!--
				<tr>
					<th>登录账号：</th>
					<td>
					   <input class="text" type="text" name="username" datatype="*6-100" sucmsg=" " ajaxurl="/distributor/check/username" >
					   <span class="Validform_checktip">*登录账号</span>
					</td>
				</tr>
				<tr>
					<th>虚拟账户：</th>
					<td>
					   <input class="text" type="text" name="virtualAccount" datatype="*6-20" sucmsg=" " ajaxurl="/distributor/check/virtualAccount">
					   <span class="Validform_checktip">*虚拟账户</span>
					</td>
				</tr>
				<tr>
					<th>密码：</th>
					<td>
					   <input class="text" type="password" name="password" datatype="*6-20" sucmsg=" ">
					   <span class="Validform_checktip">*</span>
					</td>
				</tr>
				<tr>
					<th>确认密码：</th>
					<td>
					    <input class="text" type="password" datatype="*" recheck="password" sucmsg=" ">
    					<span class="Validform_checktip">*</span>
					</td>
				</tr>
				-->
				<tr>
					<th>商家名称：</th>
					<td>
					   <input class="text" type="text" name="title" datatype="*2-100" sucmsg=" ">
					   <span class="Validform_checktip">*</span>
					</td>
				</tr>
				<tr>
					<th>所属地区：</th>
					<td>
					   <div id="address">
    					   <select name="province" class="prov" style="width: 100px;" datatype="*"></select>
                           <select name="city" class="city" style="width: 100px;" datatype="*"></select>
                           <select name="disctrict" class="dist" style="width: 100px;" datatype="*0-10"></select>
    					   <span style="float:none;" class="Validform_checktip">*</span>
					   </div>
					</td>
				</tr>
				<tr>
					<th>详细地址：</th>
					<td>
					   <input type="text" class="text" name="address" datatype="*">
					   <span class="Validform_checktip">*</span>
					<#--
					    <input name="longitude" type="text" class="text" value="<#if diy_site?? && diy_site.longitude??>${diy_site.longitude?string("#.######")}</#if>" class="input normal" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="" sucmsg=" ">
                        <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
                        <span class="Validform_checktip"></span>
                      -->
					</td>
				</tr>
				<tr>
					<th>手机号：</th>
					<td>
					   <input class="text" type="text" name="mobile" datatype="m" sucmsg=" ">
					   <span style="float:none;" class="Validform_checktip">*用于接收短信信息</span>
					</td>
				</tr>
				<tr>
					<th>客服电话：</th>
					<td>
					   <input class="text" type="text" name="serviceTele" datatype="*" sucmsg=" ">
					   <span class="Validform_checktip">*</span>
					</td>
				</tr>
				<tr>
					<th>商家介绍：</th>
					<td>
						<textarea name="info"></textarea>
					</td>
				</tr>
				<tr>
				    <th></th>
				    <td>
				        <input id="txtImgUrl" style="display:none" name="imageUri" type="text" datatype="*" value="<#if diy_site?? && diy_site.imageUri??>${diy_site.imageUri!""}</#if>" class="input normal upload-path">
                        <div class="upload-box upload-img"></div>
                        <div class="photo-list thumb_ImgUrl_show">
                            <ul>
                                <li>
                                    <div class="img-box1" ></div>
                                </li>
                            </ul>
                        </div>
                        <span class="Validform_checktip"></span>
				        
				        <#--
				        <div class="upload-box upload-show360"></div>
                        <div class="photo-list_show360">
                            <ul>
                                <#if diy_site?? && diy_site.showPictures??>
                                    <#list diy_site.showPictures?split(",") as uri>
                                        <#if uri != "">
                                        <li>
                                            <input type="hidden" name="hid_photo_name_show360" value="0|${uri!""}|${uri!""}">
                                            <div class="img-box">
                                                <img src="${uri!""}" bigsrc="${uri!""}" width="80px" max-height="80px">
                                            </div>
                                            <a href="javascript:;" onclick="delImg(this);">删除</a>
                                        </li>
                                        </#if>
                                    </#list>
                                </#if>
                            </ul>
                        </div>
                            -->
				    </td>
				</tr>
				<tr>
					<th></th>
					<td><input  class="sub" type="submit" value="提交申请"></td>
				</tr>
			</table>
		</section>
	</div>
	</form>


	<!--  底部    -->
    <#include "/client/common_footer.ftl" />

</body>
</html>