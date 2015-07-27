<script src="/client/js/Validform_v5.3.2_min.js"></script>
<link href="/client/css/style.css" rel="stylesheet" type="text/css" />

<#-- 投诉 -->
<script>
$(document).ready(function(){
    $("#suggestionForm").Validform({
        tiptype: 3,
        ajaxPost:true,
        callback: function(data) {
            if (data.code==0)
            {
                alert("提交成功，我们会尽快处理，请耐心等待");
                window.location.reload();
            }
            else
            {
                alert(data.message);
            }
        }
    });
});

function hideSuggestion()
{
    $("#sugguestion-div").css("display", "none");
}
</script>

<div id="sugguestion-div" class="mianfeilingqutanchu" style="display:none;">
    <div class="mianfeilingqutanchu_dl"> 
        <div class="main bgff">
            <div class="tousuyemian">
                <div class="rightb_gundong fr" id="yincang">
                    <a href="javascript:hideSuggestion();"><img src="/client/images/20150407114113116_easyicon_net_71.8756476684.png" width="21" height="21" /></a>
                </div>
                  <form id="suggestionForm" action="/suggestion/add">
                      <div class="clear"></div>
                      <h3>我要投诉</h3>
                      <p>在您填写下列投诉内容之前，我们首先代表车有同盟为导致您进行投诉的原因（行为）表示歉意，请详细描述事件经过，以便我们尽快为您解决问题，我们一定会及时处理，给您一个满意的解决方案，您的满意是我们最大的动力，谢谢！</p>
                      <div class="tousuneirong">
                          <#-- 
                          <span>标题</span><input name="title"  onfocus="if(value=='订单编号') {value=''}" onblur="if (value=='订单编号') {value='订单编号'}"  value="" id="et_contact_name" class="input ml20" type="text">
                          -->
                          <div class="clear h10"></div>
                          <span><b class="red">*</b>投诉内容</span>
                          <div class=" clear"></div>
                          <textarea class="input" style="width:1000px;height:250px" onfocus="if(value=='') {value=''}" onblur="if (value=='') {value=''}"  value="" datatype="*1-255" nullmsg="请输入投诉内容" id="suggestionContent" name="content"></textarea>
                          
                      </div>
                      <div class="tousulianxifangshi mt20">
                          <p>为了尽快为您解决问题，请提供您的联系方式，谢谢。</p>
                          <div> 
	                          <span><b class="red">*</b>称呼</span>                                               
	                          <input name="name" value="" datatype="*1-20" nullmsg="请输入您的称呼" class="input ml20" type="text">                                         
                          </div>
                          <div class="clear h10"></div>
                          <div>
	                          <span>邮箱&nbsp;&nbsp;</span>
	                          <input name="mail"   value="" datatype="*0-250" nullmsg="请输入正确的邮箱地址" class="input ml20" type="text">
                          </div>
                          <div class="clear h10"></div>
                          <div>
	                          <span><b class="red">*</b>手机</span>
	                          <input name="mobile"   value="" datatype="m" nullmsg="请输入您的手机号码" class="input ml20" type="text">
                          </div>
                          <div class="clear h20"></div>
                          <input class="Message_an" type="submit" value="提交" title="提交" />
                      </div>
                   </form>
            
                <div class="tousubeizhu mt5">
                    <h4>备注</h4>
                    <p>您还可以拨打${site.telephone!''}进行电话投诉</p>
                </div>  
            </div>
        </div>
    
        <div class="clear"></div> 
    </div>
</div>



<div class="top">
    <div class="w1200 top1">
        <p class="huanyin">商城访问量：<span class="blue">${site.totalVisits!'0'}</span></p>
        <p class="huanyin">在线人数：<span class="blue">${site.totalOnlines!'1'}</span></p>
    
        <div class="wdbgg">
            <#if username??>
                <a href="/user">${username}<i></i></a>
                <a href="/logout">退出<i></i></a>
            <#else>
                <a href="/login" target="_blank">登录<i></i></a>
                <a href="/reg" target="_blank">免费注册<i></i></a>
            </#if>     
            <a href="/user/order/list/0">我的订单</a>
            <a href="/user">会员中心</a>
            <a href="#">在线咨询</a>
      
            服务热线：<#if site??>${site.telephone!''}
          
            </#if>
        </div>
    </div>
</div>
<div class="w1200 top2">
    <div class="logo"><a href="/"><img src="<#if site??>${site.logoUri!''}</#if>" width="200" height="100"/></a></div>
    <div class="tpgg">
        <#if top_small_ad_list??>
            <#list top_small_ad_list as item>
                <a <#if item.typeIsNewWindow?? && item.typeIsNewWindow>target="_blank"</#if> href="${item.linkUri!''}">
                    <img src="${item.fileUri!''}" width="70" height="38"/>
                </a>
            </#list>
        </#if>
    </div>
    <div class="ssbox">
        <div class="clear"></div>
        <form action="/search" method="get">
            <input type="text" class="ss_txt" name="keywords" value="${keywords!keywords_list[0].title}"/>
            <input type="submit" class="ss_btn" value="" />
        </form>
        <div class="clear"></div>
        <p class="sousuohuise" style="margin-top: 5px;">
            <#if keywords_list??>
                <#list keywords_list as item>
                    <#if item_index gt 0>
                    <a href="${item.linkUri!''}">${item.title}</a>
                    </#if>
                </#list>
            </#if>
        </p>
    </div>
    <div id="shopping_down" class="shopping_box">
        <span class="sp1"><#if cart_goods_list??>${cart_goods_list?size}<#else>0</#if></span>
        <a class="a9" href="/cart"><img src="/client/images/liebiao_09.png" width="28" height="28" />购物车<i></i></a>
        <menu>
            <#if cart_goods_list?? && cart_goods_list?size gt 0>
<script>
function delItem(id)
{
    if (null == id)
    {
        return;
    }
    
    $.ajax({
        type:"post",
        url:"/cart/del",
        data:{"id": id},
        success:function(data){
            location.reload();
        }
    });
}
</script>
                <#assign totalGoods=0>
                <#assign totalPrice=0>
                <h2>最新加入的商品</h2>
                <#list cart_goods_list as item>
                    <div class="shopping_list">
                        <div class="clear"></div>
                        <a class="a2" href="/goods/${item.goodsId}"><img src="${item.goodsCoverImageUri!''}" /></a>
                        <a class="a3" href="/goods/${item.goodsId}">${item.goodsTitle!''}</a>
                        <p>￥<#if item.price??>${item.price?string("0.00")} x ${item.quantity!'0'}</#if><a href="javascript:delItem(${item.id});">删除</a></p>
                        <div class="clear"></div>
                    </div>
                    <#if item.isSelected>
                        <#assign totalGoods=totalGoods+item.quantity>
                        <#assign totalPrice=totalPrice+item.price*item.quantity>
                    </#if>
                </#list>
                
                <h4 class="shopping_price">
                    共<#if cart_goods_list??>${cart_goods_list?size}<#else>0</#if>件商品 &nbsp;&nbsp;共计<span class="fw-b">￥<#if totalPrice??>${totalPrice?string(0.00)}</#if></span>
                    <a href="/cart">去结算</a>
                </h4>
            <#else>
                <h3 class="ta-c pa15 fs14 fw400">购物车中还没有商品，赶紧选购吧！</h3>
            </#if>
        </menu>
    </div>
</div>
<nav class="navbox">
    <div class="w1200">
        <section class="navlist" id="mainnavdown">
            <a href="javascript:;" class="a2">全部商品分类</a>
            <ul class="navlistout" id="navdown" style="display:none;">
                <#if top_cat_list??>
                    <#list top_cat_list as item>
                        <li>
                            <h3><a href="/list/${item.id}">${item.title!''}</a></h3>
                            <div class="nav_showbox">
                                <div class="clear"></div>
                                <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                    <table class="nav_more">
                                        <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                            <tr>
                                                <th width="90"><span><a href="/list/${secondLevelItem.id}">${secondLevelItem.title!''}</a></span></th>
                                                <td>
                                                    <#if ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval?? >
                                                        <#list ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval as thirdLevelItem>
                                                            <a href="/list/${thirdLevelItem.id}">${thirdLevelItem.title!''}</a>
                                                        </#list>
                                                    </#if>
                                                </td>
                                            </tr>
                                        </#list>
                                    </table>
                                </#if>
                                
                                <div class="clear"></div>
                            </div>
                        </li>
                    </#list>
                </#if>
            </ul><!--navlistout END-->
        </section>
        <#if navi_item_list??>
            <#list navi_item_list as item>
                <a class="a1" href="${item.linkUri!''}">${item.title!''}</a>
            </#list>
        </#if> 
    </div> 
</nav>
<div class="clear20"></div>




<aside class="floatbox">
    <a href="javascript:;" title="微信客服"><img src="/client/images/float_ico01.png" width="42" height="42" alt="微信客服" /><span><img src="${site.wxQrCode!''}" width="84" height="84"/></span></a>
    <a href="http://wpa.qq.com/msgrd?v=3&uin=${site.qq!''}&site=qq&menu=yes" target="_blank" title="在线咨询"><img src="/client/images/float_ico02.png" width="42" height="42" alt="在线咨询" /></a>
    <a href="javascript:;" title="新浪微博"><img src="/client/images/float_ico03.png" width="42" height="42" alt="新浪微博" /><span><img src="${site.weiboQrCode!''}" width="84" height="84"/></span></a>
    <a href="javascript:$('#sugguestion-div').show();" title="投诉"><img src="/client/images/float_ico04.png" width="42" height="42" alt="服务热线"/></a>
    <a href="javascript:$('html,body').animate({scrollTop:0},500);" title="到顶部"><img src="/client/images/float_ico05.png" width="42" height="42" alt="到顶部" /></a>

</aside>