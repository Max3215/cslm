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
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/provider_goods.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //初始化表单验证
    $("#sub_form").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 $('.sub_form').css('display','none');
                  window.location.href="/provider/goods/list/0?page=${page?c}&categoryId=${categoryId?c}"
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    });
});

// 取消批发多个商品
function deleteCheck(){
    $("#form").submit();
}  

function searchGoods(){
    $("#form1").submit();
}
function IsNum(){
    var num = $("#outFactoryPrice").val();
    var reNum=/^\d*$/; 
    if(reNum.test(num)){ 
        return true; 
    } else { 
        if(num < 0) { 
            alert("价格不能为负数！"); 
         } else { 
            alert("价格必须为数字！"); 
         } 
      return false;
     }
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
                    <h3><#if isOnSale?? && isOnSale>批发中的商品<#else>仓库中的商品</#if></h3>
                      <form action="/provider/goods/list/${isOnSale?c}" id="form1">
                            <input type="hidden" value="${page!'0'}" name="page"/>
                            <input class="mysub" type="submit" value="查询" />
                            <input class="mytext" type="text"  value="${keywords!''}" id="keywords"/>
                            <select  id="categoryId" name="categoryId" class="myselect" onchange="searchGoods()">
                                <option value="">请选择类别...</option>
                                <#if category_list??>
                                    <#list category_list as c>
                                        <option value="${c.id?c}" <#if categoryId?? && categoryId==c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                            <#--
                            <select  id="isDistribution" name="isDistribution" class="myselect" onchange="searchGoods()">
                               <option value="">是否分销</option>
                                <option value="isDistribution" <#if distribution?? && distribution=="isDistribution">selected="selected"</#if>>分销</option>
                                <option value="isNotDistribution" <#if distribution?? && distribution=="isNotDistribution">selected="selected"</#if>>未分销</option>
                            </select>
                            -->
                        </form>
                    <div class="clear"></div>
                </div>
                
                <form action="/provider/goods/checkAll/${isOnSale?c}" method="post" id="form">
                <input type="hidden" name="page" value="${page!'0'}"/>
                <input type="hidden" name="categoryId" value="${categoryId!''}"/>
                <input type="hidden" name="keywords" value="${keywords!''}"/>
                <div id="dis_goods_list">
                    <#include "/client/provider_goods_list.ftl">
                </div>
                </form>
            <div class="myclear" style="height:10px;"></div>

            <div class="mymember_page">
             <#if isOnSale>
                <a href="javascript:deleteCheck();"  class="fl">取消批发选中的商品</a>
            <#else>
                <a href="javascript:deleteCheck();"  class="fl">批发选中的商品</a>
            </#if>             
                <#if provider_goods_page??>
                <#assign continueEnter=false>
                <#if provider_goods_page.totalPages gt 0>
                    <#list 1..provider_goods_page.totalPages as page>
                        <#if page <= 3 || (provider_goods_page.totalPages-page) < 3 || (provider_goods_page.number+1-page)?abs<3 >
                            <#if page == provider_goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/provider/goods/list/${isOnSale?c}?page=${page-1}">${page}</a>
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
  <aside class="sub_form">
        <p class="tit">商品批发<a href="javascript:void(0);" onclick="$('.sub_form').css('display','none')">×</a></p>
        <form id="sub_form" action="/provider/goods/edit" method="post">
        <div class="info_tab">
          <table>
            <tr>
               <p> 编辑批发价格和库存：</p>
                <input type="hidden" id="goodsId" name="goodsId"/>
                <input type="hidden" id="page" name="page"/>
            </tr>
            <tr>
              <th>*商品名称：</th>
              <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
            </tr>
             <tr>
              <th>*商品批发价：</th>
              <td><input type="text" name="outFactoryPrice" id="outFactoryPrice" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=""></td>
            </tr>
             <tr>
              <th>*库存：</th>
              <td><input type="text" name="leftNumber" id="leftNumber" datatype="n"></td>
            </tr>
            <tr>
              <th></th>
              <td><input type="submit" class="sub"  value="确认提交"></td>
            </tr>
          </table>
        </div>
        </form>
  </aside>
</body>
</html>