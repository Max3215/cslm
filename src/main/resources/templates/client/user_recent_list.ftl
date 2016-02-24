<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
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
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $(".click_a").click(function(){
        if($(this).next().is(":visible")==false){
            $(this).next().slideDown(300);
        }else{
            $(this).next().slideUp(300);
        }
    });//选择超市下拉效果

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");

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
    <!--  顶部  -->
    <#include "/client/common_header.ftl">
    
    <!--右边悬浮框-->
    <#include "/client/common_float_box.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
  
  <div class="mymember_scan_check">
    <a class="mysel">全部分类</a>
  </div>
  
  <script type="text/javascript">
    $(document).ready(function(){
         memberScanList("scan_line",".mymember_scan");
    });
  </script>
  <div class="mymember_scan">
    <span id="scan_line"></span>
    <#if recent_page??>
         <#list recent_page.content as rg>
               <#if !lastTime?? || rg.visitTime?string("yyyy-MM-dd") != lastTime?string("yyyy-MM-dd")>
                   <div class="myclear"></div>
                   <#assign lastTime=rg.visitTime>
                   <h3>${rg.visitTime?string("MM月dd日")}<span>${rg.visitTime?string("yyyy-MM-dd")}</span><i></i></h3>
                   <div class="myclear"></div>
                </#if>
                <div class="mymember_scanpart">
                    <a href="/goods/${rg.goodsId?c!''}">
                        <img src="${rg.goodsCoverImageUri!''}" width="220" height="220">
                    </a>
                    <p>￥${rg.goodsSalePrice?string("0.00")}</p>
                </div>
            </#list>
        </#if>
    <div class="myclear"></div>
  </div><!--mymember_scan END-->
  
  <div class="myclear"></div>
    <#if user_bottom_ad_list??>
    <#list user_bottom_ad_list as ad>
        <a href="${ad.linUri!''}"><img src="${ad.fileUri!''}" width="388" height="120" /></a>
    </#list>
    </#if>
  <div class="myclear"></div>  
</div><!--mymember_main END-->
<div class="myclear"></div>
</div>
<!--mymember END-->

<div class="clear"></div>

    <#include "/client/common_footer.ftl">
</body>
</html>




  











