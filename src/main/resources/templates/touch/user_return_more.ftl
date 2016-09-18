<#if return_page??>
<#list return_page.content as return>
<section class="order_list">
    <ul>
      <p class="number">订单号：${return.orderNumber!''}<span></span></p>
      <li>
        <a href="/touch/goods/${return.goodsId?c!''}" class="pic"><img src="${return.goodsCoverImageUri!''}"></a>
        <div class="info">
          <a href="/touch/goods/${return.goodsId?c!''}">${return.goodsTitle!''}</a>
          <p>价格：￥<#if return.goodsPrice??>${return.goodsPrice?string('0.00')}</#if></p>
          <p>数量：${return.returnNumber!'0'}</p>
          <p>申请时间：${return.returnTime!''}</p>
        </div>
        <div class="clear"></div>
      </li>
    </ul>
    <div class="btns">
      <span><#if return.statusId==0>待审核<#else>已审核</#if></span>
    </div>
</section>
</#list>
</#if>