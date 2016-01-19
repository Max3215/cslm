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

<script type="text/javascript">
$(document).ready(function(){
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

  $("#nav_down li").hover(function(){
    $(this).find(".nav_show").fadeIn(10);
  },function(){
    $(this).find(".nav_show").stop(true,true).fadeOut(10);
  })  

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
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
	      <article class="essayamin">
            <#if info??>
                <h3 class="ta-c fw400 fs20 lh25 pb20">${info.title!''}</h3>
                <div class="essaycon">${info.content!''}</div>
                <div class="clear h20"></div>
                
                <#if prev_info??>
                    <p class="mb10">上一篇：<a href="/distributor/content/${prev_info.id?c}?mid=${mid}">${prev_info.title!''}</a></p>
                </#if>
                <#if next_info??>
                    <p>下一篇：<a href="/distributor/content/${next_info.id?c}?mid=${mid}">${next_info.title!''}</a></p>
                </#if>
                <div class="clear h20"></div>
            </#if>   
            </article>
	     </div>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>