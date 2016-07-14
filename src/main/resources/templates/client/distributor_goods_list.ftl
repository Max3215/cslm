<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">商品编码</th>
        <th>价格</th>
        <#if sort?? &&  sort==1>
        <th><a href="/distributor/goods/sale/${isOnSale?c}?keywords=${keywords!''}<#if categoryId??>&categoryId=${categoryId?c!''}</#if>&dir=2">库存↓↑</a></th>
        <#else>
        <th><a href="/distributor/goods/sale/${isOnSale?c}?keywords=${keywords!''}<#if categoryId??>&categoryId=${categoryId?c!''}</#if>&dir=1">库存↓↑</a></th>
        </#if>
        
        <th>操作</th>
     </tr>
      <#if dis_goods_page?? && dis_goods_page.content?size gt 0>
           <#list dis_goods_page.content as dg>
               <#if !dg.isDistribution>
               <#if dg.isOnSale>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <input type="hidden"  value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}">
                            <input type="hidden"  value="${dg.unit!''}" id="unit${dg.id?c}">
                            <input type="hidden"  value="${dg.code!''}" id="code${dg.id?c}">
                            <a href="" target="_blank" class="pic" title="${dg.goodsTitle!''}"><strong><img width="80" height="80" src="${dg.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${dg.id?c}">${dg.goodsTitle!''}</p>
                             </a> 
                        </td>
                        <td class="tb01">${dg.code!''}</td>
                        <td class="tb02"><p>￥<span id="price${dg.id?c}"><#if dg.goodsPrice??>${dg.goodsPrice?string('0.00')}</#if></span></p></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber!'0'}</span></td>
                        <td>
                            <p><a href="javascript:recommed(${dg.id?c},${page},'cat');" <#if dg.isRecommendCategory?? &&dg.isRecommendCategory>style="color:#ff5b7d"</#if>>分类推荐</a>
                                &nbsp;/&nbsp;
                                <a href="javascript:recommed(${dg.id?c},${page},'index');" <#if dg.isRecommendIndex?? &&dg.isRecommendIndex>style="color:#ff5b7d"</#if>>首页推荐</a>
                             </p>
                            <p><a href="javascript:editPrice(${dg.id?c},${page?c});">修改信息</a></p>
                            <p><a href="javascript:goodsOnSale(false,${dg.id?c},${page?c});">下架</a>&nbsp;/&nbsp;<a href="javascript:deleteDisGoods(true,${dg.id?c},${page?c});">删除</a></p>
                       </td>
                      </tr>
                 <#else>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index?c}" class="check"/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <input type="hidden" value="${dg.subGoodsTitle!''}" id="subTitle${dg.id?c}">
                            <input type="hidden"  value="${dg.unit!''}" id="unit${dg.id?c}">
                            <input type="hidden"  value="${dg.code!''}" id="code${dg.id?c}">
                            <a href="" target="_blank" class="pic"><strong><img width="80" height="80" src="${dg.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="title${dg.id?c}">${dg.goodsTitle!''}</p>
                             </a> 
                        </td>
                        <td class="tb01">${dg.code!''}</td>
                        <td class="tb02"><p><#if dg.goodsPrice??>￥<span id="price${dg.id?c}">${dg.goodsPrice?string('0.00')}<#else></#if></p></td>
                        <td><span id="number${dg.id?c}">${dg.leftNumber!'0'}</span></td>
                        <td>
                            <p><a href="javascript:goodsOnSale(true,${dg.id?c},${page?c});">上架</a></p>
                            <p><a href="javascript:editPrice(${dg.id?c},${page?c});">修改信息</a></p>
                            <p><a href="javascript:deleteDisGoods(false,${dg.id?c},${page?c});">删除</a></p></td>
                      </tr>
                 </#if>
                 </#if>
            </#list>
        </#if>
</table>
<script type="text/javascript">
function recommed(id,page,type)
{
    var categoryId = $("#categoryId").val();
    var key = $("#keywords").val();
    var dir = $("#sort").val();
    $.ajax({
        url : "/distributor/goods/recommed",
        data : {"id":id,"page":page,"type":type,"categoryId":categoryId,"dir":dir,"keywords":key},
        type : "post",
       success:function(res){
            $("#dis_goods_table").html(res);
        }
    })
}
</script>