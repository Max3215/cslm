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
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />
<!--<link href="css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/distributor_goods.js"></script>
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

function supplyAll(){
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
    
    <#include "/client/common_distributor_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
      
         <div class="h30"></div>
         <div class="mymember_order_search"> 
            <h3>分销商的商品</h3>
            <form action="/distributor/supply/list" id="form">
                <input type="hidden" value="${page!'0'}" name="page"/>
                <input class="mysub" type="submit" value="查询" />
                <#--
                <p class="fr pl10 c3">价格&nbsp;&nbsp;<input type="text" style="width:50px;">&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" style="width:50px;"></p>
                <input class="mytext" type="text" onFocus="if(value=='批发商') {value=''}" onBlur="if (value=='') {value='批发商'}"  value="批发商" style="width:150px;" />
                -->
                <input class="mytext" type="text" name="keywords" value="${keywords!''}" id="keywords"/>
                <select  id="providerId" name="providerId" class="myselect" onchange="searchGoods()">
                    <option value="">选择分销商</option>
                    <#if provider_list??>
                        <#list provider_list as c>
                            <option value="${c.id?c}" <#if providerId?? && providerId==c.id>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
            </form>
            <div class="clear"></div>
        </div>
        <form  action="/distributor/supplyAll" method="post" id="form1">
           <#if providerId??><input type="hidden" value="${providerId?c}" name="providerId"/></#if>
           <input type="hidden" value="${page!'0'}" name="page"/>
           <input type="hidden" value="${keywords!''}" name="keywords"/>
           <div >
                <#include "/client/distributor_supply_goods.ftl">
          </div>
         </form>
          <div class="myclear" style="height:10px;"></div>
            <div class="mymember_page"> 
            <a href="javascript:supplyAll();" class="fl">代理选中的商品</a>
            <#if proGoods_page??>
                <#assign continueEnter=false>
                <#if proGoods_page.totalPages gt 0>
                    <#list 1..proGoods_page.totalPages as page>
                        <#if page <= 3 || (proGoods_page.totalPages-page) < 3 || (proGoods_page.number+1-page)?abs<3 >
                            <#if page == proGoods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/goods/list?keywords=${keywords!''}&page=${(page-1)?c}&providerId=${providerId!''}">${page}</a>
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
  <div class="myclear"></div>
</div>
<!--mymember END-->
    <#include "/client/common_footer.ftl">
    
</body>
</html>