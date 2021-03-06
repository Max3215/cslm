
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

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 window.location.href="/user/password";
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
    <#include "/client/common_header.ftl">
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
  
   <#include "/client/common_user_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <h3>修改密码</h3>
        
		<form id="form1" action="/user/password" method="post">
		<div class="haoh pt15 geren_rig">
            <div class="h20"></div>
            <input name="__STATE" type="hidden" value="${user.password}"/>
            <input type="hidden" value="pwd" name="type" />
            <table class="mymember_address">
                  <tr>
                       <th>旧密码：</th>
                       <td>
                            <input class="mytext" type="password" name="password" datatype="*" errormsg="原始密码不正确" sucmsg=" " recheck="__STATE"/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                       <th>新密码：</th>
                       <td>
                            <input class="mytext" type="password" name="newPassword" datatype="*6-18" sucmsg=" "/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                       <th>确认新密码：</th>
                       <td>
                            <input class="mytext" type="password" name="newPassword2" datatype="*" recheck="newPassword" sucmsg=" "/>
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