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
<script src="/layer/layer.js"></script>
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
    });
});

//上架/下架多个商品
function deleteAll(){
	
    $("#form").submit();
}  

function searchSale(){
    $("#form1").submit();
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
             <form action="/distributor/goods/supply" id="form1" method="post">
             <input type="hidden" name="eventTarget" value="" id="eventTarget">
             <input type="hidden" name="eventArgument" value="" id="eventArgument">
                    <script type="text/javascript">
                        var theForm = document.forms['form1'];
                        if (!theForm) {
                            theForm = document.form1;
                        }
                        function __doPostBack(eventTarget, eventArgument) {
                            if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                                if(null != eventTarget && eventTarget=="oneCat")
                                {
                                    $("#categoryId").attr("value",$("#oneCat").val());
                                }else if(null != eventTarget && eventTarget=="twoCat")
                                {
                                   $("#categoryId").attr("value",$("#twoCat").val());
                                }else if(null != eventTarget && eventTarget=="categoryId")
                                {
                                    $("#categoryId").attr("value",$("#category").val());
                                }else if(null != eventTarget && eventTarget=="btnPage")
                                {
                                    theForm.page.value = eventArgument;
                                }
                                
                                theForm.eventTarget.value = eventTarget;
                                theForm.eventArgument.value = eventArgument;
                                theForm.submit();
                            }
                        }
                    </script> 
            <div class="mymember_info mymember_info02">
                <div class="mymember_order_search"> 
                    <h3>代理的商品</h3>
                          <input class="mysub" type="button" value="导出全部"  onclick="javascript:setTimeout(__doPostBack('excel',''), 0)" />
                          <input class="mysub" type="submit" value="查询" />
                          <input type="hidden" name="categoryId" id="categoryId" value="<#if category??>${category.id?c}</#if>" />
                          <input type="hidden" name="page" id="page" value="" />
                          <input class="mytext" type="text" name="keywords"  value="${keywords!''}" id="keywords" />
                          <select id="oneCat" onchange="javascript:__doPostBack('oneCat',0);" >
                                <option value="">所有类别</option>
                                <#if category_list??>
                                    <#list category_list as c>
                                        <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                             <select id="twoCat" onchange="javascript:__doPostBack('twoCat',0)">
                                <option value="">所有类别</option>
                                <#if cateList??>
                                    <#list cateList as c>
                                        <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                             <select id="category" onchange="javascript:__doPostBack('categoryId',0)">
                                <option  value="">所有类别</option>
                                <#if categoryList??>
                                    <#list categoryList as c>
                                        <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                                    </#list>
                                </#if>
                            </select>
                    <div class="clear"></div>
                </div>
                
                <div id="dis_goods_list">
                    <#include "/client/distributor_goods_supply.ftl">
                </div>
            <div class="myclear" style="height:10px;"></div>

            <div class="mymember_page">
                    <a href="javascript:__doPostBack('btnDelete','')" class="fl">删除选中的商品</a>
                
                <#if dis_goods_page??>
                <#assign continueEnter=false>
                <#if dis_goods_page.totalPages gt 0>
                    <#list 1..dis_goods_page.totalPages as page>
                        <#if page <= 3 || (dis_goods_page.totalPages-page) < 3 || (dis_goods_page.number+1-page)?abs<3 >
                            <#if page == dis_goods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a onclick="javascript:__doPostBack('btnPage','${(page-1)?c}')">${page}</a>
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
 
    </form>
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
    
    <!-- 点击商品上架后弹出层 -->
  <aside class="sub_form" style="display:none">
    <p class="tit">商品上架<a  onclick="$('.sub_form').css('display','none')">×</a></p>
    <div class="info_tab">
      <table>
        <tr>
           <p> 编辑上架后的销售价格：</p>
            <input type="hidden" id="goodsId" name="goodsId"/>
            <input type="hidden" id="page" name="page"/>
        </tr>
        <tr>
          <th>*商品名称：</th>
          <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
        </tr>
         <tr>
          <th>*商品售价：</th>
          <td><input type="text" name="goodsPrice" id="goodsPrice" ></td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" onclick="editOnSale();" value="确认提交"></td>
        </tr>
      </table>
    </div>
  </aside>
</body>
</html>