    <table>
            <tr class="mymember_infotab_tit01">
                <th width="50"><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
                <th width="300">商品名称</th>
                <th width="170">分销商</th>
                <th>分销价</th>
                <th>返利比</th>
                <th>库存</th>
                <th>操作</th>
             </tr>
            <#if proGoods_page??>
                <#list proGoods_page.content as pgoods>
                     <tr id="tr_1424195166">
                        <td width=10>
                            <input id="yu_1424195166" name="listChkId" type="checkbox" value="${pgoods_index}" class="check""/>
                            <input type="hidden" name="listId" id="listId" value="${pgoods.id?c}">
                        </td>
                        <td>
                            <a href="javascript:;" target="_blank"  class="pic"><strong><img width="80" height="80" src="${pgoods.goodsCoverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${pgoods.goodsTitle!''}</p>
                             </a> 
                        </td>
                        <td class="tb01">${pgoods.providerTitle!''}</td>
                        <td class="tb02">￥${pgoods.outFactoryPrice?string('0.00')}</td>
                        <td>${pgoods.shopReturnRation?string('0.00')}</td>
                        <td>${pgoods.leftNumber!'0'}</td>
                        <td>
                            <#assign isSale = false>
                            <#if goodsList??>
                            <#list goodsList.content as dg>
                                <#if dg.goodsId == pgoods.goodsId >
                                    <#assign isSale = true>
                                    <p><a href="javascript:;" style="color: #ff5b7d">超市已存在</a></p>        
                                </#if>
                             </#list>
                             </#if>
                             <#if isSale == false>
                            <p><a href="javascript:supply(${pgoods.id?c});">我要代理</a></p>
                            </#if>
                            <p><a href="javascript:collect(${pgoods.id?c});">收藏</a></p>
                            </td>
                      </tr>
                </#list>
            </#if>
        </table>
        
        
<script type="text/javascript">

function collect(pgId){
    if (undefined == pgId)
    {
        return;
    }
    $.ajax({
        type : "post",
        url : "/distributor/collect/add",
        data : {"pgId":pgId},
        dataType : "json",
        success:function(res){
            alert(res.msg);
            
            if (res.code==1)
            {
                setTimeout(function(){
                    window.location.href = "/login";
                }, 1000); 
            }
        }
    });
    
}
 
function searchGoods(){
    $("#form").submit();
}  
    
function supply(goodsId){
    $.ajax({
        type : "post",
        url : "/distributor/supply",
        data : {"proGoodsId":goodsId},
        dataType : "json",
        success:function(data){
            alert(data.msg);
            if(data.code ==0 ){
                window.location.reload();
            }
        }
    })
}
</script>
        
