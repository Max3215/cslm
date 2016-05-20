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
    background: #39BEE9;
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
    width: 60px;
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
    width: 60px;
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
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
function win_show(){
    var oUt = rich('.win_out')[0];
    oUt.style.display = 'block';
    
    $("#type").attr("value","payPwd");
};

function edit_pwd(){
    var oUt = rich('.win_out')[0];
    oUt.style.display = 'block';
    $("#type").attr("value","pwd");
};

function win_hide(){
    var oUt = rich('.win_out')[0];
    oUt.style.display = 'none';
};
 
$(document).ready(function(){
     //初始化表单验证
    $("#pas_form").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 window.location.href="/distributor/inOrder/list/0";
            }
        }
    });
 });   
    
    
</script>
  <div class="win_out" style="display: none;">
        <dl>    
            <dt>

            </dt>
            <dd>
                <form action="/distributor/order/info" method="post" id="pas_form">
                    <input type="hidden" name="type" value="" id="type">
                    <div>
                        <label>支付密码：</label>
                        <input class="text" type="password" name="payPassword"  value="" />
                    </div>
                        <input style="margin-top: 30px;float: left;margin-left: 30px;" class="submit" type="submit" name="password"  value="确定"  />
                        <span style="margin-top: 30px;float: right;margin-right: 30px;" onclick="win_hide();">取消</span>
                </form>
            </dd>
        </dl>
    </div>
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#include "/client/common_distributor_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
      
          <div id="cart_goodslist">
            <#include "/client/distributor_ingoods_cartlist.ftl">
          </div>
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
        <p class="tit">进货清单<a  onclick="$('.sub_form').css('display','none')">×</a></p>
        <div class="info_tab">
          <table>
                <input type="hidden" id="goodsId" name="goodsId"/>
                <input type="hidden" id="leftNumber" name="leftNumber">
            <tr>
              <th>商品名称：</th>
              <td><input type="text" class="add_width" name="goodsTitle" id="goodsTitle" readonly="readonly"></td>
            </tr>
            <tr>
              <th>商品副标题：</th>
              <td><input type="text" class="add_width" name="subGoodsTitle" id="subTitle" readonly="readonly"></td>
            </tr>
             <tr>
              <th>批发价：</th>
              <td><input type="text" name="goodsMarketPrice" readonly="readonly" id="outFactoryPrice" ></td>
            </tr>
             <tr>
              <th>供货商：</th>
              <td><input type="text" name="providerTitle" class="add_width" readonly="readonly" id="providerTitle"></td>
            </tr>
            <tr>
              <th>进货数量：</th>
              <td><input type="number" name="shopReturnRation"  id="quantity" min="1" ></td>
            </tr>
            <tr>
              <th></th>
              <td><input type="submit" class="sub" onclick="addGoods();" value="确认提交"></td>
            </tr>
          </table>
        </div>
      </aside>


</body>
</html>