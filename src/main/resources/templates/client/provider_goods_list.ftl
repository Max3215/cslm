<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">批发价</th>
        <th>编码</th>
        <#if !dir?? || dir ==2>
        <th><a href="/provider/goods/list/${isOnSale?c}?dir=1<#if categoryId??>&categoryId=${categoryId?c}</#if>">库存↑↓</a></th>
        <#else>
        <th><a href="/provider/goods/list/${isOnSale?c}?dir=2<#if categoryId??>&categoryId=${categoryId?c}</#if>">库存↑↓</a></th>
        </#if>
        <th>操作</th>
     </tr>
      <#if provider_goods_page?? && provider_goods_page.content?size gt 0>
           <#list provider_goods_page.content as dg>
                <#if dg.isOnSale>      
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" >${dg.goodsTitle}</p>
                             </a>
                        </td>
                        <td class="tb01">￥<span>${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span>${dg.code!''}</span></td>
                        <td><span>${dg.leftNumber?c!"0"}</span></td>
                        <td>
                                <p><a onclick="goodsOnSale(false,${dg.id?c})">取消批发</a></p>
                                <p><a onclick="editGoods(${dg.id?c},${dg.goodsId?c})">编辑信息</a></p>
                      </tr>
                 <#else>
                       <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" >${dg.goodsTitle}</p>
                             </a>
                        </td>
                        <td class="tb01">￥<span>${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span>${dg.code!''}</span></td>
                        <td><span >${dg.leftNumber?c!''}</span></td>
                        <td>
                                <p><a onclick="goodsOnSale(true,${dg.id?c})">继续批发</a></p>
                                <p><a onclick="editGoods(${dg.id?c},${dg.goodsId?c})">编辑信息</a></p>
                                <p><a href="javascript:deleteGoods(${dg.id?c})">删除</a></p>
                      </tr>
                 </#if>
            </#list>
        </#if>
</table>
