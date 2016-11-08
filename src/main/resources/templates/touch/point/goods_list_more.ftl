<#if goods_page?? && goods_page.content?size gt 0>
        <#list goods_page.content as goods>
          <li>
            <i><img src="images/jing_icon.png" /></i>
            <a href="/touch/point/goods/detail?id=${goods.id?c}"><img src="${goods.imgUrl!''}" /></a>
            <a href="/touch/point/goods/detail?id=${goods.id?c}" class="name">${goods.goodsTitle!''}</a>
            <p class="jf_num">${goods.point!'0'}分</p>
            <a onclick="addPoint(${goods.id?c})" class="dh_btn">立即兑换</a>
          </li>
      </#list>
      </#if> 