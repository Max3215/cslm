
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
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
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
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

  $("#nav_down li").hover(function(){
    $(this).find(".nav_show").fadeIn(10);
  },function(){
    $(this).find(".nav_show").stop(true,true).fadeOut(10);
  })  

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
    <#include "/client/common_header.ftl">
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    <#include "/client/common_user_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search">申请退货</div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <th width="260">商品名称</th>
                    <th width="170">价格</th>
                    <th width="105">购买数量</th>
                </tr>
                <tbody>
                <#if order_goods??>
                <tr>
                    <td>
                        <ul class="list-proinfo" id="removeTheSingleGife">
                            <li class="fore1">
                                <a href="/goods/${order_goods.goodsId?c}" target="_blank">
                                    <img height="50" width="50" title="${order_goods.goodsTitle!''}" src="${order_goods.goodsCoverImageUri!''}" data-img="1" alt="${order_goods.goodsTitle!''}">
                                </a>
                                <div class="p-info">
                                    <a href="/goods/${order_goods.goodsId?c!''}" target="_blank">${order_goods.goodsTitle!''}</a>
                                </div>
                            </li>
                        </ul>
                    </td>                  
                    <td>
                        ${order_goods.price?string('0.00')}
                    </td>
                    <td>${order_goods.quantity}</td>
                </tr>
                </#if>
                </tbody>
            </table>
            <#if order_goods??>
            <form method="post" action="/user/return/save" id="form1">
                <table width="100%" border="0">
                  <tbody>
                  <tr>
                    <td> 
                        <span style="position:absolute;right:88px;top:-13px;">
                        <img src="/client/images/mymember/arrow06.gif"></span>
                        <input type="hidden" name="goodsId" value="${order_goods.goodsId?c!''}" />
                        <input type="hidden" name="id" value="${order.id?c}" />
                        <input type="hidden" name="shopId" value="${shop.id?c}" />
                        
                        <div class="mymember_eva_div">
                          <b><font>* </font>问题描述：</b>
                          <textarea name="reason" datatype="*5-255" errormsg="问题描述必须大于5个字符，小于255个字符" ></textarea>
                          <span class="Validform_checktip">*问题描述</span>
                        </div>
                        <div class="mymember_eva_div">
                            <b style="top:4px;">商家信息：</b>
                            <span style="line-height:30px;" name="shopTitle">${shop.title!''}</span>
                        </div>
                        <div class="mymember_eva_div">
                            <b style="top:4px;">商家地址：</b>
                            <span style="line-height:30px;">${shop.address!''}</span>
                        </div>
                        <div class="mymember_eva_div">
                            <b><font>* </font>联系电话：</b>
                            <input type="text" name="telephone" datatype="n8-20" errormsg="请输入正确的电话格式" />
                            <span class="Validform_checktip">*联系电话</span>
                        </div>
                        
                        
                        <#if !has_returned??>
                        <div class="mymember_eva_div">
                          <input class="mysub" type="submit" value="提交">
                        </div>
                        </#if>
                    </td>
                  </tr>
                  </tbody>
                </table>
            </form>
            </#if>
	     </div>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>