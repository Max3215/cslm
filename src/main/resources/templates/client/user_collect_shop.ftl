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
<script src="/client/js/user.js"></script>
<script src="/layer/layer.js"></script>
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
    <#include "/client/common_header.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
  <!-- 左侧 -->
  <#include "/client/common_user_menu.ftl" />
  
  <form name="form1" action="/user/collect/list" method="POST">  
        <script type="text/javascript">
        var theForm = document.forms['form1'];
        if (!theForm) {
            theForm = document.form1;
        }
        function __doPostBack(eventTarget, eventArgument) {
            if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                theForm.submit();
            }
        }
        </script>
    <div class="mymember_mainbox">    
    <div class="mymember_info mymember_info02">
        <div class="mymember_order_search">
            <a class="a001" >店铺收藏</a>    
            <div class="clear"></div>
        </div>
        <div class="scshop_list">
          <ul class="pro_list">
          <#if collect_page??>
          <#list collect_page.content as cg>     
            <li>
              <a href="/index?id=${cg.distributorId?c}" class="a1">
                <img src="${cg.goodsCoverImageUri!''}">
              </a>
              <p class="red">${cg.goodsTitle!''}</p>
              <a href="/index?id=${cg.distributorId?c}" class="dh_btn">进入店铺</a>
            </li>
            </#list>
            </#if>
           </ul>
         </div>
      </table>
      
      <div class="myclear" style="height:10px;"></div>
      <div class="mymember_page">
           <#if collect_page??>
                <#assign continueEnter=false>
                <#if collect_page.totalPages gt 0>
                    <#list 1..collect_page.totalPages as page>
                        <#if page <= 3 || (collect_page.totalPages-page) < 3 || (collect_page.number+1-page)?abs<3 >
                            <#if page == collect_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/user/collect/list/${type}?page=${page-1}&keywords=${keywords!''}">${page}</a>
                            </#if>
                            <#assign continueEnter=false>
                        <#else>
                            <#if !continueEnter>
                                <b class="pn-break">&hellip;</b>
                                <#assign continueEnter=true>
                            </#if>
                        </#if>
                    </#list>
                </#if>
           </#if>
      </div>
   </div> 
   </div>
</form>    
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <!--底部footer-->
    <#include "/client/common_footer.ftl" />
</body>
</html>