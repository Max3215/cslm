<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>批发中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script src="/client/js/mymember.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>

<!--  后台文件  -->
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
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
    adChange("n_banner_box","n_banner_sum","n_banner_num",3000,1000);

    $(".float_box .ewm").hover(function(){
        $(this).next().show();
    },function(){
        $(this).next().hide();
    })
})

function returnEdit(id){
    var dialog = $.dialog.confirm('操作提示信息：<br />确定已处理此次退货？', function () {
        var postData = { "id": id};
        //发送AJAX请求
        sendAjaxUrl(dialog, postData, "/provider/return/param/edit");
        return false;
    });
}

function sendAjaxUrl(winObj, postData, sendUrl) {
        $.ajax({
            type: "post",
            url: sendUrl,
            data: postData,
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
            },
            success: function (data) {
            
                if (data.code == 0) {
                    winObj.close();
                    $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                } else {
                    $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                }
            }
        });
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
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#-- 左侧菜单 -->
    <#include "/client/common_provider_menu.ftl" />
    <#-- 左侧菜单结束 -->
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
            <a class="a001" href="/user/return/list">退货列表</a>
            <div class="clear"></div>
        </div>
        
        <table align="left">
            <tr class="mymember_infotab_tit01">
                <th width="120">超市名称</th>
                <th width="60">退货金额</th>
                <th width="120">预留电话</th>
                <th width="120">处理商</th>
                <th width="120">申请时间</th>
                <th width="50">状态</th>
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
                      <td >
                            <#if return.statusId==0>
                            <a href="javascript:returnEdit(${return.id?c});">处理退货</a>
                            <#else>
                            已处理
                            </#if>     
                       </td>
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
                                <a href="/distributor/return/list?page=${page-1}">${page}</a>
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
    <!--mymember_center END-->
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>

    <#include "/client/common_footer.ftl">
</body>
</html>


