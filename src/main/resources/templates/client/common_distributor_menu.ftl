<div class="mymember_head">
        <h2><a href="/distributor/index"><img src="<#if site??>${site.logoUri!''}</#if>" /></a></h2>
        <div class="mymember_head_part">
            <a class="a001" href="/distributor/index">超市中心</a>
        </div> 
        <div id="mymember_nav01" class="mymember_head_part">
            <div id="mymember_navshow01"></div>
        </div>
    <div class="myclear"></div>
    <a class="mymember_head_kf" href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>">在线客服 >> </a>
  </div><!--mymember_head END-->
  <div class="myclear" style="height:20px;"></div>
  
  <div class="mymember_menu">
      <div class="mymember_menu_part">
        <a class="mymember_menu_tit" ><img src="/client/images/mymember/menu03.png" />订单中心</a>
        <div>
            <a href="/distributor/outOrder/list/0">我的销售单</a>
            <a href="/distributor/inOrder/list/0">我的进货单</a>
           
        </div>
        </div><!--mymember_menu_part END-->
        <div class="mymember_menu_part"> 
            <a class="mymember_menu_tit" title="商品管理"><img src="/client/images/mymember/menu06.png" />商品管理</a>
            <div> 
                <a href="/distributor/goods/list">商品进货</a>
                <a href="/distributor/goods/onsale">商品上架</a> 
                <a href="/distributor/goods/sale/1">出售中的商品</a> 
                <a href="/distributor/goods/sale/0">仓库中的商品</a> 
                <a href="/distributor/sale">已卖出的商品</a> 
             </div>
    </div> <!--mymember_menu_part END-->
  </div><!--mymember_menu END-->