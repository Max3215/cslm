<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">商品编码</th>
        <th>价格</th>
        <th>库存</th>
        <th>操作</th>
     </tr>
      <#if dis_goods_page?? && dis_goods_page.content?size gt 0>
           <#list dis_goods_page.content as dg>
               <#if dg.isOnSale>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="/goods/${dg.goodsId?c}" target="_blank" ><strong><img width="80" height="80" src="${dg.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${dg.goodsTitle}</p>
                             </a> 
                        </td>
                        <td class="tb01">${dg.code!''}</td>
                        <td class="tb02">￥${dg.goodsPrice?string('0.00')}</td>
                        <td>4</td>
                        <td>
                            <p><a href="javascript:goodsOnSale(false,${dg.id?c},${page});">下架</a></p>
                            <p><a href="javascript:deleteDisGoods(true,${dg.id?c},${page});">删除</a></p></td>
                      </tr>
                 <#else>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index}" class="check"/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="/goods/${dg.goodsId?c}" target="_blank" ><strong><img width="80" height="80" src="${dg.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${dg.goodsTitle}</p>
                             </a> 
                        </td>
                        <td class="tb01">${dg.code!''}</td>
                        <td class="tb02">￥${dg.goodsPrice?string('0.00')}</td>
                        <td>4</td>
                        <td>
                            <p><a href="javascript:goodsOnSale(true,${dg.id?c},${page});">上架</a></p>
                            <p><a href="javascript:deleteDisGoods(false,${dg.id?c},${page});">删除</a></p></td>
                      </tr>
                 </#if>
            </#list>
        </#if>
</table>