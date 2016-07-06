<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>批发中心</title>
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

function editgoods(gid){
    $("#goodsId").attr("value",gid);
    var goodsTitle = $("#title"+gid).html();
    var subTitle = $("#subTitle"+gid).val();
    var unit = $("#unit"+gid).val();
    var code = $("#code"+gid).html();
    var marketPrice = $("#marketPrice"+gid).html();
    
    $("#code").attr("value",code);
    $("#marketPrice").attr("value",marketPrice);
    $("#goodsTitle").attr("value",goodsTitle);
    $("#subTitle").attr("value",subTitle);
    $("#unit").attr("value",unit);
    $('.sub_form').css('display','block');
}

function subDisGoods(){
    var goodsId = $("#goodsId").val();
    var goodsTitle = $("#goodsTitle").val();
    var subTitle = $("#subTitle").val();
    var outFactoryPrice = $("#outFactoryPrice").val();
    var marketPrice = $("#marketPrice").val();
    var leftNumber = $("#leftNumber").val();
    var shopReturnRation = $("#shopReturnRation").val();
    var unit = $("#unit").val();
    
    if(undefined == goodsTitle || ""==goodsTitle)
    {
        alert("请输入商品标题");
        return;
    }
     var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if(undefined == marketPrice || ""==marketPrice || !reg.test(marketPrice))
    {
        alert("请输入商品市场价");
        return ;
    }
    
    if(undefined == outFactoryPrice || ""==outFactoryPrice || !reg.test(outFactoryPrice))
    {
        alert("请输入商品分销价");
        return ;
    }
    
    if(undefined == leftNumber || ""==leftNumber || isNaN(leftNumber))
    {
        alert("请输入库存数量");
        return;
    }
    
    if(undefined == shopReturnRation || ""==shopReturnRation || isNaN(shopReturnRation))
    {
        alert("请输入分销比例");
        return;
    }
    
    var ration = /(^[0]+(.[0-9]{2})?$)/;
    if("" != shopReturnRation && !ration.test(shopReturnRation)){
        alert("输入正确的分销比例，如0.01。。。");
        return ;
    }
    
    $.ajax({
        type : "post",
        url : "/supply/distribution",
        data : {"goodsId":goodsId,
            "goodsTitle":goodsTitle,
            "subTitle":subTitle,
            "outFactoryPrice":outFactoryPrice,
            "marketPrice":marketPrice,
            "leftNumber":leftNumber,
            "unit":unit,
            "shopReturnRation":shopReturnRation},
        dataType : "json",
        success:function(data){
            $('.sub_form').css('display','none');
            alert(data.msg);
        }
    })
    
}
function search(type){
    if(null != type && type=="oneCat")
    {
        $("#categoryId").attr("value",$("#oneCat").val());
    }else if(null != type && type=="twoCat")
    {
       $("#categoryId").attr("value",$("#twoCat").val());
    }else if(null != type && type=="categoryId")
    {
        $("#categoryId").attr("value",$("#category").val());
    }
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
          <h3>平台的商品</h3>
          <form action="/supply/goods/distribution" id="form1">
              <input type="hidden" name="categoryId" id="categoryId" value="<#if category??>${category.id?c}</#if>" />
              <input class="mysub" type="submit" value="查询"/>
              <input class="mytext" type="text"   value="${keywords!''}" name="keywords" id="keywords" />
              <select id="oneCat" onchange="javascript:search('oneCat')">
                    <option <#if category??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if category_list??>
                        <#list category_list as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
                 <select id="twoCat" onchange="javascript:search('twoCat')">
                    <option <#if category??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if cateList??>
                        <#list cateList as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
                 <select id="category" onchange="javascript:search('categoryId')">
                    <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if categoryList??>
                        <#list categoryList as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
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
            <th>市场价</th>
            <th>操作</th>
          </tr>
           <#if goods_page??>
                <#list goods_page.content as goods>
                    <tr id="tr_1424195166">
                        <input type="hidden" id="subTitle${goods.id?c}" value="${goods.subTitle!''}">
                        <input type="hidden" id="unit${goods.id?c}" value="${goods.unit!''}">
                        <td><a class="pic"><strong><img width="80" height="80" src="${goods.coverImageUri!''}"  /></strong><p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${goods.id?c}">${goods.title!''}</p></a> </td>
                        <td class="tb01"><span id="code${goods.id?c}">${goods.code!''}</span></td>
                        <td class="tb02"><p>￥<span id="marketPrice${goods.id?c}">${goods.marketPrice?string('0.00')}</span></p></td>
                        <td>
                          <p><a href="javascript:;"  onclick="editgoods(${goods.id?c});">我要分销</a></p>
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
                                <a href="/supply/goods/distribution?page=${page-1}&keywords=${keywords!''}<#if categoryId??>&categoryId=${categoryId?c!''}</#if>">${page}</a>
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

  <!-- 点击商品上架后弹出层 -->
  <aside class="sub_form">
    <p class="tit">商品分销<a href="javascript:void(0);" onclick="$('.sub_form').css('display','none')">×</a>
</p>
    <div class="info_tab">
      <table>
        <tr>
           <p> 编辑分销价格和库存：</p>
            <input type="hidden" id="goodsId" name="goodsId"/>
        </tr>
        <tr>
          <th>*商品名称：</th>
          <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
        </tr>
        <tr>
          <th>*商品副标题：</th>
          <td><input type="text" class="add_width" name="subTitle" id="subTitle" ></td>
        </tr>
        <tr>
          <th>商品编码：</th>
          <td><input type="text" class="add_width" name="code" id="code" readonly="readonly"></td>
        </tr>
        <tr>
          <th>商品市场价：</th>
          <td><input type="text" name="marketPrice" id="marketPrice" ></td>
        </tr>
         <tr>
          <th>*商品售价：</th>
          <td><input type="text" name="outFactoryPrice" id="outFactoryPrice" >&emsp;单位：<input type="text" name="unit" id="unit"></td>
        </tr>
         <tr>
          <th>*库存：</th>
          <td><input type="text" name="leftNumber" id="leftNumber"></td>
        </tr>
         <tr>
          <th>*分销比例：</th>
          <td><input type="text" name="shopReturnRation" id="shopReturnRation"></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" onclick="subDisGoods();" value="确认提交"></td>
        </tr>
      </table>
    </div>
  </aside>
</body>
</html>