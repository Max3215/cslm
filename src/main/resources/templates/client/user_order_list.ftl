<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />


<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<!--<link href="css/member.css" rel="stylesheet" type="text/css" />-->
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

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

    <!--  顶部  -->
    <#include "/client/common_header.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">

  <!-- 左侧 -->
  <#include "/client/common_user_menu.ftl" />
  
  <div class="mymember_mainbox">
    <div class="mymember_info mymember_info03">
      <h3>全部订单<a class="a002" href="#">在线客服 >> </a></h3>

    </div><!--mymember_info END-->
    <form name="form1" action="/user/order/list/${status_id}" method="POST">
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
        <div class="mymember_info mymember_info02">
            <div class="mymember_order_search">
                <a class="a001" >全部订单</a>
            <#--
            <input class="mysub" type="submit" value="查询" />
            <input class="mytext" type="text" onFocus="if(value=='商品名称、订单编号') {value=''}" onBlur="if (value=='') {value='商品名称、订单编号'}"  value="商品名称、订单编号" />
            -->
                <div class="clear"></div>
            </div>
            <table>
                <tr class="mymember_infotab_tit01">
                    <th>订单信息</th>
                    <th width="70">收货人</th>
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
                            <option value="1" <#if status_id==1>selected="selected"</#if>>待确认</option>
                            <option value="2" <#if status_id==2>selected="selected"</#if>>待付款</option>
                            <option value="3" <#if status_id==3>selected="selected"</#if>>待发货</option>
                            <option value="4" <#if status_id==4>selected="selected"</#if>>待收货</option>
                            <option value="5" <#if status_id==5>selected="selected"</#if>>待评价</option>
                            <option value="6" <#if status_id==6>selected="selected"</#if>>已完成</option>
                            <option value="7" <#if status_id==7>selected="selected"</#if>>已取消</option>
                            <option value="8" <#if status_id==8>selected="selected"</#if>>支付取消</option>                              
                        </select>
                    </th>
                    <th width="60">操作</th>
                </tr>
                <#if order_page??>
                <#list order_page.content as order>
                <tr>
                      <th colspan="7">订单编号：<a href="/user/order?id=${order.id?c}">${order.orderNumber!''}</a></th>
                  </tr>
                  <tr>
                      <td class="td001">
                          <#list order.orderGoodsList as og>
                                <a href="/goods/${og.goodsId?c}"><img src="${og.goodsCoverImageUri!''}" width="50px;" alt="${og.goodsTitle!''}"/></a>
                          </#list>
                      </td>
                      <td>${order.shippingName!''}</td>
                      <td>
                            <p>￥${order.totalGoodsPrice?string('0.00')}</p>
                      </td>
                      <td class="td003">
                        <p>${order.orderTime!''}</p>
                      </td>
                      <td>
                            <#if order.statusId?? && order.statusId == 1>     
                               <p>待确认</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 2>
                                <p>待付款</p>
                                <a href="/order/dopayleft/${order.id?c}">去支付</a>
                            </#if>
                            <#if order.statusId?? && order.statusId == 3>
                                <p>待发货</p>
                             </#if>
                            <#if order.statusId?? && order.statusId == 4>
                                <p>待收货</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 5>
                                <p>待评价</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 6>
                                <p>已完成</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 7>
                                <p>已取消</p>
                            </#if>
                            <#if order.statusId?? && order.statusId == 8>
                                <p>支付取消（失败）</p>
                            </#if> 
                      </td>
                      <td class="td003"> 
                            <p><a href="/user/order?id=${order.id?c}">查看</a></p>
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
                                <a href="/user/order/list/${statusId}?page=${page-1}&timeId=${time_id}&keywords=${keywords!''}">${page}</a>
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
    
    <div class="mymember_info">
        <h3 id="mymember_likecheck">
            
            <span>猜你喜欢</span>
        </h3>
        <ul id="mymember_likelist">
                <li>
            <#if hot_list?? && hot_list?size gt 0> 
                <#list hot_list as goods> 
                    <#if goods_index < 4 >
                      <a href="${goods.id?c}">
                        <img src="${goods.coverImageUri!''}" width="208px" height="208px;"/>
                        <p>${goods.title!''}</p>
                        <p>￥<span>${goods.salePrice?string('0.00')}</span></p>
                        <i>已售 ${goods.soldNumber!'0'} 件</i>
                      </a>
                    </#if>
                </#list>
            </#if>
                </li>
        </ul>
        <div class="myclear"></div>
    </div>
  </div>
  
    <div class="myclear"></div>
    <#if user_bottom_ad_list??>
    <#list user_bottom_ad_list as ad>
        <a href="${ad.linUri!''}"><img src="${ad.fileUri!''}" width="388" height="120" /></a>
    </#list>
    </#if>
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




  











