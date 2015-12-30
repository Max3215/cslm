
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>分销中心</title>
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
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            alert(data.msg);
            if(data.code==1)
            {
                 window.location.href="/supply/edit"
            }
        }
    });
    
     $("#address").citySelect({
        nodata:"none",
        <#if supply?? && supply.province??>prov: "${supply.province!''}",</#if>
        <#if supply?? && supply.city??>city: "${supply.city!''}",</#if>
        <#if supply?? && supply.disctrict??>dist: "${supply.disctrict!''}",</#if>
        required:false
    });

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
  
   <#include "/client/common_supply_menu.ftl">
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <h3>修改密码</h3>
        
		<form id="form1" action="/supply/edit" method="post">
        <div class="haoh pt15 geren_rig">
            <div class="h20"></div>
            <input type="hidden" value="${supply.id?c}" name="id" />
            <input name="__STATE" type="hidden" value="${supply.password}"/>
            <table class="mymember_address">
                  <tr>
                       <th>商家名称：</th>
                       <td>
                            <input class="text" type="text" name="title" value="${supply.title!''}" datatype="*2-100" sucmsg=" " errormsg="请输入名称"/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                    <th>所属地区：</th>
                    <td>
                       <div id="address">
                           <select name="province" class="prov" style="width: 100px;" datatype="*"></select>
                           <select name="city" class="city" style="width: 100px;" datatype="*"></select>
                           <select name="disctrict" class="dist" style="width: 100px;" datatype="*0-10"></select>
                           <span style="float:none;" class="Validform_checktip">*</span>
                       </div>
                    </td>
                  </tr>
                  <tr>
                    <th>详细地址：</th>
                    <td>
                       <input type="text" class="text" name="address" value="${supply.address!''}" datatype="*" sucmsg=" " errormsg="请输入详细地址"/ >
                       <span class="Validform_checktip">*</span>
                    </td>
                 </tr>
                 <tr>
                    <th>手机号：</th>
                    <td>
                       <input class="text" type="text" name="mobile" value="${supply.mobile!''}" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/" sucmsg=" ">
                       <span style="float:none;" class="Validform_checktip"></span>
                    </td>
                </tr>
                  <tr>
                       <th>旧密码：</th>
                       <td>
                            <input class="text" type="password" value="${supply.password!''}" name="password" datatype="*" errormsg="请输入密码！" sucmsg=" " />
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                       <th>重复密码：</th>
                       <td>
                            <input class="text" type="password" datatype="*" value="${supply.password!''}" recheck="password"  errormsg="请再次输入密码！" sucmsg=" "/>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                        <th>平台服务比例</th>
                        <td>
                            <input name="serviceRation" readonly="readonly" type="text" value="<#if supply?? && supply.serviceRation??>${supply.serviceRation?string("0.00")}<#else>0.01</#if>" class="text" sucmsg=" "> 
                            <span class="Validform_checktip" style="color:red">*不可更改</span>
                        </td>
                      </tr>
                      <tr>
                        <th>支付宝使用费比例</th>
                        <td>
                            <input name="aliRation" readonly="readonly" type="text" value="<#if supply?? && supply.aliRation??>${supply.aliRation?string("0.00")}<#else>0</#if>" class="text" sucmsg=" "> 
                            <span class="Validform_checktip" style="color:red">*不可更改</span>
                        </td>
                      </tr>
                      <tr>
                        <th>商家邮费收取比例</th>
                        <td>
                            <input name="postPrice"  type="text" value="<#if supply?? && supply.postPrice??>${supply.postPrice?string("0.00")}<#else>0</#if>" class="text" sucmsg=" "> 
                            <span class="Validform_checktip">*商家每次交易时根据商品总额按比例计算邮费（如：0.01）</span>
                        </td>
                      </tr>
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