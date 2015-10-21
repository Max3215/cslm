
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
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
        <h3>个人信息</h3>
        
		<form id="form1" action="/user/info" method="post">
		<div class="haoh pt15 geren_rig">
        <div class="h20"></div>
        <table class="mymember_address">
          <tr>
            <th>姓名：</th>
            <td>
                <input name="realName" type="text"  class="mytext" id="textfield" value="${user.realName!''}" size="33"/>
            </td>
          </tr>
          <tr>
            <th>昵称：</th>
             <td><input class="mytext" name="nickname" type="text" /></td>
          </tr>
          <tr>
            <th>性别：</th>
            <td>
                <input type="radio" id="pcUserman" name="sex" class="pcUserRaman" value="男" <#if user.sex?? && user.sex=="男">checked="checked" </#if>/>
                <label for="pcUserman">&nbsp;男</label>
                      
                <input type="radio" id="pcUserwoman" name="sex" value="男" <#if user.sex?? && user.sex=="女">checked="checked" </#if>/>
                <label for="pcUserwoman">&nbsp;女</label></td>
          </tr>
          <tr>
            <th>邮箱：</th>
            <td><input class="mytext" type="text" name="email" value="${user.email!''}" datatype="e"/></td>
          </tr>
          <tr>
            <th>手机号码：</th>
            <td><input class="mytext" type="text" name="mobile" datatype="m" value="${user.mobile!''}"/></td>
          </tr>
          <tr>
            <th></th>
            <td><input class="mysub" type="submit" value="保存" /></td>
          </tr>
        </table>
      </div>
	  </form>
	  
	  
      </div>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>