<#if goods_page?? && goods_page.content?size gt 0>
    <#list goods_page.content as goods>
            <#--<a href="/touch/goods/${goods.id?c}" class="a1">
                <img src="${goods.coverImageUri!''}"/>
                <p>${goods.goodsTitle!""}</p>
                <p >￥${goods.goodsPrice?string("#.##")}<#if goods.unit?? && goods.unit != ''><span>/${goods.unit!''}</span></#if></p>
            </a>-->
            <li>
                <#if goods.tagId??><i class="icon"><img src="${goods.tagImg!''}" /></i></#if>
			        <a href="/touch/goods/${goods.id?c}"><img src="${goods.coverImageUri!''}" /></a>
			        <a href="/touch/goods/${goods.id?c}" class="name">${goods.goodsTitle!""}</a>
			        <p class="price">¥ ${goods.goodsPrice?string("0.00")}<#if goods.unit?? && goods.unit != ''><span>/${goods.unit!''}</span></#if></p>
			        <div class="bot">
			         <#if goods.isDistribution?? && goods.isDistribution == true>
				          <i><img src="/touch/images/yg_icon.png" /></i>
				          <a href="javascript:;" onclick="addCart(${goods.id?c})" class="yg">加入购物车</a>
			         <#else>
			         	<a href="javascript:;" onclick="addCart(${goods.id?c})">加入购物车</a>
			         </#if>
			        </div>
		        </li>
    </#list>
</#if> 
