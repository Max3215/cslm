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

</body>
</html>