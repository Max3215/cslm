
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

<#include "/client/common_header.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#include "/client/common_user_menu.ftl" />
    
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search fs16">退货详情</div>
        <table>
        
                <tr class="mymember_infotab_tit01">
                        <th width="350">商品名称</th>
                        <th>价格</th>
                        <th>购买数量</th>
                        <th>审核状态</th>
                </tr>
                
                <tr>
                    <td>
                        <ul class="list-proinfo" id="removeTheSingleGife">
                            <li class="fore1 oh">
                                <a href="/goods/${return.goodsId?c!''}" target="_blank" class="fl">
                                    <img height="50" width="50" title="" src="${return.goodsCoverImageUri!''}" alt="${return.goodsTitle!''}">
                                </a>
                                <div class="p-info fl ml10" style="width: 240px;height: 40px;overflow: hidden;"><a href="/goods/${return.goodsId?c!''}" target="_blank">${return.goodsTitle!''}</a>
                                </div>
                            </li>
                        </ul>
                    </td>
                    <td>￥<#if return.goodsPrice??>${return.goodsPrice?string('0.00')}<#else>0</#if></td>                  
                    <td><p>${return.returnNumber!''}</p></td>
                    <td>
                        <#if return.turnType?? && return.turnType ==2>
                            <#switch return.statusId>
                                <#case 0>超市审核中<#break>
                                <#case 1>分销商审核中<#break>
                                <#case 2>超市已拒绝<#break>
                                <#case 3>已同意<#break>
                                <#case 4>已拒绝<#break>
                            </#switch>
                            <#else>
                                <#switch return.statusId>
                                    <#case 0>新提交<#break>
                                    <#case 1>已同意<#break>
                                    <#case 2>已拒绝<#break>
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
                            <span style="line-height:26px;">${return.orderNumber!''}</span>
                          </div>

                          <div class="mymember_eva_div">
                            <b style="top:4px;">商家信息：</b>
                            <span style="line-height:26px;">${shop.title!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">商家地址：</b>
                            <span style="line-height:26px;">${shop.address!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">商家电话：</b>
                            <span style="line-height:26px;">${shop.serviceTele!''}</span>
                          </div>
                          <#if return.turnType?? && return.turnType ==2>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">分销商：</b>
                            <span style="line-height:26px;">${supply.title!''}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;width: 90px;">分销商电话：</b>
                            <span style="line-height:26px;">${supply.mobile!''}</span>
                          </div>
                          </#if>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">退货说明：</b>
                            <span style="line-height:26px;color:red;">${return.reason!'无'}</span>
                          </div>
                          <div class="mymember_eva_div">
                            <b style="top:4px;">当前状态：</b>
                            <#if return.turnType?? && return.turnType ==2>
                                <#switch return.statusId>
                                    <#case 0>超市审核中<#break>
                                    <#case 1><span style="line-height:26px;color:#3ae01d;">分销商审核中</span><#break>
                                    <#case 2><span style="line-height:26px;color:red;">超市已拒绝</span><#break>
                                    <#case 3><span style="line-height:26px;color:#3ae01d;">已同意</span><#break>
                                    <#case 4><span style="line-height:26px;color:red;">已拒绝</span><#break>
                                </#switch>
                            <#else>
                                <#switch return.statusId>
                                    <#case 0>新提交<#break>
                                    <#case 1><span style="line-height:26px;color:#3ae01d;">已同意</span><#break>
                                    <#case 2><span style="line-height:26px;color:red;">已拒绝</span><#break>
                                </#switch>
                            </#if>
                          </div>

                          <#if return.handleDetail?? >
                          <div class="mymember_eva_div">
                            <b style="top:4px;">超市意见：</b>
                            <span style="line-height:26px;">${return.handleDetail!'无'}</span>
                          </div>
                          </#if>
                          <#if return.turnType?? && return.turnType ==2>
                          <div class="mymember_eva_div">
                            <b style="top:4px; width: 90px;">分销商意见：</b>
                            <span style="line-height:26px;">${return.suppDetail!'无'}</span>
                          </div>
                          </#if>
                            <#--
                          <div class="mymember_eva_div">
                            <input class="mysub" type="submit" value="同意退货">
                            <input class="mysub change" type="submit" value="拒绝退货">
                          </div>
                          -->                        
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