<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">批发价</th>
        <th>佣金比例</th>
        <th>库存</th>
        <th>操作</th>
     </tr>
      <#if provider_goods_page?? && provider_goods_page.content?size gt 0>
           <#list provider_goods_page.content as dg>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="" target="_blank" ><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${dg.goodsTitle}</p>
                             </a> 
                        </td>
                        <td class="tb01">￥${dg.outFactoryPrice?string('0.00')}</td>
                        <td class="tb02">${dg.shopReturnRation!'0'}</td>
                        <td>${dg.leftNumber}</td>
                        <td>
                            <p><a href="javascript:deletegoods(${dg.id?c},${page})">取消批发</a></p>
                      </tr>
            </#list>
        </#if>
</table>
