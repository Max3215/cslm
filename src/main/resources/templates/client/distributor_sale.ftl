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
   // adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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
          <form action ="/distributor/sale" method="POST" id="form1">
              <select name="shipAddressId" onchange="javascript:__doPostBack('shipAddressId',0);" style=" width: 220px;">
                    <option value="" >所有地址</option>
                    <#if addressList??>
                    <#list addressList as addr>
                    	<option value="${addr.id?c}" <#if shipAddressId?? &&shipAddressId ==addr.id>selected="selected"</#if>>${addr.detailAddress!''}</option>
                    </#list>
                    </#if>
                </select>
              		&nbsp;时间&nbsp;&nbsp;
                    <input name="startTime" type="text" value="<#if startTime??>${startTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
                                    &nbsp;&nbsp;至&nbsp;&nbsp;
                    <input name="endTime" type="text" value="<#if endTime??>${endTime?string('yyyy-MM-dd HH:mm')}</#if>" class="input date" style="height:25px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',lang:'zh-cn'})">
               
              <select name="statusId" onchange="javascript:__doPostBack('statusId',0);" >
                    <option value="" <#if !status_id?? || status_id==0>selected="selected"</#if>>所有状态</option>
                    <option value="1" <#if status_id==1>selected="selected"</#if>>待确认</option>
                    <option value="2" <#if status_id==2>selected="selected"</#if>>待付款</option>
                    <option value="3" <#if status_id==3>selected="selected"</#if>>待发货</option>
                    <option value="4" <#if status_id==4>selected="selected"</#if>>待收货</option>
                    <option value="5" <#if status_id==5>selected="selected"</#if>>待评价</option>
                    <option value="6" <#if status_id==6>selected="selected"</#if>>已完成</option>
                    <option value="7" <#if status_id==7>selected="selected"</#if>>已取消</option>
                </select>
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
              <input class="mysub" type="button" value="导出本页" name="excel" onclick="javascript:setTimeout(__doPostBack('excel',''), 0)"/>
              <input class="mysub" type="submit" value="查询" />
            </form>
        </div>
        <table>
          <tr class="mymember_infotab_tit01">
                <th width="200">商品名称</th>
                <th width="200">副标题</th>
                <th>商品编码</th>
                <th>数量</th>
                <th>售价</th>
                <th>销售额价</th>
          </tr>
          <#if saleList??>
               <#list saleList as item>
                    <tr id="tr_1424195166">
                        <td>
                            <a>
                                <p style="text-align: left;margin:10px 0 10px 5px;max-height:60px;overflow:hidden;">${item.goodsTitle!''}</p>
                            </a>
                        </td>
                        <td><p style="text-align: left;margin:10px 0 10px 5px;max-height:60px;overflow:hidden;" >${item.subTitle!''}</p></td>
                        <td><#if item.goodsCode??>${item.goodsCode!''}</#if></td>
                        <td class="tb01">${item.quantity!'0'}</td>
                        <td class="tb02">￥${item.price?string('0.00')}</td>
                        <td class="tb02">￥${item.totalPrice?string('0.00')}</td>
                        </td>
                    </tr>
                </#list>
           </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>

        <div class="mymember_page"> 
<#--
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
        -->
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