<#if goodsPage??>
<#list goodsPage.content as item>
 <a href="/touch/goods/${item.id?c!''}">
    <img src="${item.coverImageUri!''}" />
    <p class="name">${item.goodsTitle!''}</p>
    <p class="price">¥ ${item.goodsPrice?string('0.00')}<#if item.unit??><span>/${item.unit!''}</span></#if></p>
  </a>
</#list>
</#if>
