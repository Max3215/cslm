<#if collect_page??>
    <#list collect_page.content as cg>
    <#if cg.isSelect?? && cg.isSelect == false>
        <#assign allChecked=false >
    </#if>     
      <li>
        <a href="/touch/user/collect/select?id=${cg.id?c}" class="choose <#if cg.isSelect?? && cg.isSelect==true>sel</#if>" ></a>
        <a href="/touch/goods/${cg.distributorId?c}" class="pic"><img src="${cg.goodsCoverImageUri!''}" /></a>
        <a href="/touch/goods/${cg.distributorId?c}" class="name">${cg.goodsTitle!''}</a>
        <p>价格：￥${cg.goodsSalePrice?string("0.00")}</p>
        <a href="/touch/user/collect/del?id=${cg.distributorId?c!''}" class="btn">取消收藏</a>
        <div class="clear"></div>
      </li>
    </#list>
</#if>