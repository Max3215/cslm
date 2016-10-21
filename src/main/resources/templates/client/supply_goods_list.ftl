<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">销售价</th>
        <th>佣金比例</th>
        <#if !dir?? || dir==2>
        <th><a href="/supply/goods/list/${isDistribution?c}?dir=1<#if categoryId??>&categoryId=${categoryId?c}</#if>">库存↑↓</a></th>
        <#else>
        <th><a href="/supply/goods/list/${isDistribution?c}?dir=2<#if categoryId??>&categoryId=${categoryId?c}</#if>">库存↑↓</a></th>
        </#if>
        <th>操作</th>
     </tr>
      <#if supply_goods_page?? && supply_goods_page.content?size gt 0>
           <#list supply_goods_page.content as dg>
                <#if dg.isDistribution>      
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check"/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;"><span id="title${dg.id?c}">${dg.goodsTitle}</span></p>
                             </a> 
                             <input type="hidden" value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}" /> 
                             <input type="hidden" value="${dg.code!''}" id="code${dg.id?c}" />
                             <input type="hidden" value="${dg.unit!''}" id="unit${dg.id?c}" />
                             <input type="hidden" value="<#if dg.goodsMarketPrice??>${dg.goodsMarketPrice?string('0.00')}</#if>" id="marketPrice${dg.id?c}">
                        </td>
                        <td class="tb01">￥<span id="price${dg.id?c}">${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span id="ration${dg.id?c}">${dg.shopReturnRation!'0'}</span></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber?c}</span></td>
                        <td>
                                <p><a href="javascript:goodsAudit(false,${dg.id?c},${page})">取消分销</a></p>
                                <p><a onclick="editGoods(${dg.id?c},${dg.goodsId?c});">修改信息</a></p>
                                <p><a onclick="deleteGoods(${dg.id?c})">删除</a></p>
                      </tr>
                 <#else>
                       <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check"/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;"><span id="title${dg.id?c}">${dg.goodsTitle}</span></p>
                             </a> 
                             <input type="hidden" value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}" /> 
                             <input type="hidden" value="${dg.code!''}" id="code${dg.id?c}" />
                             <input type="hidden" value="${dg.unit!''}" id="unit${dg.id?c}" />
                             <input type="hidden" value="<#if dg.goodsMarketPrice??>${dg.goodsMarketPrice?string('0.00')}</#if>" id="marketPrice${dg.id?c}">
                        </td>
                        <td class="tb01">￥<span id="price${dg.id?c}">${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span id="ration${dg.id?c}">${dg.shopReturnRation!'0'}</span></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber?c}</span></td>
                        <td>
                                <p><a href="javascript:goodsAudit(true,${dg.id?c},${page})">上架分销</a></p>
                               <p><a onclick="editGoods(${dg.id?c},${dg.goodsId?c});">修改信息</a></p>
                                <p><a href="javascript:deleteGoods(false,${dg.id?c},${page})">删除</a></p>
                      </tr>
                 </#if>
            </#list>
        </#if>
</table>
