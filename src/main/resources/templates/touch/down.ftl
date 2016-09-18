<!doctype html>
<html lang="zh-CN">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
    <meta http-equiv="Content-Language" content="zh-CN">
    <title><#if site??>${site.seoTitle!''}-</#if>联超商城</title>
    <meta name="keywords" content="${site.seoKeywords!''}">
    <meta name="description" content="${site.seoDescription!''}">
    <meta name="copyright" content="${site.copyright!''}" />
    <link href="/touch/images/cslm.ico" rel="shortcut icon">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"> 
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="format-detection" content="telephone=no" />
    <meta name="format-detection" content="email=no" />
    <meta name="format-detection" content="address=no" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
    <link rel="stylesheet" href="/touch/css/swiper-3.3.1.min.css">
    <script src="/touch/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
    	function setRootSize() {
		    var deviceWidth = document.documentElement.clientWidth; 
		    if(deviceWidth>750){deviceWidth = 750;}  
		    document.documentElement.style.fontSize = deviceWidth / 7.5 + 'px';
		}
		setRootSize();
		window.addEventListener('resize', function () {
		    setRootSize();
		}, false);
		window.onload = function(){
		    setRootSize();
		}
    </script>
    <script src="/touch/js/swiper-3.3.1.min.js"></script>
</head>
<style>
	*{margin: 0;padding: 0; font-family:"Microsoft YaHei",微软雅黑,"MicrosoftJhengHei",华文细黑,STHeiti,MingLiu; font-size: 0.24rem;}
	html,body{ max-width: 720px; margin: 0 auto;}
	.appdowninner{}
	.appdowntitle{ position: relative; padding:0.2rem; padding-left: 1.4rem; height: 0.98rem; overflow: hidden;}
	.appdowntitle img{ position: absolute; left: 0.2rem; top: 0.2rem; width: 0.98rem; height: 0.98rem;}
	.appdowntitle h1{ font-size:0.32rem; font-weight: normal;}
	.appdowntitle p{ display: block; padding-top: 0.05rem; font-size:0.24rem; color: #666;}
	.appimgshow{ background-color: #eee; padding:0.2rem; overflow: hidden;}
	.r5{ border-radius:0.05rem;}
	.swiper-slide{ overflow: hidden; height: auto;}
	.swiper-slide img{ display: block; width: 100%;}
	.swiper-container-horizontal>.swiper-pagination-bullets, .swiper-pagination-custom, .swiper-pagination-fraction{ height: 0.2rem; }
	.swiper-container-horizontal>.swiper-pagination-bullets .swiper-pagination-bullet{ margin:0 2px;}
	.swiper-pagination-bullet{ width: 5px; height: 5px;}
	.appinfo{ padding:0.2rem;}
	.appinfo h2{ display: block; line-height: 0.72rem; font-size:0.28rem; border-bottom:1px solid #eee;}
	.appinfowrap{ padding:0.2rem 0; line-height: 0.38rem; color: #666; text-indent: 0.48rem;}
	.appdownbut{ position: fixed; z-index: 2; left: 0; bottom: 0; padding: 0.1rem 2%; width: 96%; box-shadow: 0 0 0.4rem rgba(0,0,0,0.1); background-color: #fff; border-top: 1px solid #eee; }
	.appdownbut a{ display:  block; float: left; width: 49%; height: 0.8rem; line-height: 0.8rem; text-align: center; color: #fff; text-decoration: none; }
	.appdownbut a:before{ content: ""; display: inline-block; width: 0.4rem; height: 0.4rem; position: relative; top: 0.1rem; margin-right: 0.1rem;}
	.appdownbut a:first-child{ background-color: #ff5b7d;}
	.appdownbut a:first-child:before{ background:url(/touch/images/android.png) no-repeat center center; background-size:auto 100%;}
	.appdownbut a:last-child{ background-color: #333; float: right;}
	.appdownbut a:last-child:before{ background:url(/touch/images/ios.png) no-repeat center center; background-size:auto 100%;}
	
	.muban{width: 100%;height: 100%;position: absolute; z-index: 9; top: 0;left: 0;background-color: #000;opacity: 0.8;display: none;}
	.jiantou{-width: 20%;float: right;margin-right: 5%;}
	.wenzi{font-size: 2rem;display: inline-block;color: #fff;font-family: "微软雅黑";margin: 0 auto;}
	.wenzi_liu{text-align: center;}
	.beijingshouji{width: 100%;display: inline-block;}

</style>
<body>
	<div class="appdowninner">
		<div class="appdowntitle">
			<img src="/touch/images/icon.png">
			<h1>联超商城</h1>
			<p><#if site??>${site.bottomLogoUri2!''}</#if></p>
		</div>
		<div class="appimgshow">
			<div class="swiper-container">
				<div class="swiper-wrapper">
				    <#if down_ad_list??>
				    <#list down_ad_list as ad>
					<div class="swiper-slide r5"><img src="${ad.fileUri!''}"></div>
					</#list>
					</#if>
				</div>
				<div class="swiper-pagination"></div>
			</div>
		</div>
		<script language="javascript"> 
			var mySwiper = new Swiper('.swiper-container',{
				slidesPerView : 2,
				spaceBetween : 6,
				pagination : '.swiper-pagination',
				});
		</script>
		<div class="appinfo">
			<h2>应用描述</h2>
			<div class="appinfowrap">
				<p><#if site??>${site.bottomLogoUri1!''}</#if></p>
			</div>
		</div>
	</div>
	<div style="height:1.2rem;"></div>

	<!-- 下载按钮 -->
	<div class="appdownbut">
		<a href="/touch/download" class="app_android r5">Android版下载</a>
		<a href="https://itunes.apple.com/cn/app/chao-shi-lian-meng/id1142052556?mt=8" class="app_iphone r5">IOS版下载
	</div>
	<div class="muban">
		<div class="jiantou tu">
			<img src="/touch/images/jiantou.png">
		</div>
		<div class="wenzi">
			<div class="wenzi_liu">
				<span>通过在[浏览器中打开]</span>
			</div>
			<div class="wenzi_liu">
				<span>下载应用吧！</span>
			</div>
			
		</div>
	</div>	
</body>
<script>
	$(function(){
	$(".app_android").click(function(){
		var link="/touch/download";
		isWeiXin(link);
	})
	 $(".app_iphone").click(function(){
	 	var link="https://itunes.apple.com/cn/app/chao-shi-lian-meng/id1142052556?mt=8";
	 	isWeiXin(link);
	 });
	var rightwidth= $(window).width();
	$(".wenzi_liu").css("width",rightwidth);
	var zuidawidth=$(".beijingshouji").width();
	var zuizongwidth=(rightwidth-zuidawidth)/2;

	$(".beijingshouji").css("left",zuizongwidth);
})

function isWeiXin(link){ 
	var ua = window.navigator.userAgent.toLowerCase(); 
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
		$(".muban").show();
		$(".muban").click(function(){
			$(this).hide();
		})
	}else{ 
		window.location.href=link;
	} 
} 
</script>
</html>