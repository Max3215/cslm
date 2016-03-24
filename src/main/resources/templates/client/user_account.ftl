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
<!--<link href="css/member.css" rel="stylesheet" type="text/css" />-->
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
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
<link href="/client/css/popup.css" rel="stylesheet" type="text/css" />
<script src="/client/js/Rich_Lee.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">
function win_show(){
    var oUt = rich('.win_out')[0];
    oUt.style.display = 'block';
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
                 window.location.href="/user/account"
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
                <form action="/user/password" method="post" id="pas_form">
                    <input type="hidden" name="type" value="payPwd" id="type">
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
    <#include "/client/common_header.ftl">
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
<div class="mymember_main">
    <#include "/client/common_user_menu.ftl">
  <div class="mymember_center add_width">
    <div class="mymember_info mymember_info01 no_width">
      <p class="fs18 pb10">账号管理</p>
      <table>
        <tr>
          <th width="150" rowspan="2"><a class="mymember_header p_left"><img src="${user.headImageUri!'/client/images/user_img.png'}" height="120px;" width="120px;"/></a></th>
          <td>账户名称：${user.username!''}</td>
          <td><a href="javascript:;" onclick="win_show();" class="btn">修改密码</a></td>
        </tr>
        <tr>
          <td>账户余额：¥<#if user.virtualMoney??>${user.virtualMoney?string('0.00')}<#else>0</#if></td>
          <td><a href="/user/topup1" class="btn">充值</a></td>
          <td><a href="/user/draw1" class="btn">提现</a></td>
        </tr>
      </table>
    </div>
    
    <div class="mymember_info mymember_info02">
      <menu class="buy_record">
        <a class="sel">最近交易记录</a>
      </menu>

      <table class="record_tab">
        <tr>
          <th >记录时间</th>
            <th width="18%">单号</th>
            <th >物流费</th>
            <th >商品总额</th>
            <th >订单总金额</th>
            <th >实际支出</th>
            <th>说明</th>
        </tr>
        <#if pay_record_page?? && pay_record_page.content??>
            <#list pay_record_page.content as re>
                <tr>
                    <td >${re.createTime?string('yyyy-MM-dd')}</td>
                    <td >${re.orderNumber!''}</td>
                    <td ><#if re.postPrice??>${re.postPrice?string('0.00')}<#else>0.00</#if></td>
                    <td ><#if re.totalGoodsPrice??>${re.totalGoodsPrice?string('0.00')}<#else>0.00</#if></td>
                    <td ><#if re.provice??>${re.provice?string('0.00')}<#else>0.00</#if></td>
                    <td >
                        <#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if>
                    </td>
                    <td >
                        ${re.cont!''}
                    </td>          
                </tr>
            </#list>
        </#if>
      </table>
      <div class="mymember_page"> 
        <#if pay_record_page??>
            <#assign continueEnter=false>
            <#if pay_record_page.totalPages gt 0>
                <#list 1..pay_record_page.totalPages as page>
                    <#if page <= 3 || (pay_record_page.totalPages-page) < 3 || (pay_record_page.number+1-page)?abs<3 >
                        <#if page == pay_record_page.number+1>
                            <a class="mysel" href="javascript:;">${page}</a>
                        <#else>
                            <a href="/user/account?page=${page-1}">${page}</a>
                        </#if>
                        <#assign continueEnter=false>
                    <#else>
                        <#if !continueEnter>
                            <b class="pn-break">&hellip;</b>
                            <#assign continueEnter=true>
                        </#if>
                    </#if>
                </#list>
            </#if>
        </#if>
        </div>
    </div> 

    
  </div>


<div class="clear"></div>
</div>
</div>
    <#include "/client/common_footer.ftl">

</body>
</html>




  











