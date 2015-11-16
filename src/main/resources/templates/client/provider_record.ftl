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

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果


  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})

function subRecord(){
    $("#form").submit();
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
        <#include "/client/common_provider_menu.ftl">
    <div class="mymember_mainbox">    
    <div class="mymember_info mymember_info02">
        <div class="mymember_order_search">
                交易记录  
                <form action="/provider/pay/record" id="form">
                    <input type="hidden" value="${page!'0'}" name="page"/>
                    <select  id="cont" name="cont" class="myselect" onchange="subRecord()">
                        <option value="">全部记录</option>
                        <option value="充值" <#if cont?? && cont=="充值">selected="selected"</#if>>充值记录</option>
                        <option value="提现" <#if cont?? && cont=="提现">selected="selected"</#if>>提现记录</option>
                        <option value="销售" <#if cont?? && cont=="销售">selected="selected"</#if>>销售记录</option>
                        <option value="批发" <#if cont?? && cont=="批发">selected="selected"</#if>>进货记录</option>
                    </select>
               </form>
            <div class="clear"></div>
        </div>
        <table style="table-layout: fixed;">
            <tr class="mymember_infotab_tit01">     
                  <th>订单编号</th>
                  <th>交易时间</th>
                  <th>总金额</th>
                  <th>说明</th>
            </tr>     
            <#if pay_record_page?? && pay_record_page.content??>
                <#list pay_record_page.content as re>
                    <tr>
                          <td >${re.orderNumber!''}</td>
                          <td class="td003">
                            <p>${re.createTime?string('yyyy-MM-dd')}</p>
                            <p>${re.createTime?string('HH:mm:ss')}</p>
                          </td>
                          <td>￥${re.provice?string('0.00')}</td>
                          <td>${re.cont}</td>         
                    </tr>
                </#list>
            </#if>
      </table>
      <div class="myclear" style="height:10px;"></div>
      <div class="mymember_page">
        <#if pay_record_page??>
            <#assign continueEnter=false>
            <#if pay_record_page.totalPages gt 0>
                <#list 1..pay_record_page.totalPages as page>
                    <#if page <= 3 || (pay_record_page.totalPages-page) < 3 || (pay_record_page.number+1-page)?abs<3 >
                        <#if page == pay_record_page.number+1>
                            <a class="mysel" href="javascript:;">${page}</a>
                        <#else>
                            <a href="/provider/pay/record?page=${page-1}">${page}</a>
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
    
  </div>
    <!--mymember_center END-->
 
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