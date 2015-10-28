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
        <#if distribution?? && distribution!="isDistribution">
          <div id="cart_goodslist">
            <#include "/client/distributor_ingoods_cartlist.ftl">
          </div>
        </#if>
         <div class="h30"></div>
           <div>
                <#include "/client/distributor_ingoods_list.ftl">
          </div>
        </div>
    </div>
    <div class="myclear"></div>
  </div>
  <div class="myclear"></div>
</div>
<!--mymember END-->

     <aside class="sub_form">
        <p class="tit">商品分销<a  onclick="$('.sub_form').css('display','none')">×</a></p>
        <div class="info_tab">
          <table>
            <tr>
               <p> 编辑上架后的销售价格：</p>
                <input type="hidden" id="goodsId" name="goodsId"/>
            </tr>
            <tr>
              <th>*商品名称：</th>
              <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle"></td>
            </tr>
             <tr>
              <th>商品原价：</th>
              <td><input type="text" name="goodsMarketPrice" readonly="readonly" id="outFactoryPrice" ></td>
            </tr>
             <tr>
              <th>*商品销售价：</th>
              <td><input type="text" name="goodsPrice" id="goodsPrice" ></td>
            </tr>
             <tr>
              <th>供货商：</th>
              <td><input type="text" name="providerTitle" readonly="readonly" id="providerTitle"></td>
            </tr>
            <tr>
              <th>返利比：</th>
              <td><input type="text" name="shopReturnRation" readonly="readonly" id="shopReturnRation"></td>
            </tr>
            <tr>
              <th></th>
              <td><input type="submit" class="sub" onclick="subDistribution();" value="确认提交"></td>
            </tr>
          </table>
        </div>
      </aside>


</body>
</html>