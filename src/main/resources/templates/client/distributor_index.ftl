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
<!--<link href="css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  
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
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

  <#include "/client/common_distributor_menu.ftl">
  
  <div class="mymember_center add_width">
    <div class="mymember_info mymember_info01">
      <table>
        <tr>
          <th width="150" rowspan="2"><a class="mymember_header" ><img src="${distributor.imageUri!''}" height="150px;" width="150px;"/></a></th>
          <td><a ><img src="/client/images/mymember/buy02.png" />待发货：<span>${total_undelivered!'0'}</span></a></td>
          <td><a ><img src="/client/images/mymember/buy03.png" />待收货：<span>${total_unreceived!'0'}</span></a></td>
          <th rowspan="2" class="mymember_fen add_width">
            <a ><img src="/client/images/mymember/buy05.png" /><p>账户余额：<span>${distributor.virtualMoney?string('0.00')}</span></p></a>
          </th>
        </tr>
        <tr>
          <td><a ><img src="/client/images/mymember/buy04.png" />已完成：<span>${total_finished!'0'}</span></a></td>
        </tr>
      </table>
    </div>
    
    <div class="mymember_info mymember_info02">
        <h3>我的进货单<a href="/distributor/inOrder/list/0">查看全部进货单</a></h3>
        <table width="100%">    
            <#if dis_goodsIn_order_page?? && dis_goodsIn_order_page.content??>
            <#list dis_goodsIn_order_page.content as order>
                <#if order_index < 3 >
                    <tr>
                        <th colspan="7">进货单编号：<a href="/distributor/order?id=${order.id?c}&type=inOrder">${order.orderNumber!''}</a></th>
                    </tr>
                    <tr>
                        <td align="left"  colspan="2">
                            <#if order.orderGoodsList??>
                            <#list order.orderGoodsList as og>  
                                <#if og_index < 1>
                                    <a><img src="${og.goodsCoverImageUri!''}"   width="50" height="50px" align="left" /></a>
                                </#if>
                            </#list>
                            </#if>
                        </td>
                        <td>
                            ${order.shopTitle!''}
                        </td>
                        <td>
                            <p>￥${order.totalPrice?string("0.00")}</p>
                        </td>
                        <td class="td003">
                            <p>${order.orderTime?string('yyyy-MM-dd')}</p>
                            <p>${order.orderTime?string('HH:mm:ss')}</p>
                        </td>
                        <td>
                            <#if order.statusId?? && order.statusId==1>
                                   <p>待发货</p>
                            <#elseif order.statusId?? &&  order.statusId==2>
                                   <p>待收货</p>
                            <#elseif order.statusId?? &&  order.statusId==3>
                                   <p>已完成</p>
                            </#if>
                        </td>
                        <td class="td003">
                            <a href="/distributor/order?id=${order.id?c}&type=inOrder">查看</a>          
                        </td>
                    </tr>
                </#if>
            </#list>
            </#if>
        </table>
    </div> 


    <div class="mymember_info mymember_info02">
      <h3>销售订单</h3>
      <table width="100%">  
            <#if dis_goodsOut_order_page?? && dis_goodsOut_order_page.content??>
                <#list dis_goodsOut_order_page.content as order>
                    <#if order_index < 3 >         
                        <tr>
                            <th colspan="7">订单编号：<a href="/distributor/order?id=${order.id?c}">${order.orderNumber!''}</a></th>
                        </tr>
                        <tr>
                            <td align="left"  colspan="2">
                                <#if order.orderGoodsList??>
                                    <#list order.orderGoodsList as og>  
                                        <#if og_index < 1>
                                            <a ><img src="${og.goodsCoverImageUri!''}"   width="50" height="50px" align="left" /></a>
                                        </#if>
                                    </#list>
                                </#if>
                            </td>
                            <td>
                                ${order.shippingName!''}
                            </td>
                            <td>
                                <p>￥${order.totalPrice?string("0.00")}</p>
                            </td>
                            <td class="td003">
                                <p>${order.orderTime?string('yyyy-MM-dd')}</p>
                                <p>${order.orderTime?string('HH:mm:ss')}</p>
                            </td>
                            <td>
                                <#if order.statusId?? && order.statusId==2>
                                      <p>待付款</p>
                                <#elseif order.statusId?? &&  order.statusId==3>
                                       <p>待发货</p>
                                <#elseif order.statusId?? &&  order.statusId==4>
                                       <p>待收货</p>
                                <#elseif order.statusId?? &&  order.statusId==5>
                                       <p>待评价</p>
                                <#elseif order.statusId?? &&  order.statusId==6>
                                       <p>已完成</p>
                                <#elseif order.statusId?? &&  order.statusId==7>
                                       <p>已取消</p>
                                       
                                </#if>
                            </td>
                            <td class="td003">
                                 <a href="/distributor/order?id=${order.id?c}">查看</a>          
                            </td>
                         </tr>
                    </#if>
                </#list>
            </#if>            
           
      </table>
    </div>  
    
  </div>
  <div class="mymember_hot">
    <ul class="market_info">
      <p class="tit">超市信息</p>
      <div class="h10"></div>
      <li>超市名称：${distributor.title!''}</li>
      <li>地址：${distributor.address!''}</li>
      <li>付款方式：${distributor.paytype!''}</li>
      <li>营业时间：${distributor.openTime!''}</li>
      <li>钱包账号：${distributor.virtualAccount!''}</li>
      <li>服务电话：${distributor.serviceTele!''}</li>
      <a href="/distributor/password" class="quit_btn" >修改密码</a><a href="/logout" class="quit_btn">退出登陆</a>
    </ul>
  </div>

<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">
</body>
</html>




  











