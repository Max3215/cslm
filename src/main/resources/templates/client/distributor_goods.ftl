<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script src="/client/js/distributor_goods.js"></script>
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    });
});

//上架/下架多个商品
function onsaleAll(){
    $("#form").submit();
}   
</script>
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>

<form action="/distributor/onsaleAll/${isOnSale?c}" method="post" id="form">
<div class="myclear"></div>
<div class="mymember_out">
    <div class="mymember_main">
       <#include "/client/common_distributor_menu.ftl">
    
        <div class="mymember_mainbox">
            <div class="mymember_info mymember_info02">
                <div class="mymember_order_search"> 
                    <h3><#if isOnSale>出售中的商品<#else>仓库中的商品</#if></h3>
                      <#--
                      <input class="mysub" type="submit" value="查询" />
                      <p class="fr pl10 c3">价格&nbsp;&nbsp;<input type="text" style="width:50px;">&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" style="width:50px;"></p>
                      <input class="mytext" type="text" onFocus="if(value=='商品编码') {value=''}" onBlur="if (value=='') {value='商品编码'}"  value="商品编码" style="width:150px;" />
                      <input class="mytext" type="text" onFocus="if(value=='商品名称') {value=''}" onBlur="if (value=='') {value='商品名称'}"  value="商品名称" />
                        -->
                    <div class="clear"></div>
                </div>
                
                <div id="dis_goods_list">
                    <#include "/client/distributor_goods_list.ftl">
                </div>
            <div class="myclear" style="height:10px;"></div>

            <div class="mymember_page">
                <#if isOnSale>
                    <a href="javascript:onsaleAll();"  class="fl">下架选中的商品</a>
                <#else>
                    <a href="javascript:onsaleAll();" class="fl">上架选中的商品</a>
                </#if>
                
                <#if dis_goods_page??>
                <#assign continueEnter=false>
                <#if dis_goods_page.totalPages gt 0>
                    <#list 1..dis_goods_page.totalPages as page>
                        <#if page <= 3 || (dis_goods_page.totalPages-page) < 3 || (dis_goods_page.number+1-page)?abs<3 >
                            <#if page == dis_goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/goods/sale/${isOnSale?c}?page=${page-1}">${page}</a>
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
 
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
</form>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>