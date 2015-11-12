<div class="mymember_head">
    <h2>
        <a href="/supply/index">
            <img src="<#if site??>${site.logoUri!''}</#if>" />
        </a>
    </h2>
    <div class="mymember_head_part">
      <a class="a001" href="/supply/index">分销商品中心</a>
    </div> 
    <div id="mymember_nav01" class="mymember_head_part">
        <a class="a001 a002" onMouseOver="mymemberNavShow('mymember_navshow01','mymember_nav01')">设置</a>
          <div id="mymember_navshow01">
            <a href="/" >浏览网站</a>
            <a href="/logout">退出登录</a>
          </div>
    </div>
    <div class="myclear"></div>
        <a class="mymember_head_kf" href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>">在线客服 >> </a>
</div><!--mymember_head END-->
  <div class="myclear" style="height:20px;"></div>
  <div class="mymember_menu">
      <div class="mymember_menu_part">
            <a class="mymember_menu_tit" >
                <img src="/client/images/mymember/menu01.png" />订单中心
            </a>
              <div>
                <a href="/supply/disOrder/list/0">我的分销单</a>
                <a href="/supply/goods/list/1">分销中商品</a>
                <a href="/supply/goods/list/0">仓库中商品</a>
                <a href="/supply/goods/audit">待审核商品</a>
                <a href="/supply/goods/distribution">选择分销商品</a>
              </div>
       </div><!--mymember_menu_part END-->
       <div class="mymember_menu_part"> 
            <a class="mymember_menu_tit" title="商品管理"><img src="/client/images/mymember/menu05.png" />账号管理</a>
            <div> 
                <a href="/supply/account">账号管理</a> 
                <a href="/supply/info/8">平台服务</a> 
             </div>
        </div> <!--mymember_menu_part END-->
  </div><!--mymember_menu END-->