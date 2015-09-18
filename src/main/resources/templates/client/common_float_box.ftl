<!--右边悬浮框-->
    <aside class="float_box">
        <a href="/user" class="user" title="个人中心"></a>
        <a href="/cart" class="car" title="购物车">购物车<span><#if cart_goods_list??>${cart_goods_list?size}<#else>0</#if></span></a>
        <a href="/user/collect/list" class="col" title="收藏"></a>
        <a href="/user/recent/list" class="history" title="浏览记录"></a>
        <a  class="ewm" title=""></a>
        <div class="ewm_show"><img src="${site.wxQrCode!''}"></div>
        <a href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>" title="在线咨询" id="floatqq" class="service"></a>
        <a href="javascript:move()" class="go_top" title="跳转顶部">TOP</a>
 <script>       
function move()
{
    $('html,body').animate({scrollTop:0},500);
}
</script>
    </aside>
    <#--
        <div class="floatboxlist" id="floatboxlist">
            <a href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>">联系客户1号</a>
            <a href="<#if site.qq2??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq2!''}&site=qq&menu=yes<#else>#</#if>">联系客户2号</a>
            <a href="<#if site.qq3??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq3!''}&site=qq&menu=yes<#else>#</#if>">联系客户3号</a>
            <a href="<#if site.qq4??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq4!''}&site=qq&menu=yes<#else>#</#if>">联系客户4号</a>
         </div>
         -->