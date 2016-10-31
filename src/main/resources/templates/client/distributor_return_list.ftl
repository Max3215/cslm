<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css" />
<link href="/client/css/main.css" rel="stylesheet" type="text/css" />
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />

<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->
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
    <#include "/client/common_distributor_menu.ftl" />
    
    <div class="mymember_mainbox">
       <form name="form1" action="/distributor/list/return" method="POST">
       <input type="hidden" value="${page!'0'}" name="page" id="page">
       <input type="hidden" name="eventTarget" value="" id="eventTarget">
          <script type="text/javascript">
            var theForm = document.forms['form1'];
            if (!theForm) {
                theForm = document.form1;
            }
            function __doPostBack(eventTarget, eventArgument) {
                if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
                    if(eventTarget=='page'){
                        
                        theForm.page.value = eventArgument;
                    }else{
                        theForm.page.value = 0;
                        theForm.eventTarget.value = eventTarget;
                    }
                    
                    theForm.submit();
                }
            }
        </script>
          <div class="mymember_info mymember_info02">
                <div class="mymember_order_search"> 
                    <h3>退货列表</h3>
                    &nbsp;&nbsp;
                    <select name="statusId" onchange="javascript:setTimeout(__doPostBack('statusId',''), 0)">
                        <option value="" >全部记录</option>
                        <option value="0" <#if status_id?? && status_id==0>selected="selected"</#if>>新提交</option>
                        <option value="1" <#if status_id?? && status_id==1>selected="selected"</#if>>已同意</option>
                        <option value="2" <#if status_id?? && status_id==2>selected="selected"</#if>>已取消</option>
                    </select>
                </div>
        
        <table align="left">
            <tr class="mymember_infotab_tit01">
                <th width="120">超市名称</th>
                <th width="60">退货金额</th>
                <th width="120">预留电话</th>
                <th width="120">处理商</th>
                <th width="120">申请时间</th>
                <th width="50">状态</th>
                <th width="50">操作</th>
            </tr>
            <#if return_page??>
                <#list return_page.content as return>
                    <tr>
                      <td class="td001" width="70">
                        ${return.username!''}
                      </td>
                      <td>${return.goodsPrice?string('0.00')}</td>
                      <td class="td003">${return.telephone!''}</td>
                      <td>${return.shopTitle!''}</td>
                      <td class="td003">${return.returnTime!''}</td>
                      <td class="td003">
                            <#switch return.statusId>
                                <#case 0>新提交<#break>
                                <#case 1>已同意</span><#break>
                                <#case 2>已拒绝</span><#break>
                            </#switch>
                      </td>
                      <td ><a href="/distributor/return/detail?id=${return.id?c}">详情</a></td>
                    </tr>
                </#list>
            </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>
        <div class="mymember_page">
            <#if return_page??>
                <#assign continueEnter=false>
                <#if return_page.totalPages gt 0>
                    <#list 1..return_page.totalPages as page>
                        <#if page <= 3 || (return_page.totalPages-page) < 3 || (return_page.number+1-page)?abs<3 >
                            <#if page == return_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a onclick="javascript:setTimeout(__doPostBack('page',${(page-1)?c}), 0)">${page}</a>
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
      </form>
    </div>
    <!--mymember_center END-->
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->

<#include "/client/common_footer.ftl" />

</div>
</body>
</html>