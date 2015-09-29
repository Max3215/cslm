
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
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
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
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
  
   <#include "/client/common_provider_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <h3>修改密码</h3>
        
		<form id="form1" action="/provider/password" method="post">
		<div class="haoh pt15 geren_rig">
            <div class="h20"></div>
            <input name="__STATE" type="hidden" value="${provider.password}"/>
            <table class="mymember_address">
                  <tr>
                       <th>旧密码：</th>
                       <td>
                            <input class="mytext" type="password" name="oldPassword" datatype="*" errormsg="原始密码不正确" recheck="__STATE"/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                       <th>新密码：</th>
                       <td>
                            <input class="mytext" type="password" name="newPassword" datatype="*6-18"/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                       <th>确认新密码：</th>
                       <td>
                            <input class="mytext" type="password" datatype="*" recheck="newPassword"/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                  <tr>
                        <th></th>
                        <td><input class="mysub" type="submit" value="确定" /></td>
                  </tr>
            </table>
        </div>
	    </form>
	  
      </div>
      
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