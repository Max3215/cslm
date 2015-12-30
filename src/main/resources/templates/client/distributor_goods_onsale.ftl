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
    
     $("#form").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 $('.sub_form').css('display','none');
                 window.location.href="/distributor/goods/onsale"
            }
        }
    });

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
    var goodsPrice = $("#price"+gid).html();
    var subTitle = $("#subTitle"+gid).val();
    var code = $("#code"+gid).val();
    
    $("#goodsTitle").attr("value",goodsTitle);
    $("#goodsPrice").attr("value",goodsPrice);
    $("#subTitle").attr("value",subTitle);
    $("#code").attr("value",code);
    $('.sub_form').css('display','block');
}

function subDisGoods(){
    var goodsId = $("#goodsId").val();
    var goodsTitle = $("#goodsTitle").val();
    var goodsPrice = $("#goodsPrice").val();
    var leftNumber = $("#leftNumber").val();
    var goodsMarketPrice = $("#goodsMarketPrice").val();
    var unit = $("#unit").val();
    
    if(undefined == goodsTitle || ""==goodsTitle)
    {
        alert("请输入商品标题");
        return;
    }
     var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if(undefined == goodsMarketPrice || ""==goodsMarketPrice || !reg.test(goodsMarketPrice))
    {
        alert("请输入商品市场价");
        return ;
    }
    
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
        alert("请输入商品销售价");
        return ;
    }
    
    
    if(undefined == leftNumber || ""==leftNumber || isNaN(leftNumber)|| 0 >= leftNumber)
    {
        alert("请输入库存数量");
        return;
    }
    
    $.ajax({
        type : "post",
        url : "/distributor/goodsOnsale",
        data : {"goodsId":goodsId,
            "goodsTitle":goodsTitle,
            "goodsPrice":goodsPrice,
            "leftNumber":leftNumber,
            "goodsMarketPrice":goodsMarketPrice,
            "unit":unit},
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
  
    <#include "/client/common_distributor_menu.ftl">
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
          <h3>平台中的商品</h3>
            <form action="/distributor/goods/onsale" id="form1">
              <input type="hidden" value="${page!'0'}" name="page">
              <input type="hidden" name="categoryId" id="categoryId" value="<#if category??>${category.id?c}</#if>" />
              <input class="mysub" type="submit" value="查询"/>
              <input class="mytext" type="text"   value="${keywords!''}" name="keywords" id="keywords" />
                 <select id="oneCat" onchange="javascript:search('oneCat')" >
                    <option <#if category??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if category_list??>
                        <#list category_list as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id+"]")>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
                 <select id="twoCat" onchange="javascript:search('twoCat')">
                    <option <#if category??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if cateList??>
                        <#list cateList as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id+"]")>selected="selected"</#if>>${c.title!""}</option>
                        </#list>
                    </#if>
                </select>
                 <select id="category" onchange="javascript:search('categoryId')">
                    <option <#if categoryId??><#else>selected="selected"</#if> value="">所有类别</option>
                    <#if categoryList??>
                        <#list categoryList as c>
                            <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id+"]")>selected="selected"</#if>>${c.title!""}</option>
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
                    <input type="hidden" value="${goods.code!''}" id="code${goods.id?c}">
                    <input type="hidden" value="${goods.subTitle!''}" id="subTitle${goods.id?c}">
                    <tr id="tr_1424195166">
                        <td><a ><strong><img width="80" height="80" src="${goods.coverImageUri!''}"  /></strong><p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${goods.id?c}">${goods.title!''}</p></a> </td>
                        <td class="tb01">${goods.code!''}</td>
                        <td class="tb02">￥<span id="price${goods.id?c}">${goods.marketPrice?string('0.00')}</span>/${goods.promotion!''}</td>
                        <td>
                          <p><a onclick="editgoods(${goods.id?c});">编辑上架</a></p>
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
                                <a href="/distributor/goods/onsale?page=${page-1}&keywords=${keywords!''}&categoryId=${categoryId!''}">${page}</a>
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
    <p class="tit">商品上架<a  onclick="$('.sub_form').css('display','none')">×</a></p>
    <div class="info_tab">
    <form id ="form" action="/distributor/goodsOnsale" method="post">
      <table>
        <tr>
           <p> 编辑上架后的销售价格和库存：</p>
            <input type="hidden" id="goodsId" name="goodsId"/>
        </tr>
        <tr>
          <th>*商品名称：</th>
          <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
        </tr>
        <tr>
          <th>商品副标题：</th>
          <td><input type="text" class="add_width" name="subGoodsTitle" id="subTitle" readonly="readonly"></td>
        </tr>
        <tr>
          <th>商品编码：</th>
          <td><input type="text" class="add_width" name="code" id="code" readonly="readonly"></td>
        </tr>
         <tr>
          <th>*商品售价：</th>
          <td><input type="text" name="goodsPrice" id="goodsPrice" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="请输入正确的价格" nullmsg="请输入价格"></td>
        </tr>
        <tr>
          <th>*实体店价：</th>
          <td><input type="text" name="goodsMarketPrice" id="goodsMarketPrice" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="请输入正确的价格" nullmsg="请输入价格"></td>
        </tr>
        <tr>
          <th>商品单位：</th>
          <td><input type="text" name="unit" id="unit">不填则默认为平台设定单位</td>
        </tr>
         <tr>
          <th>*库存：</th>
          <td><input type="text" name="leftNumber" datatype="n" id="leftNumber" datatype="n" sucmsg=" " nullmsg="请输入库存" errormsg="请输入正确的库存"></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" onclick="subDisGoods();" value="确认提交"></td>
        </tr>
      </table>
      </form>
    </div>
  </aside>
</body>
</html>