<#if cart_goods_list?? && cart_goods_list?size gt 0>
    <section class="cart_list">
      <ul>
        <#assign allChecked=true >
        <#assign totalGoods=0>
        <#assign totalPrice=0>
        
        <#list cart_goods_list as cg>
            <li>
                <a href="javascript:;" onclick="toggleSelect(${cg.id?c});"  class="choose <#if cg.isSelected?? && cg.isSelected>sel<#else><#assign allChecked=false ></#if>"></a>
                <a href="/touch/goods/${cg.distributorGoodsId!''}" class="pic"><img src="${cg.goodsCoverImageUri!''}" /></a>
                <a href="/touch/goods/${cg.distributorGoodsId!''}" class="name">${cg.goodsTitle!''}</a>
                <p>价格：￥${cg.price?string("0.00")}</p>
                <div class="num">
                  <a href="javascript:minusNum(${cg.id?c})" class="aj">-</a>
                  <input class="text" type="text" value="${cg.quantity!''}" />
                  <a href="javascript:addNum(${cg.id?c});" class="aj">+</a>
                  <a href="javascript:delCartItem(${cg.id?c});" class="del">删除</a>
                </div>
                <div class="clear"></div>
              </li>
              <#if cg.isSelected>
                <#assign totalGoods=totalGoods+cg.quantity>
                <#assign totalPrice=totalPrice+cg.price*cg.quantity>
              </#if>
            </li>
        </#list>
      </ul>
    </section>

  <div style="height:0.58rem;"></div>
  <section class="cart_foot">
    
    <a <#if allChecked>class="choose sel" href="javascript:toggleAllSelect(1);"<#else>class="choose" href="javascript:toggleAllSelect(0);"</#if>>全选</a>
  <#--   <a href="javascript:void(0)" class="choose <#if allChecked>" onclick="$(this).toggleClass('sel');">全选</a>
   <a href="#" class="del">删除选中的商品</a>-->
    <p>合计：<span>¥<#if cart_goods_list??>${totalPrice?string("0.00")}</#if></span></p>
    <a href="javascript:goNext(${totalGoods!0});" class="btn">去结算</a>
  </section>
<#else>
     <section class="cart_empty">
        <p>购物车是空的，去看看心仪的产品吧！</p>
        <a href="/touch">马上去购物&gt;&gt;</a>
      </section>
</#if>
