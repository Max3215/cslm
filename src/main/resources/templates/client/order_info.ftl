<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>购物车</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/order_info.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/style/common.css" rel="stylesheet" type="text/css" />
<link href="/client/style/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
$(document).ready(function(){
    
    $("#form1").Validform({
         tiptype: 1
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
  })
  
  $("#pcd").citySelect({
        nodata:"none",
        required:false
    }); 
  
})
</script>
</head>

<body>

    <!--  顶部  -->
    <#include "/client/common_header.ftl" />
    
<div class="clear20"></div>

<div class="main">
    <menu class="car_top">
        <p class="sel" style="z-index:10; width:34%;">我的购物车<i></i></p>
        <p class="sel" style="z-index:8;">我的订单信息<i></i></p>
        <p>支付成功</p>
        <div class="clear"></div>
    </menu>
    <div class="clear h30"></div>
    
    <form id="form1" action="/order/submit" method="post">
        <input type="hidden" name="type" value="<#if type??>${type!''}</#if>">
        <table class="address_tab">
            <tr>
                <th width="140">
                  <p>收货地址</p>
                  <a class="red" href="javascriput:viod(0);" onclick="$('.add_address').fadeIn();">新增收货地址</a>
                </th>
                <td>
                     <input id="input-address-id" type="hidden" name="addressId" value="" datatype="n" nullmsg="请选择收货地址!"/>
                    <#if user.shippingAddressList?? && user.shippingAddressList?size gt 0>
                        <#list user.shippingAddressList as address>
                             <a  href="javascript:;" onclick="javascript:selectAddress(this, ${address.id?c});">
                                <p>收货人：${address.receiverName!''}</p>
                                <p class="p1">收货地址：${address.province!''}${address.city!''}${address.disctrict!''}${address.detailAddress!''}</p>
                                <p>电话：${address.receiverMobile!''}</p>
                             </a>
                        </#list>                                                 
                     </#if>
                    <div class="clear"></div>
                </td>
            </tr>
        </table>
        <div class="add_address">
              <table>
                <tr>
                  <th>收货人姓名：</th>
                  <td><input type="text" class="text" id="receiverName"></td>
                </tr>
                <tr>
                  <th>地区：</th>
                  <td>
                    <div id="pcd">
                        <select name="province" id="province" class="prov" style="width: 100px;" ></select>
                        <select name="city" class="city" id="newcity" style="width: 100px;" ></select>
                        <select name="disctrict" class="dist" id="disctrict" style="width: 100px;" ></select>
                     </div>
                  </td>
                </tr>
                <tr>
                  <th>街道地址：</th>
                  <td><input type="text" class="text" style="width:300px;" id="detailAddress"></td>
                </tr>
                <tr>
                  <th>邮政编码：</th>
                  <td><input type="text" class="text" id="postcode"></td>
                </tr>
                <tr>
                  <th>手机号码：</th>
                  <td><input type="text" class="text" id="mobile"></td>
                </tr>
                <tr>
                  <th></th>
                  <td>
                    <input type="button" class="sub" value="保存" onclick="submitAddress();">
                    <input type="button" class="sub" value="取消" onclick="$('.add_address').fadeOut();">
                  </td>
                </tr>
              </table>
            </div>
    <div class="clear h20"></div>
    <div class="clear h20"></div>
    
    <section class="paybox">
        <h3>选择支付方式</h3>
        <#if pay_type_list_third??>
           <#list pay_type_list_third as pay_type>
               <div class="pay_style">
                    <input type="radio" name="payTypeId" value="${pay_type.id?c}" datatype="n"/>
                     <span><img src="${pay_type.coverImageUri}" width="148" /></span>
               </div>
            </#list>
        </#if>
        <div class="clear"></div>
    </section>
    <div class="clear h20"></div>
    <div class="clear h20"></div>
    <div class="clear h20"></div>
    
    <h3 class="car_tit">给商家留言：</h3>
    <div class="car_pay">
        <input class="text" type="text" value="" name="userMessage"/>
    </div>
    <div class="clear h20"></div>
    
    <div class="car_btn">
        <a class="ml20 fc" href="javascript:history.go(-1);">返回上一页</a>
        <span>应付总额：<b class="red fs18">￥${totalPrice?string('0.00')}</b></span>
        <input class="sub" type="submit" value="提交订单" />
    </div>
    <div class="clear"></div> 
    </form>
</div><!--main END-->

<div class="clear h50"></div>
    <!--  底部    -->
    <#include "/client/common_footer.ftl" />
</body>
</html>
