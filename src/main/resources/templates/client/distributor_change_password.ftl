
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
<link href="/client/css/popup.css" rel="stylesheet" type="text/css" />

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
                 window.location.href="/distributor/edit"
            }
        }
    });
    
    $("#address").citySelect({
        nodata:"none",
        <#if distributor?? && distributor.province??>prov: "${distributor.province!''}",</#if>
        <#if distributor?? && distributor.city??>city: "${distributor.city!''}",</#if>
        <#if distributor?? && distributor.disctrict??>dist: "${distributor.disctrict!''}",</#if>
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
<#--
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
    height:230px;
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
    -->
<script src="/client/js/Rich_Lee.js"></script>
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
                 window.location.href="/distributor/edit"
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
                <form action="/distributor/edit/password" method="post" id="pas_form">
                    <input type="hidden" name="type" value="" id="type">
                    <div>
                        <label>原密码：</label>
                        <input class="text" type="password" name="password"  value="" />
                    </div>
                    <div>
                        <label>新密码：</label>
                        <input class="text" type="password" name="newPassword"  value="" />
                    </div>
                    <div>
                        <label>确认新密码：</label>
                        <input class="text" type="password" name="newPassword2" value="" />
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
        <h3>修改信息</h3>
        
		<form id="form1" action="/distributor/edit" method="post">
		<div class="haoh pt15 geren_rig">
            <div class="h20"></div>
            <input type="hidden" value="${distributor.id?c}" name="id" />
            <input name="__STATE" type="hidden" value="${distributor.password}"/>
            <table class="mymember_address">
                  <tr>
                       <th>商家名称：</th>
                       <td>
                            <input class="mytext" type="text" name="title" value="${distributor.title!''}" datatype="*2-100" sucmsg=" " errormsg="请输入名称"/>
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
                       <input type="text" class="mytext" name="address" value="${distributor.address!''}" datatype="*" sucmsg=" " errormsg="请输入详细地址"/ >
                       <span class="Validform_checktip">*</span>
                    </td>
                 </tr>
                 <tr>
                    <th>经度：</th>
                    <td>
                      <input name="longitude" type="text" value="<#if distributor?? && distributor.longitude??>${distributor.longitude?string("#.######")}</#if>" class="mytext"  datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="请正确复制坐标" sucmsg=" ">
                      <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
                      <span class="Validform_checktip"></span>
                    </td>
                </tr>
                <tr>
                    <th>纬度：</th>
                    <td>
                      <input name="latitude" type="text" value="<#if distributor?? && distributor.longitude??>${distributor.longitude?string("#.######")}</#if>" class="mytext" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,6})?$/" errormsg="请正确复制坐标" sucmsg=" ">
                      <a href="http://api.map.baidu.com/lbsapi/getpoint/" target="_blank">坐标拾取</a>
                      <span class="Validform_checktip"></span>
                    </td>
                </tr>
                 <tr>
                    <th>手机号：</th>
                    <td>
                       <input class="text" type="mytext" name="mobile" value="${distributor.mobile!''}" datatype="m|/^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/" sucmsg=" " errormsg="请输入正确的手机号">
                       <span style="float:none;" class="Validform_checktip"></span>
                    </td>
                </tr>
                  <tr>
                       <th>密码：</th>
                       <td>
                            <input class="mytext" type="password" value="${distributor.password!''}" <#if distributor.password??>readonly="readonly"</#if>  name="password" datatype="*" errormsg="请输入密码！" sucmsg=" " />&emsp;
                            <a href="javascript:;" onclick="edit_pwd();">修改</a>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                       <th>支付密码：</th>
                       <td>
                            <input class="mytext" type="password" name="payPassword" datatype="*" value="${distributor.payPassword!''}"   errormsg="请再次输入密码！" sucmsg=" "/>&emsp;
                            <a href="javascript:;" onclick="win_show();">修改</a>
                            <span class="Validform_checktip"></span>
                       </td>
                  </tr>
                  <tr>
                    <th>平台服务比例</th>
                    <td>
                        <input name="serviceRation" readonly="readonly" type="text" value="<#if distributor?? && distributor.serviceRation??>${distributor.serviceRation?string("0.00")}<#else>0.01</#if>" class="mytext" sucmsg=" "> 
                        <span class="Validform_checktip" style="color:red">*不可更改</span>
                    </td>
                  </tr>
                  <tr>
                    <th>第三方使用费比例</th>
                    <td>
                        <input name="aliRation" readonly="readonly" type="text" value="<#if distributor?? && distributor.aliRation??>${distributor.aliRation?string("0.00")}<#else>0</#if>" class="mytext" sucmsg=" "> 
                        <span class="Validform_checktip" style="color:red">*不可更改</span>
                    </td>
                  </tr>
                  <tr>
                    <th>商家邮费收取</th>
                    <td>
                        <input name="postPrice"  type="text" value="<#if distributor?? && distributor.postPrice??>${distributor.postPrice?string("0.00")}</#if>" datatype="/^[+-]?\d+(\.\d+)?$/" class="mytext" sucmsg=" " errormsg="请输入正确金额"> 
                        <span class="Validform_checktip">*</span>
                    </td>
                  </tr>
                  <tr>
                    <th>商家满额免邮</th>
                    <td>
                        <input name="maxPostPrice" type="text" value="<#if distributor?? && distributor.maxPostPrice??>${distributor.maxPostPrice?string("0.00")}</#if>" class="mytext" sucmsg=" " errormsg="请输入正确金额"> 
                        <span class="Validform_checktip">*商品总额满额包邮</span>
                    </td>
                   </tr>
                   <tr>
                    <th>商家配送说明</th>
                    <td>
                        <input name="postInfo" type="text" value="<#if distributor?? && distributor.postInfo??>${distributor.postInfo!''}</#if>" class="mytext" sucmsg=" " errormsg=" "> 
                        <span class="Validform_checktip">&emsp; 商品配送区域说明</span>
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