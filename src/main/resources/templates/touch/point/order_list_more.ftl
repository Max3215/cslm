<#if order_page??>
    <#list order_page.content as order>
    <section class="order_list" >
        <ul>
        <a href="/touch/user/point/order/detail?id=${order.id?c}"> <p class="number">订单号：${order.orderNumber!''}</p></a>
      <li>
        <a href="/touch/point/goods/detail?id=${order.pointId?c}" class="pic"><img src="${order.goodsImg!''}" /></a>
        <div class="info">
          <a href="/touch/point/goods/detail?id=${order.pointId?c}">${order.goodsTitle}</a>
          <p>积分：${order.point}</p>
        </div>
        <div class="clear"></div>
      </li>
    </ul>
    <div class="btns">
      <#if order.statusId??>
      <#switch order.statusId>
      <#case 1>
           <span>待发货</span>
           <menu>
            <a onclick="orderFinish(${order.id?c},4)" class="cur">取消兑换</a>
            <a href="/touch/user/point/order/detail?id=${order.id?c}">查看订单</a>
           </menu>
      <#break>
      <#case 2>
           <span>待收货</span>
           <menu>
                <a  onclick="orderFinish(${order.id?c},3)" class="cur">确认收货</a>
                <a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 3>
           <span>完成</span>
           <menu>
           <a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
      <#case 4>
           <span>已取消</span>
           <menu>
                <<a href="/touch/user/point/order/detail?id=${order.id?c}" id="">查看订单</a>
           </menu>
      <#break>
        </#switch>
      </#if>
    </div>
  </section>
   </#list>
   </#if>