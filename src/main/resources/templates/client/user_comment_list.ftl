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
<script src="/client/js/Validform_v5.3.2_min.js"></script>
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

function showCommentTr(i, j)
{
    $("#comment-tr" + i + j).toggleClass("hide");
}

function commentJump()
{
    window.location.href="/user/comment/list?statusId=" + $("#commentTypeSelect").val();
}
</script>
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG_0.0.8a.js" ></script>
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
  <div class="mymember_head">
    <#include "/client/common_user_menu.ftl">
  
  <div class="mymember_mainbox">
        <div class="mymember_info">
            <div class="mymember_order_search">
                <a class="a001" >商品评价/晒单</a>
                <select id="commentTypeSelect" onchange="javascript:commentJump();">
                    <option value="0" <#if statusId==0>selected="selected"</#if>>待评价订单</option>
                    <option value="1" <#if statusId==1>selected="selected"</#if>>已评价订单</option> 
                </select>
                <div class="clear"></div>
            </div>
        <table class="mymember_evaluate">
            <tr>
              <th colspan="2">商品信息</th>
              <th width="200">购买时间</th>
              <th width="200">评价状态</th>
            </tr>
        <#if order_page??>
            <#list order_page.content as order>
                <#if order.orderGoodsList??>
                    <#list order.orderGoodsList as item>
                        <tr>
                            <td class="td001" width="70">
                                <a href="/goods/${item.goodsId?c}">
                                <img src="${item.goodsCoverImageUri!''}" width="60" height="60" /></a>
                            </td>
                            <td class="td002"><a href="/goods/${item.goodsId?c}">${item.goodsTitle!''}</a></td>
                            <td>${order.orderTime?string("yyyy-MM-dd")}</td>
                            <td class="td003">
                                <#if item.isCommented?? && item.isCommented>
                                    <a href="javascript:evaluateShow(${order_index}, ${item_index});">已评价</a>
                                <#else>
                                    <a href="javascript:evaluateShow('comment-tr${order_index}${item_index}','mymember_evabox'  );">发表评价</a>
                                </#if>
                            </td>
                            </tr>
                            <#if item.isCommented?? && item.isCommented>
                                <#if ("comment_" + order.id + "_" + item.id)?eval??>
                                <#assign comt=("comment_" + order.id + "_" + item.id)?eval>
                                    <tr id="comment-tr${order_index}${item_index}" class="mymember_evabox">
                                         <td class="td004" colspan="4">
                                            <div class="pb20 lh25">
                                                <input type="radio" disabled="disabled" <#if comt.stars?? && comt.stars==3>checked="checked"</#if>/><span class="mr20"> 好评</span>
                                                <input type="radio" disabled="disabled" <#if comt.stars?? && comt.stars==2>checked="checked"</#if>/><span class="mr20"> 中评</span>
                                                <input type="radio" disabled="disabled" <#if comt.stars?? && comt.stars==1>checked="checked"</#if>/><span class="mr20"> 差评</span>
                                            </div>
                                            <span style="position:absolute;right:88px;top:-13px;"><img src="/client/images/mymember/arrow06.gif" /></span>
                                            <div class="mymember_eva_div">
                                                  <b><font>* </font>商品满意度：</b>
                                                  <div class="myclear"></div>
                                                  <a class="a001" href="javascript:;"><img src="/client/images/mymember/<#if comt.goodsStar?? && comt.goodsStar gt 0>star02.png<#else>star01.png</#if>"></a>
                                                  <a class="a001" href="javascript:;"><img src="/client/images/mymember/<#if comt.goodsStar?? && comt.goodsStar gt 1>star02.png<#else>star01.png</#if>"></a>
                                                  <a class="a001" href="javascript:;"><img src="/client/images/mymember/<#if comt.goodsStar?? && comt.goodsStar gt 2>star02.png<#else>star01.png</#if>"></a>
                                                  <a class="a001" href="javascript:;"><img src="/client/images/mymember/<#if comt.goodsStar?? && comt.goodsStar gt 3>star02.png<#else>star01.png</#if>"></a>
                                                  <a class="a001" href="javascript:;"><img src="/client/images/mymember/<#if comt.goodsStar?? && comt.goodsStar gt 4>star02.png<#else>star01.png</#if>"></a>
                                                  <div class="myclear"></div>
                                            </div>
                                            <div class="mymember_eva_div">
                                              <b><font>* </font>评价：</b>
                                              <textarea disabled="disabled">${comt.content!''}</textarea>
                                            </div>
                            
                                        </td>
                                    </tr>
                                </#if>
                            <#else>
                                <tr id="comment-tr${order_index}${item_index}" class="mymember_evabox">
                                    <form class="commentForm${order_index}${item_index}" action="/user/comment/add" method="post">
                                        <input type="hidden" name="orderId" value=${order.id?c} />
                                        <input type="hidden" name="ogId" value=${item.id?c} />
                                        <input type="hidden" name="goodsId" value=${item.goodsId?c} />
                                        <td class="td004" colspan="4">
                                            <div class="pb20 lh25">
                                                <input class="ml20" type="radio" name="stars" value="3" datatype="n" nullmsg="请点击进行评价"/><span class="mr20"> 好评</span>
                                                <input type="radio" name="stars" value="2" datatype="n" nullmsg="请点击进行评价"/><span class="mr20"> 中评</span>
                                                <input type="radio" name="stars" value="1" datatype="n" nullmsg="请点击进行评价"/><span class="mr20"> 差评</span>
                                            </div>
                                            <span style="position:absolute;right:88px;top:-13px;"><img src="/client/images/mymember/arrow06.gif" /></span>
                                            <div class="mymember_eva_div">
                                                 <b><font>* </font>满意度：</b>
                                                 <div class="myclear"></div>
                                                 <input id="goodsStar${order_index}${item_index}" name="goodsStar" type="hidden" value="1" datatype="n" nullmsg="请点击进行评价"/>
                                                 <a class="goodsStar${order_index}${item_index} a001" href="javascript:starChange${order_index}${item_index}('goodsStar', 1);"><img src="/client/images/mymember/star01.png"></a>
                                                 <a class="goodsStar${order_index}${item_index} a001" href="javascript:starChange${order_index}${item_index}('goodsStar', 2);"><img src="/client/images/mymember/star01.png"></a>
                                                 <a class="goodsStar${order_index}${item_index} a001" href="javascript:starChange${order_index}${item_index}('goodsStar', 3);"><img src="/client/images/mymember/star01.png"></a>
                                                 <a class="goodsStar${order_index}${item_index} a001" href="javascript:starChange${order_index}${item_index}('goodsStar', 4);"><img src="/client/images/mymember/star01.png"></a>
                                                 <a class="goodsStar${order_index}${item_index} a001" href="javascript:starChange${order_index}${item_index}('goodsStar', 5);"><img src="/client/images/mymember/star01.png"></a>
                                            </div>
                                            <div class="mymember_eva_div">
                                                <b><font>* </font>评价：</b>
                                                <textarea name="content" datatype="*5-255" nullmsg="请输入评价内容"></textarea>
                                            </div>
                                            <div class="mymember_eva_div">
                                              <input class="mysub" type="submit" value="发表评论">
                                            </div>
                                        </td>
                                    </form>
                                </tr>
<script>  
$(document).ready(function(){  
     //初始化表单验证
    $(".commentForm${order_index}${item_index}").Validform({
        tiptype: 4,
        ajaxPost:true,
        callback: function(data) {
            if (data.code==0)
            {
                alert("提交评论成功");
                window.location.reload();
            }
            else
            {
                alert(data.message);
            }
        }
    });
});                            
// 改变星级
function starChange${order_index}${item_index}(type, stars)
{
    if (null == type || null == stars)
    {
        return;
    }
    
    var starCount = parseInt(stars);
    
    // 商品满意度
    if ("goodsStar" == type)
    {
        $("#goodsStar${order_index}${item_index}").val(starCount);
        switch(starCount)
        {
        case 1:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star01.png" />');
            break;
        case 2:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star01.png" />');
            break;
        case 3:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star01.png" />');
            break;
        case 4:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star01.png" />');
            break;
        case 5:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star02.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star02.png" />');
            break;
        default:
            $("a.goodsStar${order_index}${item_index}").eq(0).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(1).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(2).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(3).html('<img src="/client/images/mymember/star01.png" />');
            $("a.goodsStar${order_index}${item_index}").eq(4).html('<img src="/client/images/mymember/star01.png" />');    
        }
    }
}
</script>            
                           </#if>
                        </#list>
                    </#if>
                </#list>
            </#if> 
            
            <#if comment_page??>
                <#list comment_page.content as comment>
                <tr>
                    <td>
                       <a href="/goods/${comment.goodsId?c}">
                            <img src="${comment.goodsCoverImageUri!''}" width="50" height="50" title="${comment.goodsTitle!''}">                                             </a>                                        </td>
                    <td>
                       <a target="_blank" href="/goods/${comment.goodsId?c}">${comment.goodsTitle!''}</a>
                    </td>
                    <td>
                        我的评论：${comment.content!''}
                        <br>
                        ${comment.commentTime!''}
                        <br>
                        <#if comment.isReplied>
                        ${comment.reply!''}
                        <#else>
                        （暂无回复）
                        </#if>    
                    </td>
                </tr>
                </#list>
            </#if> 
      </table>
      
      <div class="myclear" style="height:10px;"></div>
      <div class="mymember_page">
        <#if comment_page??>
            <#assign continueEnter=false>
            <#if comment_page.totalPages gt 0>
                <#list 1..comment_page.totalPages as page>
                    <#if page <= 3 || (comment_page.totalPages-page) < 3 || (comment_page.number+1-page)?abs<3 >
                        <#if page == comment_page.number+1>
                            <a class="mysel" href="javascript:;">${page}</a>
                        <#else>
                            <a href="/user/comment/list?page=${page-1}&keywords=${keywords!''}">${page}</a>
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
  
  <div class="myclear"></div>
    <#if recent_page??>
        <#list recent_page.content as rgoods>
             <a class="mymember_hot_story" href="/goods/${rgoods.goodsId?c}">
                <img src="${rgoods.goodsCoverImageUri!''}"  width="65" height="65"/>
                <p>￥${rgoods.goodsSalePrice?string("0.00")}</p>
             </a>
        </#list>
     </#if> 
  <div class="myclear"></div>  
</div><!--mymember_main END-->
<div class="myclear"></div>
</div>
<!--mymember END-->

<div class="clear"></div>

    <#include "/client/common_footer.ftl">
</body>
</html>


