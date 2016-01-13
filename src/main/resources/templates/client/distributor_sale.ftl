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

<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
<link href="/mag/style/pagination.css" rel="stylesheet" type="text/css">
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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
    <!-- 左侧菜单 -->
     <#include "/client/common_distributor_menu.ftl" />
     
     
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
          <h3>已售的商品</h3>
          <form action ="/distributor/sale">
              <input type="hidden" value="${page!'0'}" name="page">
              <input class="mysub" type="submit" value="查询" />
              <input class="mytext" type="text"  name="keywords" value="${keywords!''}"/>
              <p class="fr pl10 c3">时间&nbsp;&nbsp;
                    <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
                                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyyy-MM-dd')}</#if>" class="input date" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'zh-cn'})">
               </p>
              <div class="clear"></div>
          </form>
        </div>
        <table>
          <tr class="mymember_infotab_tit01">
                <th width="300">商品名称</th>
                <th>商品编码</th>
                <th>数量</th>
                <th>价格</th>
                <th>售出时间</th>
          </tr>
          <#if orderGoodsPage??>
               <#list orderGoodsPage.content as og>
                    <tr id="tr_1424195166">
                        <td>
                            <a>
                                <strong><img width="80" height="80" src="${og.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${og.goodsTitle!''}</p>
                            </a>
                        </td>
                        <td><#if og.code??>${og.code!''}</#if></td>
                        <td class="tb01">${og.quantity!'0'}</td>
                        <td class="tb02">￥${og.price?string('0.00')}</td>
                        <td><#if og.saleTime??>${og.saleTime?string('0.00')}</#if></td>
                        </td>
                    </tr>
                </#list>
           </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>

        <div class="mymember_page"> 
            <#if orderGoodsPage??>
                <#assign continueEnter=false>
                <#if orderGoodsPage.totalPages gt 0>
                    <#list 1..orderGoodsPage.totalPages as page>
                        <#if page <= 3 || (orderGoodsPage.totalPages-page) < 3 || (orderGoodsPage.number+1-page)?abs<3 >
                            <#if page == orderGoodsPage.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/sale?page=${page-1}">${page}</a>
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
      <!--mymember_info END-->

    </div>
    <!--mymember_center END-->
 
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