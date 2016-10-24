    <div class="mymember_order_search"> 
        <h3>出售的商品</h3>
        <input type="hidden" name="categoryId" id="categoryId" value="<#if category??>${category.id?c}</#if>" />
        <input type="hidden" name="page" id="page" value="" />
        <input type="hidden" name="goodsId"  value="${goodsId?c}" />
        <input class="mysub" type="button" onclick="searchRelevance(false);" value="查询" />
        <input class="mytext" style="width: 200px;" type="text" name="keywords" value="${keywords!''}" id="keywords"/>
        <select class="myselect" style="width: 130px;" id="oneCat" onchange="searchGoods('oneCat');">
            <option <#if !category??>selected="selected"</#if> value="">所有类别</option>
            <#if category_list??>
                <#list category_list as c>
                    <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                </#list>
            </#if>
        </select>
         <select class="myselect" style="width: 130px;" id="twoCat" onchange="searchGoods('twoCat','');">
            <option <#if !category??>selected="selected"</#if> value="">所有类别</option>
            <#if cateList??>
                <#list cateList as c>
                    <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                </#list>
            </#if>
        </select>
         <select class="myselect" style="width: 130px;" id="category" onchange="searchGoods('categoryId');">
            <option <#if !category??>selected="selected"</#if> value="">所有类别</option>
            <#if categoryList??>
                <#list categoryList as c>
                    <option value="${c.id?c}" <#if category?? && category.parentTree?contains("["+c.id?c+"]")>selected="selected"</#if>>${c.title!""}</option>
                </#list>
            </#if>
        </select>
        
        <div class="clear"></div>
    </div>
    <table>
            <tr class="mymember_infotab_tit01">
                <th width="80"></th>
                <th width="200">商品名称</th>
                <th width="200">副标题</th>
                <th>编码</th>
                <th>售价</th>
                <th>库存</th>
                <th>操作</th>
             </tr>
            <#if sale_page??>
                <#list sale_page.content as goods>
                    <tr>
                        <td colspan="2">
                            <a  class="pic" title="${goods.goodsTitle!''}">
                                <strong><img width="80" height="80" src="${goods.coverImageUri!''}"  /></strong>
                                <p class="fr" style="width:170px;text-align:left;padding-top:20px;" >${goods.goodsTitle!''}</p>
                             </a> 
                        </td>
                        <td><p style="text-align: left;margin:10px 0 10px 5px;max-height:60px;overflow:hidden;" >${goods.subGoodsTitle!''}</p></td>
                        <td class="tb01"><span >${goods.code!''}</span></td>
                        <td class="tb02">￥<span>${goods.goodsPrice?string('0.00')}</span></td>
                        <td><span>${goods.leftNumber?c!'0'}</span></td>
                        <td>
                            <p><a onclick="addRelevance(${goodsId?c},${goods.id?c});">添加</a></p>
                        </td>
                  </tr>
                </#list>
            </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>
            <div class="mymember_page"> 
            <#if sale_page??>
                <#assign continueEnter=false>
                <#if sale_page.totalPages gt 0>
                    <#list 1..sale_page.totalPages as page>
                        <#if page <= 3 || (sale_page.totalPages-page) < 3 || (sale_page.number+1-page)?abs<3 >
                            <#if page == sale_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="javascript:;" onclick="searchGoodPage(${page-1})">${page}</a>
                            </#if>
                            <#assign continueEnter=false>
                        <#else>
                            <#if !continueEnter>
                                <b class="pn-break">&hellip;</b>
                                <#assign continueEnter=true>
                            </#if>
                        </#if>
                    </#list>
                </#if>
                </#if>
        </div>
        
<script type="text/javascript">

function searchGoods(type){
	if(null != type && type=="oneCat")
    {
        $("#categoryId").val($("#oneCat").val());
    }else if(null != type && type=="twoCat")
    {
       $("#categoryId").val($("#twoCat").val());
    }else if(null != type && type=="categoryId")
    {
        $("#categoryId").val($("#category").val());
    }else if(null != type && type=="excel")
    {
        $("#excel").attr("value","excel");
    }
    // $("#form").submit();
    searchSaleGoods(false);
}  
function searchGoodPage(page){
	$("#page").val(page);
	searchSaleGoods(false);
}
</script>
        
