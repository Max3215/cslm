
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
<script src="/client/js/jquery.cityselect.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<script src="/client/js/user.js"></script>
<script src="/layer/layer.js"></script>
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

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })

  
  $("#pcd").citySelect({
        nodata:"none",
        <#if address?? && address.province??>prov: "${address.province!''}",</#if>
        <#if address?? && address.city??>city: "${address.city!''}",</#if>
        <#if address?? && address.disctrict??>dist: "${address.disctrict!''}",</#if>
        required:false
    }); 
  
  
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
                <h3>收货地址</h3>
        
		        <div class="haoh pt15 geren_rig">
                    <table class="center_tab">
                         <tr>
                            <th>收货人</th>
                            <th>地区</th>
                            <th>邮政编码</th>
                            <th>联系电话</th>
                            <th>地址</th>
                            <th>操作</th>
                         </tr>
                         <#if address_list??>
                         <#list address_list as address>
                         <tr>
                            <td>${address.receiverName!''}</td>
                            <td>${address.province!''}${address.city!''}${address.disctrict!''}</td>
                            <td>${address.postcode!''}</td>
                            <td>${address.receiverMobile!''}</td>
                            <td>${address.detailAddress!''}</td>
                            <td>
                                <#if address.isDefaultAddress?? && address.isDefaultAddress>
                                    <p><a style="color:red;">默认地址</a></p>
                                <#else>
                                    <p><a onclick="defaultAddr(${address.id?c});">设为默认</a></p>
                                </#if>
                                <p><a href="/user/address/update?id=${address.id?c}">修改</a></p>
                                <p><a onclick="deleteAddr(${address.id?c})">删除</a></p>
                            </td>
                         </tr>
                         </#list>
                         </#if>
                    </table>
        
                    <div class="h20"></div>
                    <form method="post" action="/user/address/save" id="form1" >
                        <input class="mytext" name="addressId" type="hidden" value="<#if address??>${address.id?c}</#if>">
                        <input  name="isDefaultAddress" type="hidden" value="" id="defaultAddr">
                        <table class="mymember_address">
                             <tr>
                                <th>收货人：</th>
                                <td>
                                    <input class="mytext" type="text" name="receiverName" datatype="*2-128" errormsg="最少两个字符！" sucmsg=" " value="<#if address??>${address.receiverName!''}</#if>"/>
                                    <span class="Validform_checktip"></span>
                                </td>
                             </tr>
                             <tr>
                                <th>地区：</th>
                                <td>
                                    <div id="pcd">
                                    <select name="province" class="prov" style="width: 100px;" datatype="*" sucmsg=" "></select>
                                    <select name="city" class="city" style="width: 100px;" datatype="*" sucmsg=" "></select>
                                    <select name="disctrict" class="dist" style="width: 100px;" datatype="*0-10" sucmsg=" "></select>
                                    </div>
                                </td>
                             </tr>
                             <tr>
                                <th>地址：</th>
                                <td>
                                    <input class="mytext" name="detailAddress" datatype="*2-128" errormsg="最少两个字符！" sucmsg=" " value="<#if address??>${address.detailAddress!''}</#if>" type="text">
                                    <span class="Validform_checktip"></span>
                                </td>
                             </tr>
                             <tr>
                                <th>邮政编码：</th>
                                <td>
                                    <input class="mytext" name="postcode" datatype="p" errormsg="邮政编码为6位数字！" sucmsg=" " value="<#if address??>${address.postcode!''}</#if>" type="text">
                                    <span class="Validform_checktip"></span>
                                </td>
                             </tr>
                             <tr>
                                <th>手机号码：</th>
                                <td>
                                    <input class="mytext" name="receiverMobile" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/" errormsg="请输入正确的电话号码格式！" sucmsg=" " value="<#if address??>${address.receiverMobile!''}</#if>" type="text">
                                    <span class="Validform_checktip"></span>
                                </td>
                             </tr>
                             <tr>
                                <th></th>
                                <td><input class="mysub" type="button" onclick="subAddress();" value="保存" /></td>
                             </tr>
                        </table>
                    </form>
                </div>
            </div>
      <!--mymember_info END-->    
       </div>
   </div>
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>