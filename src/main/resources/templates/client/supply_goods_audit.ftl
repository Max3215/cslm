<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>分销中心</title>
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
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    })

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

function search(){
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
  
    <#include "/client/common_supply_menu.ftl">
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
          <h3>待审核的商品</h3>
          <form action="/supply/goods/audit" id="form1">
              <input type="hidden" value="${page!'0'}" name="page">
              <input class="mysub" type="submit" value="查询"/>
              <input class="mytext" type="text"   value="${keywords!''}" name="keywords" id="keywords" />
              <select  id="categoryId" name="categoryId" class="myselect" onchange="search()">
                    <option value="">请选择类别...</option>
                    <#if category_list??>
                        <#list category_list as c>
                            <option value="${c.id?c}" <#if categoryId?? && categoryId==c.id>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
              </form>
          <div class="clear"></div>
        </div>
        <table>
          <tr class="mymember_infotab_tit01">
            <th width="330">商品名称</th>
            <th width="190">商品编码</th>
            <th>售价</th>
            <th>分销比例</th>
            <th>操作</th>
          </tr>
           <#if goods_page??>
                <#list goods_page.content as goods>
                    <tr id="tr_1424195166">
                        <td>
                            <a >
                                <strong><img width="80" height="80" src="${goods.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${goods.id?c}">${goods.goodsTitle!''}</p>
                            </a> 
                        </td>
                        <td class="tb01">${goods.code!''}</td>
                        <td class="tb02">￥${goods.outFactoryPrice?string('0.00')}</td>
                        <td>${goods.shopReturnRation?string('#.##')}</td>
                        <td>
                          <p><a>审核中…</a></p>
                         </td>
                      </tr>
                </#list>
           </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>

        <div class="mymember_page"> 
        <#if goods_page??>
                <#assign continueEnter=false>
                <#if goods_page.totalPages gt 0>
                    <#list 1..goods_page.totalPages as page>
                        <#if page <= 3 || (goods_page.totalPages-page) < 3 || (goods_page.number+1-page)?abs<3 >
                            <#if page == goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/supply/goods/audit?page=${page-1}&keywords=${keywords!''}&categoryId=${categoryId!''}">${page}</a>
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
    <#include "/client/common_footer.ftl">
</body>
</html>