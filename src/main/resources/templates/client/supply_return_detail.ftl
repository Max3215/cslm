
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>分销中心</title>
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


function editReturn(id,statusId){
    var dialog = $.dialog.confirm('操作提示信息：<br />确定已处理此次退货？', function () {
        var handleDetail = document.getElementById("suppDetail").value;
        var realPrice = document.getElementById("realPrice").value;
        
        var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
	    if(undefined == realPrice || ""==realPrice || !reg.test(realPrice))
	    {
	        $.dialog.alert('错误提示：请正确输入退还金额', function () { }, dialog);
	        return ;
	    }
	    
        var postData = { "id": id,"statusId":statusId,"suppDetail":suppDetail,"realPrice":realPrice};
        //发送AJAX请求
        
        sendAjaxUrl(dialog, postData, "/supply/return/param/edit");
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

<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#include "/client/common_supply_menu.ftl" />
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search fs16">退货详情</div>
        <table>
        
                <tr class="mymember_infotab_tit01">
                        <th width="350" align="center">商品名称</th>
                        <th  align="center">价格</th>
                        <th  align="center">购买数量</th>
                        <th  align="center">审核状态</th>
                </tr>
                
                <tr>
                    <td>
                        <ul class="list-proinfo" id="removeTheSingleGife">
                            <li class="fore1 oh">
                                <a  class="fl">
                                    <img height="50" width="50" title="" src="${userRturn.goodsCoverImageUri!''}" alt="${userRturn.goodsTitle!''}">
                                </a>
                                <div class="p-info fl ml10" style="width: 240px;height: 40px;overflow: hidden;"><a >${userRturn.goodsTitle!''}</a>
                                </div>
                            </li>
                        </ul>
                    </td>
                    <td>￥<#if userRturn.goodsPrice??>${userRturn.goodsPrice?string('0.00')}<#else>0</#if></td>                  
                    <td><p>${userRturn.returnNumber!''}</p></td>
                    <td>
                        <#if userRturn.statusId??>
                        <#switch userRturn.statusId>
                            <#case 0>新提交<#break>
                            <#case 1>超市已同意<#break>
                            <#case 2>超市已拒绝<#break>
                            <#case 3>已同意<#break>
                            <#case 4>已拒绝<#break>
                        </#switch>
                        </#if>
                    </td>
                </tr>
                
            </table>
            <table>
               
                <tr>
                  <td> <span style="position:absolute;right:88px;top:-13px;"><img src="/client/images/mymember/arrow06.gif"></span>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">订单编号：</b>
                            <span style="line-height:26px;">${userRturn.orderNumber!''}</span>
                          </div>
					      <div class="mymember_eva_div">
                            <b style="top:4px;">退还金额：￥</b>
                            <input style="line-height:26px;height:26px;border:1px solid #ccc;text-indent:4px;" id="realPrice" value="<#if return.realPrice??>${return.realPrice?string('0.00')}</#if>" />
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">会员账号：</b>
                            <span style="line-height:26px;">${userRturn.username!''}</span>
                          </div>

                          <div class="mymember_eva_div">
                            <b style="top:4px;">联系电话：</b>
                            <span style="line-height:26px;">${userRturn.telephone!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">提交时间：</b>
                            <span style="line-height:26px;">${userRturn.returnTime!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">说明：</b>
                            <span style="line-height:26px;">${userRturn.reason!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">当前状态：</b>
                            
                                <#if userRturn.statusId??>
                                <#switch userRturn.statusId>
                                    <#case 0>新提交<#break>
                                    <#case 1><span style="line-height:26px;color:#3ae01d;">超市已同意</span><#break>
                                    <#case 2><span style="line-height:26px;color:red;">超市已拒绝</span><#break>
                                    <#case 3><span style="line-height:26px;color:#3ae01d;">已同意</span><#break>
                                    <#case 4><span style="line-height:26px;color:red;">已拒绝</span><#break>
                                </#switch>
                                </#if>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">超市意见：</b>
                            <span style="line-height:26px;">${userRturn.handleDetail!'无'}</span>
                          </div>

                          <div class="mymember_eva_div">
                            <b>处理说明：</b>
                            <textarea id="suppDetail">${userRturn.suppDetail!'无'}</textarea>
                          </div>
                            
                          <#if userRturn.statusId ==1>
                          <div class="mymember_eva_div">
                            <input class="mysub" type="submit" onclick="editReturn(${userRturn.id?c},3)" value="同意退货">
                            <input class="mysub change" type="submit" onclick="editReturn(${userRturn.id?c},4)" value="拒绝退货">
                          </div>
                          </#if>
                    </td>
                </tr>              
          </table>
	     </div>
       <style type="text/css">
          .mymember_eva_div{margin:0 10px;font-size: 14px;color:#333;}
          .mymember_eva_div .mysub{width: 150px; height: 32px; border: none; color: #FFF; font-family: "微软雅黑"; background: #39bee9; cursor: pointer;border-radius: 4px;font-size: 14px;}
          .mymember_eva_div .mysub.change{background: #ff5a7c;margin-left: 20px;} 
          .mymember_info02 td{position: static;}
          .mymember_info02 th{border: 1px solid #eee;}
       </style>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
</div>
<!--mymember END-->
<div class="clear"></div>
<#include "/client/common_footer.ftl" />
</body>
</html>