<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>批发中心</title>
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
<script src="/client/js/supply_goods.js"></script>
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

// 取消批发多个商品
function deleteCheck(){
    $("#form").submit();
}  

function searchGoods(){
    $("#form1").submit();
}

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
       <#include "/client/common_supply_menu.ftl">
    
        <div class="mymember_mainbox">
            <div class="mymember_info mymember_info02">
                <div class="mymember_order_search"> 
                    <h3><#if isDistribution?? && isDistribution>分销中的商品<#else>仓库中的商品</#if></h3>
                      <form action="/supply/goods/list/${isDistribution?c}" id="form1">
                            <input type="hidden" value="${page!'0'}" name="page"/>
                            <input class="mysub" type="submit" value="查询" />
                            <input class="mytext" type="text"  value="${keywords!''}" id="keywords"/>
                            <select  id="categoryId" name="categoryId" class="myselect" onchange="searchGoods()">
                                <option value="">请选择类别...</option>
                                <#if category_list??>
                                    <#list category_list as c>
                                        <option value="${c.id?c}" <#if categoryId?? && categoryId==c.id>selected="selected"</#if>>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                        </form>
                    <div class="clear"></div>
                </div>
                
                <form action="/supply/goods/checkAll/${isDistribution?c}" method="post" id="form">
                <input type="hidden" name="page" value="${page!'0'}"/>
                <input type="hidden" name="categoryId" value="${categoryId!''}"/>
                <input type="hidden" name="keywords" value="${keywords!''}"/>
                <div id="dis_goods_list">
                    <#include "/client/supply_goods_list.ftl">
                </div>
                </form>
            <div class="myclear" style="height:10px;"></div> 

            <div class="mymember_page">
             <#if isDistribution>
                <a href="javascript:deleteCheck();"  class="fl">取消分销商品</a>
            <#else>
                <a href="javascript:deleteCheck();"  class="fl">分销选中的商品</a>
            </#if>             
                <#if supply_goods_page??>
                <#assign continueEnter=false>
                <#if supply_goods_page.totalPages gt 0>
                    <#list 1..supply_goods_page.totalPages as page>
                        <#if page <= 3 || (supply_goods_page.totalPages-page) < 3 || (supply_goods_page.number+1-page)?abs<3 >
                            <#if page == supply_goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/supply/goods/list/${isDistribution?c}?page=${page-1}">${page}</a>
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
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>