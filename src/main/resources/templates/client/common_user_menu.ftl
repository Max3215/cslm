 <div class="mymember_head">
    <h2><a href="/">超市联盟</a></h2>
    <div class="mymember_head_part">
      <a class="a001" href="/user">个人主页</a>
    </div> 
    <div id="mymember_nav01" class="mymember_head_part">
      <a class="a001 a002" onMouseOver="mymemberNavShow('mymember_navshow01','mymember_nav01')">设置</a>
      <div id="mymember_navshow01">
        <a href="/user">个人信息</a>
        <a href="/user/password/list">收货地址</a>

      </div>
    </div>
    <div class="myclear"></div>
    <a class="mymember_head_kf" href="#">在线客服 >> </a>
  </div><!--mymember_head END-->
  <div class="myclear" style="height:20px;"></div>
 <div class="mymember_menu">
    <#if user.roleId?? && user.roleId==1 >
       <div class="mymember_menu_part"> 
            <a class="mymember_menu_tit" title="商品管理"><img src="/client/images/mymember/menu06.png" />商品管理</a>
            <div> 
                <a href="/distributor/goods/sale/1">出售中的商品</a> 
                <a href="/distributor/goods/sale/0">仓库中的商品</a> 
                <a href="/distributor/sale">已卖出的商品</a> 
             </div>
        </div>
        <#--
        <div class="mymember_menu_part">
            <a class="mymember_menu_tit" titlef="交易管理"><img src="/client/images/mymember/menu07.png" />交易管理</a>
            <div>
                
                <a href="#">评价管理</a> 
            </div>
        </div>
        -->
     </#if>
    <div class="mymember_menu_part">
      <a class="mymember_menu_tit"  title="订单中心"><img src="/client/images/mymember/menu01.png" />订单中心</a>
      <div>
        <a href="/user/order/list/0"  title="我的订单">我的订单</a>
        <a href="/user/comment/list"  title="评价结单">评价结单</a>
        <a href="/user/order/list/7" title="取消订单记录">取消订单记录</a>
      </div>
    </div><!--mymember_menu_part END-->
    <div class="mymember_menu_part">
      <a class="mymember_menu_tit" title="我的收藏"><img src="/client/images/mymember/menu02.png" />我的收藏</a>
      <div>
        <a href="/user/collect/list" title="收藏商品">收藏的商品</a>
        <a href="/user/recent/list" title="浏览历史">浏览历史</a>
      
      </div>
    </div><!--mymember_menu_part END-->
    <div class="mymember_menu_part">
      <a class="mymember_menu_tit"  title="设置"><img src="/client/images/mymember/menu05.png" />设置</a>
      <div>
        <a href="/user/info" title="个人信息">个人信息</a>
        <a href="/user/address/list" title="收货地址">收货地址</a>
        <a href="/user/password" title="修改密码">修改密码</a>
      </div>
    </div><!--mymember_menu_part END-->
  </div><!--mymember_menu END-->