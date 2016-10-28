<#if order_page??>
    <#list order_page.content as order>
  <section class="order_list" id="order_list">
    <ul>
      <a href="/touch/user/order?id=${order.id?c}"> <p class="number">订单号：${order.orderNumber!''}</p></a>
      <#list order.orderGoodsList as og>
      <li>
        <a href="/touch/goods/${og.goodsId?c}" class="pic"><img src="${og.goodsCoverImageUri!''}" /></a>
        <div class="info">
          <a href="/touch/goods/${og.goodsId?c}">${og.goodsTitle!''}</a>
          <#if og.specName??><p>规格：${og.specName!''}</p></#if>
          <p>价格：￥${og.price?string('0.00')}</p>
          <p>数量：${og.quantity!'0'}</p>
        </div>
        <div class="clear"></div>
      </li>
     </#list>
    </ul>
    <div class="btns">
      <#if order.statusId??>
      <#switch order.statusId>
      <#case 1>
           <span>待确认</span>
           <menu>
            <a href="/touch/user/cancel/direct?id=${order.id?c}" onClick="cancelConfirm()" id="">取消订单</a>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 2>
           <span>待付款</span>
           <menu>
                <a href="/touch/user/cancel/direct?id=${order.id?c}" onClick="cancelConfirm()" id="">取消订单</a>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
                <a href="/touch/order/dopay/${order.id?c}" class="cur">去付款</a>
           </menu>
      <#break>
      <#case 3>
           <span>待发货</span>
           <menu>
           <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 4>
           <span>待收货</span>
           <menu>
                <a href="javascript:orderReceive(${order.id?c});" onClick="receiveConfirm()" class="cur">确认收货</a>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 5>
           <span>待评价</span>
           <menu>
                <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
                <a href="/touch/user/comment/list?keywords=${order.orderNumber!''}" class="cur">去评价</a>
           </menu>
      <#break>
      <#case 6>
           <span>已完成</span>
           <menu>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 7>
           <span>已取消</span>
           <menu>
            <a href="/touch/user/order?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#default>
            <span>支付取消</span>
           <menu>
           </menu>
        </#switch>
      </#if>
    </div>
  </section>
   </#list>
   </#if>