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

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>

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

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

  <!-- 左侧 -->
  <#include "/client/common_distributor_menu.ftl" />
  
  <div class="mymember_mainbox">
    <form name="form1" action="/distributor/inOrder/list" method="POST">
        <input type="hidden" name="eventTarget" value="" id="eventTarget">
        <input type="hidden" value="${page!'0'}" name="page" id="page">
        <script type="text/javascript">
            var theForm = document.forms['form1'];
            if (!theForm) {
                theForm = document.form1;
            }
            function __doPostBack(eventTarget, eventArgument) {
                if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                    if(eventTarget=='page'){
                        theForm.page.value = eventArgument;
                    }else{
                        theForm.page.value = 0;
                        theForm.eventTarget.value = eventTarget;
                    }
                    theForm.submit();
                }
            }
        </script>
        <div class="mymember_info mymember_info02">
            <div class="mymember_order_search">
                <h3>进货订单</h3>
                    <#assign totalPrice =0.0>
                    <#if orderList??>
                    <#list orderList as order>
                        <#assign totalPrice = totalPrice+order.totalPrice>
                    </#list>
                    </#if>
                  时间&nbsp;&nbsp;
                        <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                   <select name="statusId" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                            <option value="0" <#if !statusId?? || statusId==0>selected="selected"</#if>>所有订单</option>
                            <option value="3" <#if statusId==3>selected="selected"</#if>>待发货</option>
                            <option value="4" <#if statusId==4>selected="selected"</#if>>待收货</option>
                            <option value="6" <#if statusId==6>selected="selected"</#if>>已完成</option>
                    </select>
                    共计订单：<#if orderList??>${orderList?size}<#else>0</#if>个,订单总金额：￥${totalPrice?string('0.00')}
                <input class="mysub" type="submit" value="导出本页" name="excel" onclick="javascript:setTimeout(__doPostBack('excel',''), 0)"/>
                <input class="mysub" type="submit" value="导出全部" name="excel" onclick="javascript:setTimeout(__doPostBack('excelAll',''), 0)"/>
                <input class="mysub" type="submit" value="查询" onclick="javascript:setTimeout(__doPostBack('submit',''), 0)" />
            </div>
            <table>
                <tr class="mymember_infotab_tit01">
                    <th>订单信息</th>
                    <th width="70">批发商</th>
                    <th width="80">订单金额</th>
                    <th width="80">
                        订单时间
                    </th>
                    <th width="80">
                        订单状态
                    </th>
                    <th width="60">操作</th>
                </tr>
                <#if order_page??>
                <#list order_page.content as order>
                <tr>
                      <th colspan="7">订单编号：<a href="/distributor/order?id=${order.id?c}&type=inOrder">${order.orderNumber!''}</a></th>
                  </tr>
                  <tr>
                      <td class="td001">
                          <#list order.orderGoodsList as og>
                          <#if og_index lt 7>
                                <a href=""><img src="${og.goodsCoverImageUri!''}" width="50px;" height="50px;" alt="${og.goodsTitle!''}"/></a>
                          </#if>
                          </#list>
                      </td>
                      <td>${order.shopTitle!''}</td>
                      <td>
                            <p>￥${order.totalGoodsPrice?string('0.00')}</p>
                      </td>
                      <td class="td003">
                        <p>${order.orderTime!''}</p>
                      </td>
                      <td>
                            <#if order.statusId?? && order.statusId == 3>     
                               <p>待发货</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 4>
                                <p>待收货</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 6>
                                <p>已完成</p>
                             </#if>
                      </td>
                      <td class="td003"> 
                            <p><a href="/distributor/order?id=${order.id?c}&type=inOrder">查看</a></p>
                      </td>
                  </tr>
                  </#list>
                  </#if>
            </table>
            <div class="myclear" style="height:10px;"></div>
            <div class="mymember_page">
            <#if order_page??>
                <#assign continueEnter=false>
                <#if order_page.totalPages gt 0>
                    <#list 1..order_page.totalPages as page>
                        <#if page <= 3 || (order_page.totalPages-page) < 3 || (order_page.number+1-page)?abs<3 >
                            <#if page == order_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="javascript:;" onclick="javascript:setTimeout(__doPostBack('page',${(page-1)?c}), 0)">${page}</a>
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
        </div><!--mymember_info END-->
    </form>
    
  </div>
  
    <div class="myclear"></div>
    <div class="myclear"></div>  
</div><!--mymember_main END-->
<div class="myclear"></div>
</div>
<!--mymember END-->

<div class="clear"></div>

    <!--底部footer-->
    <#include "/client/common_footer.ftl" />
</body>
</html>




  











