<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="<#if productCategory??>${productCategory.seoKeywords!''}</#if>" />
<meta name="description" content="<#if productCategory??>${productCategory.seoDescription!''}</#if>" />
<meta name="copyright" content="<#if site??>${site.copyright!''}</#if>" /> 

<link href="/client/images/cslm.ico" rel="shortcut icon">
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<title>404</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.cityselect.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#add").citySelect({
    nodata:"none",
    prov: "云南",
    required:false
    });


    $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");
 //   bannerCartoon("n_banner_box","a","n_banner_num",300,5000,"","");

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
})


</script>
<style type="text/css">
	.error_box{background: #38bfe9;height: 500px;}
	.error_box .con{padding-left: 580px;color:#fff;height: 500px;background: url(/client/images/error.png) no-repeat 302px center;}
	.error_box .con .p1{font-size: 36px;font-weight: bold;padding-top: 150px;line-height: 72px;}
	.error_box .con .p2{font-size: 24px;}
	.error_box .con .btns{overflow: hidden;}
	.error_box .con .btns a{display: block;width: 120px;height: 32px;background: #fff;color:#38bfe9;line-height: 32px;text-align: center;border-radius: 3px;-webkit-border-radius: 3px;float:left;font-size: 14px;margin-left: 50px;margin-top: 56px;}
</style>



</head>

<body>
	<#include "/client/common_header.ftl" />
	<div class="error_box">
		<div class="main">
			<div class="con">
				<p class="p1">抱歉！</p>
				<p class="p2">您访问的页面已经删除或更改地址。</p>
				<menu class="btns">
					<a href="javascript:history.go(-1);">返回上一页>></a>
					<a href="/">访问首页>></a>
				</menu>
			</div>
		</div>
	</div>
 <#include "/client/common_footer.ftl" />
</body>
</html>