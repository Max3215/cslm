<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><#if site??>${site.seoTitle!''}-</#if>提交订单</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<link href="/client/images/cslm.ico" rel="shortcut icon">
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
         tiptype: 1,
    });
    
    <#if msg??>
    alert("${msg!''}");
    </#if>
    
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

  navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
  
  $("#pcd").citySelect({
        nodata:"none",
        required:false
    }); 
    
  $("#delive_post").click(function(){
        <#if postPrice??>
             $("#post_price").val(${postPrice})
        <#else>
            $("#post_price").val('0');
        </#if>
      $("#input-address-id").attr("datatype","n");
     subPrice();
  }) 
  
  $("#delive_u").click(function(){
       $("#post_price").val('0');
       $("#input-address-id").removeAttr("datatype");
      subPrice();
  })  
  
})
    
function subPrice(){
    var postPrice = parseFloat($("#post_price").val());
    var totalPrice = parseFloat(${totalPrice})
    
    $("#postprice").html(postPrice.toFixed(2))
    $("#totalPrice").html((totalPrice+postPrice).toFixed(2));
}
</script>
</head>

<body>

    <!--  顶部  -->
    <#include "/client/common_header.ftl" />
   <style type="text/css">
        .win_out{
    position:fixed;
    overflow: hidden;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 100%;
    z-index: 999999999999999999;
    background: url(/client/images/win_outbd.png);
}
.win_out dl{
    background: #fff;
    margin: auto;
    width: 450px;
    height:140px;
    border-radius: 10px;
    margin-top: 260px;
}
.win_out dt{
    float: left;
    width: 100%;
}
.win_out dt span{
    float: left;
    width: 50%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #333333;
}
.win_out dd{
    float: left;
    width: 90%;
    padding:0 5%;
}
.win_out dd input{
    float: left;
    width: 70%;
    padding-left: 6%;
    height: 30px;
    border: #DDDDDD 1px solid;
  //  color: #999999;
    font-size: 16px;
}
.win_out dd div{
    overflow: hidden;
    margin-top: 20px;
}
.win_out dd label{
    display: block;
    float: left;
    width: 90px;
    line-height: 32px;
    text-align: center;
}
.win_out dd .btn{
    width: 60px;
    background: #f79100;
    color: white;
    font-size: 18px;
    border-bottom: none;
    outline: none;
    height: 40px;
}
.win_out dd span{
    width: 105px;
    background: #f79100;
    color: white;
    font-size: 18px;
    display: block;
    text-align: center;
    height: 30px;
    line-height:30px;
    margin-top: 10px;
    border-radius: 4px;
}
.win_out dd .submit{
    width: 105px;
    background: #f79100;
    color: white;
    font-size: 18px;
    display: block;
    text-align: center;
    height: 30px;
    line-height:30px;
    margin-top: 10px;
    border-radius: 4px;
    border:none;
    padding:0px;
}
    </style>
<script src="/client/js/Rich_Lee.js"></script>
<script type="text/javascript">
function win_show(){
    var oUt = rich('.win_out')[0];
    oUt.style.display = 'block';
};

function win_hide(){
    var oUt = rich('.win_out')[0];
    $("#payPwd").val('');
    oUt.style.display = 'none';
};
 
function checkPassword(){
     var paypwd=$("#payPwd").val();
      $.ajax({
            url : "/order/check/password",
            type : 'post',
            data : {"paypwd" : paypwd},
            success : function(data){
                if(data.code == 0){
                    alert(data.msg);
                    return;
                }else{
                    $("#form1").submit();
                }
           }
     })
} 
    
</script>
  <div class="win_out" style="display: none;">
        <dl>    
            <dt>

            </dt>
            <dd>
                <div>
                    <label>支付密码：</label>
                    <input class="text" id="payPwd" type="password" name="payPassword"  value="" />
                </div>
                
                <#if user.isUpdatePay?? && user.isUpdatePay == true>
	            <a href="/login/password_retrieve?type=paypwd" style="display:block;margin:10px 0 10px 90px;">忘记密码</a>
	            <#else>
	            <p style="display:block;margin:10px 0 10px 90px;">*初始支付密码默认为注册登录密码</p>
	            </#if>
                
                
                <input style="margin-top: 6px;float: left;margin-left:90px;background: #ff5b7d;" class="submit" type="button" name="password" onclick="checkPassword();" value="确定"  />
                <span style="margin-top: 6px;float: right;margin-right: 5px;background: #39bee9;" onclick="win_hide();">取消</span>
            </dd>
        </dl>
    </div> 
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
                    <#assign defaul = false>
                    <#if user.shippingAddressList?? && user.shippingAddressList?size gt 0>
                        <#list user.shippingAddressList as address>
                            <#if address.isDefaultAddress?? && address.isDefaultAddress>
                                <input id="input-address-id" type="hidden" name="addressId" value="${address.id?c}" datatype="n" nullmsg="请选择收货地址!"/>
                                <#assign defaul = true>        
                            <#else>
                            </#if>
                             <a  href="javascript:;" onclick="javascript:selectAddress(this, ${address.id?c});" <#if address.isDefaultAddress?? && address.isDefaultAddress>class="sel"</#if>>
                                <p>收货人：${address.receiverName!''}</p>
                                <p class="p1">收货地址：${address.province!''}${address.city!''}${address.disctrict!''}${address.detailAddress!''}</p>
                                <p>电话：${address.receiverMobile!''}</p>
                             </a>
                        </#list>                                                 
                     </#if>
                     <#if defaul == false>
                     <input id="input-address-id" type="hidden" name="addressId" value="" datatype="n" nullmsg="请选择收货地址!"/>
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
                        <select name="province" id="province" class="prov"></select>
                        <select name="city" class="city" id="newcity"></select>
                        <select name="disctrict" class="dist" id="disctrict"></select>
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
      
     <!-- 配送方式 -->
    <div class="del_mode">
        <p>配送方式</p>
        <div >
            <span><label><input type="radio" value="0" name="deliveryType" checked="checked"  datatype="n" id="delive_post" nullmsg="请选择配送方式!" />&nbsp;送货上门</label></span>
            <#if post??>&emsp;&emsp;${post!''}</#if>
        </div>
        <div  style="margin-top:15px;">
            <span><label><input type="radio" name="deliveryType" value="1" datatype="n" id="delive_u" nullmsg="请选择配送方式!" />&nbsp;门店自提</label></span>&nbsp;&nbsp;
            <#if addressList??>
            <select name="shipAddressId">
                <#list addressList as addr>
              <option value="${addr.id?c}">${addr.disctrict!''}${addr.detailAddress!''}</option>
                </#list>
            </select>
            </#if>
        </div>
        <input type="hidden" value="<#if postPrice??>${postPrice?string('0.00')}<#else>0</#if>" id="post_price">
    </div>
    <!-- 配送方式 END -->      
      
    <div class="clear h20"></div>
    <div class="clear h20"></div>
    
    <section class="paybox">
        <h3>选择支付方式</h3>
        <div class="pay_balance">
            <label><input type="radio" datatype="n" value="0" onclick="showPwdBtn()" checked="checked" name="payTypeId" nullmsg="请选择支付方式!"/>&nbsp;余额支付</label>
            <span>余额：<#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if></span>
          </div>

          <p class="alipay_tit">快捷支付</p>
          <div class="alipay">
              
              <label><input type="radio" value="1" onclick="showSub()"  name="payTypeId" datatype="n" nullmsg="请选择支付方式!"><img src="/client/images/ali.png" width="148px"></label>
              <label><input type="radio" value="2" onclick="showSub()"  name="payTypeId" datatype="n" nullmsg="请选择支付方式!"><img src="/client/images/wx.png" width="148px"></label>
        
              
              <div class="clear"></div>
           </div>
    </section>
     <script>
       function showPwdBtn(){
            $("#btn_sub").css("display","none");
            $("#btn_show").css("display","block");
       }
     function showSub(){
            $("#btn_sub").css("display","block");
            $("#btn_show").css("display","none");
       }
     
  
    </script>
    <div class="clear h20"></div>
    <div class="clear h20"></div>
    
    
    <h3 class="car_tit">给商家留言：</h3>
    <div class="car_pay">
        <input class="text" type="text" value="" name="userMessage"/>
    </div>
    <div class="clear h20"></div>
    
    <#assign price=0>
    <#assign postprice=0>
    <#if postPrice??>
        <#assign postprice=postPrice>
        <#assign price=totalPrice+postPrice>
    <#else>
        <#assign price=totalPrice>
    </#if>
    
    <div class="car_btn">
        <a class="ml20 fc" href="javascript:history.go(-1);">返回上一页</a>
         <span>&emsp;&emsp;&emsp;商品总额：￥<b class="red fs18" id="goodsPrice">${totalPrice?string('0.00')}</b>&nbsp;<b>＋</b>
                &nbsp; 邮费：￥<b class="red fs18" id="postprice"><#if postprice??>${postprice?string('0.00')}</#if></b>&nbsp;
                <#--<b>-</b>
                &nbsp; 积分抵消：￥<b class="red fs18" id="pointprice">0.00</b>
                -->
                <b>＝</b>
                &emsp;&emsp;
                应付总额：￥<b class="red fs18" id="totalPrice">${price?string('0.00')}</b></span>
                
        <input class="sub" type="submit" id="btn_sub" value="提交订单" style="display:none;"/>
        <input class="sub" type="button" id="btn_show" onclick="win_show()"  value="提交订单" />
    </div>
    <div class="clear"></div> 
    </form>
</div><!--main END-->

<div class="clear h50"></div>
    <!--  底部    -->
    <#include "/client/common_footer.ftl" />
</body>
</html>
