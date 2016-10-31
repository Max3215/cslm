<table id="dis_goods_table">
     <tr class="mymember_infotab_tit01">
        <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
        <th width="300">商品名称</th>
        <th width="170">商品编码</th>
        <th>价格</th>
        <th>库存</th>
        <th>供货商</th>
        <th>操作</th>
     </tr>
      <#if dis_goods_page?? && dis_goods_page.content?size gt 0>
           <#list dis_goods_page.content as dg>
                      <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${dg_index}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${dg.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank" class="pic" title="${dg.goodsTitle!''}"><strong><img width="80" height="80" src="${dg.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${dg.goodsTitle!''}</p>
                             </a> 
                        </td>
                        <td class="tb01">${dg.code!''}</td>
                        <td class="tb02">￥${dg.goodsPrice?string('0.00')}</td>
                        <td class="tb01">${dg.leftNumber!''}</td>
                        <td>${dg.providerTitle!''}</td>
                        <td>
                            <#--
                             <p><a href="javascript:recommed(${dg.id?c},'cat');" <#if dg.isRecommendCategory?? &&dg.isRecommendCategory>style="color:#ff5b7d"</#if>>分类推荐</a>
                                &nbsp;/&nbsp;
                                <a href="javascript:recommed(${dg.id?c},'index');" <#if dg.isRecommendIndex?? &&dg.isRecommendIndex>style="color:#ff5b7d"</#if>>首页推荐</a>
                             </p>
                             -->
                             <p><a href="javascript:recommed(${dg.id?c},'cat');" <#if dg.isRecommendType?? &&dg.isRecommendType ==true>style="color:#ff5b7d"</#if>>分类</a>
                                &nbsp;/&nbsp;
                                <a href="javascript:recommed(${dg.id?c},'index');" <#if dg.isRecommendIndex?? &&dg.isRecommendIndex==true>style="color:#ff5b7d"</#if>>推荐</a>
                             </p>
                             <p>
                                <#-- 触屏：
                            
                             	<a href="javascript:recommed(${dg.id?c},'touchcat');" <#if dg.isTouchRecommendType?? &&dg.isTouchRecommendType==true>style="color:#ff5b7d"</#if>>分类</a>
                                &nbsp;/&nbsp;
                                
                                <a href="javascript:recommed(${dg.id?c},'hot');" <#if dg.isTouchHot?? &&dg.isTouchHot==true>style="color:#ff5b7d"</#if>>精品</a>-->
                                <a href="/distributor/relevance/list?goodsId=${dg.id?c}" >关联其他</a>
                             </p>
                            <p><a onclick="deleteDisGoods(${dg.id?c})">删除</a></p></td>
                      </tr>
            </#list>
        </#if>
</table>
<script>
function recommed(id,type)
{
    $.ajax({
        url : "/distributor/goods/supplyrecommed",
        data : {"id":id,"type":type},
        type : "post",
       success:function(data){
            if(data.code == 0){
                alert(data.msg);
                window.location.href="/login";
            }else if(data.code == 1){
               window.location.reload();
            }else{
                alert(data.msg);
            }
        }
    })
}
</script>