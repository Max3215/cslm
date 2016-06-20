<div class="mymember_head">
        <h2><a href="/distributor/index"><img src="<#if site??>${site.logoUri!''}</#if>" /></a></h2>
        <div class="mymember_head_part">
            <a class="a001" href="/distributor/index">超市中心</a>
        </div> 
         <div class="mymember_head_part">
            <a class="a001" href="/distributor/collect/list">收藏夹</a>
        </div>
        <div id="mymember_nav01" class="mymember_head_part">
              <a class="a001 a002" onMouseOver="mymemberNavShow('mymember_navshow01','mymember_nav01')">设置</a>
              <div id="mymember_navshow01">
                <a href="/" target="_blank">浏览网站</a>
                <a href="/logout">退出登录</a>
              </div>
        </div>
       
    <div class="myclear"></div>
    <a class="mymember_head_kf" href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>">在线客服 >> </a>
 </div><!--mymember_head END-->
  <div class="myclear" style="height:20px;"></div>
  
  <div class="mymember_menu">
        <div class="mymember_menu_part">
            <a class="mymember_menu_tit" ><img src="/client/images/mymember/menu03.png" />订单中心</a>
            <div>
                <a href="/distributor/outOrder/list">我的销售单</a>
                <#if distributor?? && distributor.isStock?? && distributor.isStock==true>
                    <a href="/distributor/inOrder/list">我的进货单</a>
                </#if>
                <#if distributor?? && distributor.isSupply?? && distributor.isSupply==true>
                    <a href="/distributor/outOrder/list?typeId=2">分销订单</a>
                </#if>
                <a href="/distributor/return/list">处理退货</a>
                <#if distributor?? && distributor.isStock?? && distributor.isStock==true>
                    <a href="/distributor/order/return">申请退货</a>
                    <a href="/distributor/list/return">退货记录</a>
                </#if>
            </div>
        </div><!--mymember_menu_part END-->
        <div class="mymember_menu_part"> 
            <a class="mymember_menu_tit" title="商品管理"><img src="/client/images/mymember/menu06.png" />商品管理</a>
            <div>
                <#if distributor?? && distributor.isStock?? && distributor.isStock==true> 
                    <a href="/distributor/goods/list">商品进货</a>
                </#if>
                <#if distributor?? && distributor.isSupply?? && distributor.isSupply==true>
                    <a href="/distributor/supply/list">我要代理</a>
                </#if>
                <a href="/distributor/goods/onsale">商品上架</a>
                <#if distributor?? && distributor.isSupply?? && distributor.isSupply==true>
                    <a href="/distributor/goods/supply">我代理的商品</a> 
                </#if>
                <a href="/distributor/goods/sale/1">出售中的商品</a> 
                <a href="/distributor/goods/sale/0">仓库中的商品</a> 
                <a href="/distributor/sale">销售统计</a> 
             </div>
        </div> <!--mymember_menu_part END-->
        <div class="mymember_menu_part"> 
            <a class="mymember_menu_tit" title="超市管理"><img src="/client/images/mymember/menu05.png" />超市管理</a>
            <div> 
                <a href="/distributor/collect/list">商品收藏</a>
                <a href="/distributor/account">账号管理</a>
                <a href="/distributor/address/list">自提点设置</a> 
                <a href="/distributor/goods/need">商品供求</a> 
                <a href="/distributor/ad/list">广告管理</a>
                <a href="/distributor/info/list">信息管理</a>
                <a href="/distributor/info/8">平台服务</a> 
             </div>
        </div> <!--mymember_menu_part END-->
    </div><!--mymember_menu END-->