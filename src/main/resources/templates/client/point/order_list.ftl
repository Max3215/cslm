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
<script type="text/javascript" src="/client/js/common.js"></script>
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>

<script src="/client/js/jquery.diysiteselect.js"></script>
<script src="/client/js/com.js"></script>
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
    <#include "/client/common_header.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

  <!-- 左侧 -->
  <#include "/client/common_user_menu.ftl" />
  
  <div class="mymember_mainbox">
    <form name="form1" action="/user/point/order/list" method="post">
        <input type="hidden" value="${page!'0'}" name="page" id="page">
        <input type="hidden" name="eventTarget" value="" id="eventTarget">
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
                <h3>积分兑换列表</h3>
                 <#--
                  时间
                        <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                   -->  &nbsp;&nbsp;
                    <input class="mysub" type="submit" value="查询" onclick="javascript:setTimeout(__doPostBack('submit',''), 0)" />
                      <input name="keywords" type="text" value="${keywords!''}" class="mytext"  >
                   <select name="statusId" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                        <option value="" <#if !statusId?? || statusId==0>selected="selected"</#if>>所有订单</option>
                        <option value="1" <#if statusId?? && statusId==1>selected="selected"</#if>>待发货</option>
                        <option value="2" <#if statusId?? && statusId==2>selected="selected"</#if>>待收货</option>
                        <option value="3" <#if statusId?? && statusId==3>selected="selected"</#if>>已完成</option>
                        <option value="4" <#if statusId?? && statusId==4>selected="selected"</#if>>已取消</option>
                    </select>
                    
            </div>
            <table id="list">
                <tr class="mymember_infotab_tit01">
                    <th> 订单信息</th>
                    <th width="70">用户</th>
                    <th width="80">兑换积分</th>
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
                <tr class="list">
                      <th colspan="7">
                        订单编号：<a href="/user/point/order/detail?id=${order.id?c}">${order.orderNumber!''}</a>
                      </th>
                  </tr>
                  <tr>
                      <td class="td001" >
                                <img src="${order.goodsImg!''}" width="50px;" height="50px;" style=" float: left;"/>
                                <p class="fr" style="width:170px;text-align:left;margin-left: 60px;height: 60px; float: left;"><span>${order.goodsTitle}</span></p>
                      </td>
                      <td>${order.username!''}</td>
                      <td>
                            <p>${order.point}</p>
                      </td>
                      <td class="td003">
                        <p>${order.createTime!''}</p>
                      </td>
                      <td>
                            <#if order.statusId?? && order.statusId == 1>     
                                 <p>待发货</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 2>
                                <p>待收货</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 3>
                                <p>已完成</p>
                             </#if>
                            <#if order.statusId?? && order.statusId == 4>
                                <p>已取消</p>
                            </#if>
                      </td>
                      <td class="td003"> 
                            <p><a href="/user/point/order/detail?id=${order.id?c}">查看</a></p>
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




  











