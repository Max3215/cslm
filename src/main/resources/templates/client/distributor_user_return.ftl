<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css" />
<link href="/client/css/main.css" rel="stylesheet" type="text/css" />
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<!--  后台文件  -->
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
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#include "/client/common_distributor_menu.ftl" />
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
            <a class="a001" href="/user/return/list">退货列表</a>
            <div class="clear"></div>
        </div>
        
        <table align="left">
            <tr class="mymember_infotab_tit01">
                <th colspan="2">商品信息</th>
                <th width="60">价格*数量</th>
                <th width="60">退款</th>
                <th width="120">订单号</th>
                <th width="90">用户名</th>
                <th width="120">申请时间</th>
                <th width="50">状态</th>
                <th width="50">操作</th>
            </tr>
            <#if return_page??>
                <#list return_page.content as return>
                    <tr>
                      <td class="td001" width="70">
                        <a><img src="${return.goodsCoverImageUri!''}" width="60" height="60"></a>
                      </td>
                      <td class="td002">
                        <a>${return.goodsTitle!''}</a>
                      </td>
                      <td>${return.goodsPrice?string('0.00')}*${return.returnNumber!''}</td>
                      <td><#if return.realPrice??>${return.realPrice?string('0.00')}</#if></td>
                      <td>${return.orderNumber!''}</td>
                      <td class="td003">${return.username!''}</td>
                      <td class="td003">${return.returnTime!''}</td>
                      <td >
                            <#if return.turnType?? && return.turnType ==2>
                            <#switch return.statusId>
                                <#case 0>新提交<#break>
                                <#case 1>已同意<#break>
                                <#case 2>已拒绝<#break>
                                <#case 3>分销商已同意<#break>
                                <#case 4>分销商已拒绝<#break>
                            </#switch>
                            <#else>
                                <#switch return.statusId>
                                    <#case 0>新提交<#break>
                                    <#case 1>已同意<#break>
                                    <#case 2>已拒绝<#break>
                                </#switch>
                            </#if>
                       </td>
                       <td><a href="/distributor/user/returnDetail?id=${return.id?c}">详情</a></td>
                    </tr>
                </#list>
            </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>
        <div class="mymember_page">
            <#if return_page??>
                <#assign continueEnter=false>
                <#if return_page.totalPages gt 0>
                    <#list 1..return_page.totalPages as page>
                        <#if page <= 3 || (return_page.totalPages-page) < 3 || (return_page.number+1-page)?abs<3 >
                            <#if page == return_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/return/list?page=${page-1}">${page}</a>
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
    <!--mymember_center END-->
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>

    <#include "/client/common_footer.ftl">
</body>
</html>


