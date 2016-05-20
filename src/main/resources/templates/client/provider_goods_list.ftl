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
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${dg.id?c}">${dg.goodsTitle}</p>
                             </a>
                             <input type="hidden" value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}" />  
                        </td>
                        <td class="tb01">￥<span id="price${dg.id?c}">${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span id="code${dg.id?c}">${dg.code!''}</span></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber}</span></td>
                        <td>
                                <p><a href="javascript:goodsOnSale(false,${dg.id?c},${page})">取消批发</a></p>
                                <p><a href="javascript:showSubForm(${dg.id?c},${page})">编辑信息</a></p>
                      </tr>
                 <#else>
                       <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${dg.id?c}">${dg.goodsTitle}</p>
                             </a>
                             <input type="hidden" value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}" />  
                        </td>
                        <td class="tb01">￥<span id="price${dg.id?c}">${dg.outFactoryPrice?string('0.00')}</span></td>
                        <td class="tb02"><span id="code${dg.id?c}">${dg.code!''}</span></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber}</span></td>
                        <td>
                                <p><a href="javascript:goodsOnSale(true,${dg.id?c},${page})">继续批发</a></p>
                                <p><a href="javascript:showSubForm(${dg.id?c},${page})">编辑信息</a></p>
                                <p><a href="javascript:deleteGoods(false,${dg.id?c},${page})">删除</a></p>
                      </tr>
                 </#if>
            </#list>
        </#if>
</table>
<script type="text/javascript">

function showSubForm(pgId,page){
    var price = $("#price"+pgId).html();
    var title = $("#title"+pgId).html();
    var subTitle = $("#subTitle"+pgId).val();
    var code = $("#code"+pgId).html();
    var number = $("#number"+pgId).html();
    
    $("#goodsId").attr("value",pgId);
    $("#page").attr("value",page);
    $("#goodsTitle").attr("value",title);
    $("#subTitle").attr("value",subTitle);
    $("#code").attr("value",code);
    $("#outFactoryPrice").attr("value",price);
    $("#leftNumber").attr("value",number);
    $('.sub_form').css('display','block')
}
</script>
