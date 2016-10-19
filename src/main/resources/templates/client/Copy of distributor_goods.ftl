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
<script src="/client/js/jquery.diysiteselect.js"></script>
<script src="/client/js/distributor_goods.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    $("#sub_form").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 $('.sub_form').css('display','none');
                 window.location.href="/distributor/goods/sale/1"
            }
            else if(data.code=2)
            {
                $('.sub_form').css('display','none');
                window.location.href="/distributor/goods/sale/0"
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

    navDownList("nav_down","li",".nav_show");
    menuDownList("mainnavdown","#nav_down",".a2","sel");
 //   adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    });
});

//上架/下架多个商品
function onsaleAll(){
    $("#form").submit();
}  

function searchSale(){
    $("#form1").submit();
} 

function editPrice(dgId,page){
    var goodsTitle = $("#title"+dgId).html();
    var subTitle = $("#subTitle"+dgId).val();
    var unit = $("#unit"+dgId).val();
    var code = $("#code"+dgId).val(); 
    var marketPrice = $("#marketPrice"+dgId).val();
    var goodsPrice = $("#price"+dgId).html();
    var leftNumber = $("#number"+dgId).html();
    
    $("#goodsId").attr("value",dgId);
    $("#page").attr("value",page);
    $("#goodsTitle").attr("value",goodsTitle);
    $("#subGoodsTitle").attr("value",subTitle);
    $("#marketPrice").attr("value",marketPrice);
    $("#goodsPrice").attr("value",goodsPrice);
    $("#leftNumber").attr("value",leftNumber);
    $("#unit").attr("value",unit);
    $("#code").attr("value",code);
    $('.sub_form').css('display','block');
}


//超市中心商品上下架
function editOnSale(){
    var disId = $("#goodsId").val();
    var page = $("#page").val();
    var goodsTitle = $("#goodsTitle").val();
    var goodsPrice = $("#goodsPrice").val();
    var maxPostPrice = $("#maxPostPrice").val();
    var postPrice = $("#postPrice").val();
    
    if(undefined == goodsTitle || ""==goodsTitle)
    {
        alert("请输入商品标题");
        return;
    }
     var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
        alert("请输入商品销售价");
        return ;
    }

    $.ajax({
        url : "/distributor/goods/editOnSale/"+disId,
        data : {"goodsTitle":goodsTitle,
                "goodsPrice":goodsPrice,
                "postPrice":postPrice,
                "maxPostPrice":maxPostPrice,
                "page":page},
        type : "post",
       success:function(res){
            $('.sub_form').css('display','none');
            $("#dis_goods_table").html(res);
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
                    <h3><#if isOnSale>出售中的商品<#else>仓库中的商品</#if></h3>
                         <form action="/distributor/goods/sale/${isOnSale?c}" id="form1"> 
                          <input class="mysub" type="submit" value="查询" />
                          <input type="hidden" name="categoryId" id="categoryId" value="<#if category??>${category.id?c}</#if>" />
                          <input class="mytext" type="text" name="keywords"  value="${keywords!''}" id="keywords" />
                          <input type="hidden" name="dir" value="${sort!''}" id="sort"/>
                          <select id="oneCat" onchange="javascript:search('oneCat')" >
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
                          <#--
                          <select  id="categoryId" name="categoryId" class="myselect" onchange="searchSale()">
                                <option value="">请选择类别...</option>
                                <#if category_list??>
                                    <#list category_list as c>
                                        <option value="${c.id?c}" <#if categoryId?? && categoryId==c.id>selected="selected"</#if>>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                            -->
                          </form>
                          
                    <div class="clear"></div>
                </div>
                
                <form action="/distributor/onsaleAll/${isOnSale?c}" method="post" id="form">
                <input type="hidden" name="categoryId" value="<#if categoryId??>${categoryId?c!''}</#if>"/>
                <input type="hidden" name="keywords" value="${keywords!''}"/>
                <input type="hidden" name="dir" value="${sort!''}" >
                <div id="dis_goods_list">
                    <#include "/client/distributor_goods_list.ftl">
                </div>
                </form>
            <div class="myclear" style="height:10px;"></div>

            <div class="mymember_page">
                <#if isOnSale>
                    <a href="javascript:onsaleAll();"  class="fl">下架选中的商品</a>
                <#else>
                    <a href="javascript:onsaleAll();" class="fl">上架选中的商品</a>
                </#if>
                
                <#if dis_goods_page??>
                <#assign continueEnter=false>
                <#if dis_goods_page.totalPages gt 0>
                    <#list 1..dis_goods_page.totalPages as page>
                        <#if page <= 3 || (dis_goods_page.totalPages-page) < 3 || (dis_goods_page.number+1-page)?abs<3 >
                            <#if page == dis_goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/goods/sale/${isOnSale?c}?page=${(page-1)?c}&keywords=${keywords!''}<#if categoryId??>&categoryId=${categoryId?c!''}</#if><#if sort??>&dir=${sort}</#if>">${page}</a>
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
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
    
    <!-- 点击商品上架后弹出层 -->
  <aside class="sub_form" style="display:none" id="detail_div">
    <p class="tit">商品上架<a  onclick="$('.sub_form').css('display','none')">×</a></p>
    <form id="sub_form" action="/distributor/goods/editOnSale" method="post">
    <div class="info_tab">
        <input type="hidden" name="type" value="${isOnSale?c}">
      <table>
        <tr>
           <p> 编辑商品信息：</p>
            <input type="hidden" id="goodsId" name="goodsId"/>
            <input type="hidden" id="page" name="page"/>
        </tr>
        <tr>
          <th>*商品名称：</th>
          <td ><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
        </tr>
        <tr>
          <th>*商品副标题：</th>
          <td ><input type="text" class="add_width" name="subGoodsTitle" id="subGoodsTitle" ></td>
        </tr>
        <tr>
          <th>商品编码：</th>
          <td ><input type="text" class="add_width" name="code" id="code" ></td>
        </tr>
        <tr>
          <th>*实体店价：</th>
          <td><input type="text" name="goodsMarketPrice" id="marketPrice" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="请正确输入" nullmsg="请输入价格"></td>
        </tr>
         <tr>
          <th>*特惠价：</th>
          <td><input type="text" name="goodsPrice" id="goodsPrice" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" " errormsg="请正确输入" nullmsg="请输入价格"></td>
        </tr>
        <tr>
            <th>单位：</th>
            <td><input type="text" name="unit" id="unit" ></td>
        </tr>
        <tr>
          <th>*库存：</th>
          <td ><input type="text" name="leftNumber" id="leftNumber" datatype="n" sucmsg=" " nullmsg="请输入库存" errormsg="请输入正确的库存"></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" value="确认提交"></td>
        </tr>
      </table>
    </div>
    </form>
  </aside>
</body>
</html>