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
 //   adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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
    <form name="form1" action="/distributor/inOrder/list/${status_id}" method="POST">
        <input type="hidden" name="eventTarget" value="" id="eventTarget">
        <script type="text/javascript">
            var theForm = document.forms['form1'];
            if (!theForm) {
                theForm = document.form1;
            }
            function __doPostBack(eventTarget, eventArgument) {
                if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                    theForm.eventTarget.value = eventTarget;
                    theForm.submit();
                }
            }
        </script>
        <div class="mymember_info mymember_info02">
            <div class="mymember_order_search">
                <a class="a001" >进货订单</a>
                <input class="mysub" type="submit" value="导出本页" name="excel" onclick="javascript:setTimeout(__doPostBack('excel',''), 0)"/>
            <#--
            <input class="mysub" type="submit" value="查询" />
            <input class="mytext" type="text" onFocus="if(value=='商品名称、订单编号') {value=''}" onBlur="if (value=='') {value='商品名称、订单编号'}"  value="商品名称、订单编号" />
            -->
                <div class="clear"></div>
            </div>
            <table>
                <tr class="mymember_infotab_tit01">
                    <th>订单信息</th>
                    <th width="70">批发商</th>
                    <th width="80">订单金额</th>
                    <th width="80">
                        <select name="timeId" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                            <option value="0" <#if !time_id?? || time_id==0>selected="selected"</#if>>所有订单</option>
                            <option value="1" <#if time_id==1>selected="selected"</#if>>最近一个月</option>
                            <option value="3" <#if time_id==3>selected="selected"</#if>>最近三个月</option>
                            <option value="6" <#if time_id==6>selected="selected"</#if>>最近半年</option>
                            <option value="12" <#if time_id==12>selected="selected"</#if>>最近一年</option>                              
                        </select>
                    </th>
                    <th width="80">
                        <select name="statusid" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                            <option value="0" <#if !status_id?? || status_id==0>selected="selected"</#if>>所有订单</option>
                            <option value="3" <#if status_id==1>selected="selected"</#if>>待发货</option>
                            <option value="4" <#if status_id==2>selected="selected"</#if>>待收货</option>
                            <option value="6" <#if status_id==3>selected="selected"</#if>>已完成</option>
                        </select>
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
                                <a href=""><img src="${og.goodsCoverImageUri!''}" width="50px;" height="50px;" alt="${og.goodsTitle!''}"/></a>
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
                                <a href="/distributor/inOrder/list/${statusId}?page=${page-1}&timeId=${time_id}">${page}</a>
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




  











