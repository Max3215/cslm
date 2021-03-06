
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
<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
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
      <div class="mymember_info">
        <h3 style="font-weight:400;">申请退货</h3>		
		      <div class="haoh pt15 geren_rig">
              <div class="h20"></div>
              <div style="padding-left:40px;">
                <form action="/distributor/order/return" method="post" id="form1">
                <table class="s_d_table">
                  <tr>
                    <th>请选择退货商：</th>
                    <td>
                      <select name="shopId">
                        <#if pro_list??>
                        <#list pro_list as p>
                            <option value="${p.id?c}">${p.title!''}</option>
                        </#list>
                        </#if>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th>填写退款金额：</th>
                    <td>
                        <input type="text" name="goodsPrice" class="text lit" datatype="n"/>&nbsp;元
                    </td>
                  </tr>
                  <tr>
                    <th>退款说明：</th>
                    <td><textarea name="reason" datatype="*5-255" errormsg="问题描述必须大于5个字符，小于255个字符"></textarea></td>
                  </tr>
                  <tr>
                    <th></th>
                    <td><input type="submit" class="sub" value="提交申请" /></td>
                  </tr>
                </table>
                </from>
              </div>     
          </div>
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