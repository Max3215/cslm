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

<script src="/layer/layer.js"></script>
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


function convert(){
	var totalPoint = ${user.totalPoints!'0'};
	var point = $("#point").val();
	
	if(!/^\+?[1-9][0-9]*$/.test(point)){
		layer.msg('请输入正确的兑换积分数');
	}
	if(totalPoint < point){
		layer.msg('可兑换积分不足'+point);
	}
	
	$.ajax({
            url : "/user/convert",
            async : true,
            type : 'post',
            data : {"point" : point},
            success : function(data){
            	layer.open({
					  title: '提示',
					  content: data.msg,
					  time: 3000
					});
				if(data.code==1){
					window.location.reload();
				} 
            }
      })
}
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
    <!--  顶部  -->
    <#include "/client/common_header.ftl" />

<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
        <#include "/client/common_user_menu.ftl">
    <div class="mymember_mainbox">  
    	<div class="integral_show">
          <div class="fl">
            <p class="p1">我的积分</p>
            <p class="p2">${user.totalPoints!'0'}</p>
          </div>
          <div class="fr">
            <div class="con">
              <p class="p1">积分兑换现金</p>
              <div class="oh"><input type="text" class="text" id="point" placeholder="输入兑换的积分值" /><a href="javascript:;" onclick="convert()">我要兑换</a></div>
              <p class="p2">*兑换规则：<#if site??>${site.registerSharePoints!'10'}</#if>积分=1元</p>
            </div>
          </div>
        </div>  
    <div class="mymember_info mymember_info02">
        <div class="mymember_order_search">
                积分记录 
                <#-- 
                <form action="/supply/pay/record" id="form">
                    <input type="hidden" value="${page!'0'}" name="page"/>
                    <select  id="cont" name="cont" class="myselect" onchange="subRecord()">
                        <option value="">全部记录</option>
                        <option value="充值" <#if cont?? && cont=="充值">selected="selected"</#if>>充值记录</option>
                        <option value="提现" <#if cont?? && cont=="提现">selected="selected"</#if>>提现记录</option>
                        <option value="销售" <#if cont?? && cont=="销售">selected="selected"</#if>>销售记录</option>
                        <option value="分销" <#if cont?? && cont=="分销">selected="selected"</#if>>分销记录</option>
                    </select>
               </form>
               -->
            <div class="clear"></div>
        </div>
        <table style="table-layout: fixed;">
            <tr class="mymember_infotab_tit01">     
                  <th>订单编号</th>
                  <th>交易时间</th>
                  <th>积分</th>
                  <th>说明</th>
            </tr>     
            <#if point_page?? && point_page.content??>
                <#list point_page.content as re>
                    <tr>
                          <td >${re.orderNumber!''}</td>
                          <td class="td003">
                            <p>${re.pointTime?string('yyyy-MM-dd')}</p>
                            <p>${re.pointTime?string('HH:mm:ss')}</p>
                          </td>
                          <td>${re.point!'0'}</td>
                          <td>${re.detail}</td>         
                    </tr>
                </#list>
            </#if>
      </table>
      <div class="myclear" style="height:10px;"></div>
      <div class="mymember_page">
        <#if point_page??>
            <#assign continueEnter=false>
            <#if point_page.totalPages gt 0>
                <#list 1..point_page.totalPages as page>
                    <#if page <= 3 || (point_page.totalPages-page) < 3 || (point_page.number+1-page)?abs<3 >
                        <#if page == point_page.number+1>
                            <a class="mysel" href="javascript:;">${page}</a>
                        <#else>
                            <a href="/supply/pay/record?page=${page-1}">${page}</a>
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
    </div><!--mymember_info END-->   
    
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