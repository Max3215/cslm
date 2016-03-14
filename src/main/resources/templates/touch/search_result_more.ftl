<#if goods_page??>
    <#list goods_page.content as goods>
         <a href="/touch/goods/${goods.id?c}" class="a1">
            <img src="${goods.coverImageUri!''}"/>
            <p>${goods.goodsTitle!""}</p>
            <p >￥${goods.goodsPrice?string("#.##")}</p>
        </a>
    </#list>
</#if>