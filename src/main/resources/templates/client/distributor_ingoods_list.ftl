    <div class="mymember_order_search"> 
        <h3>批发商的商品</h3>
        <form action="/distributor/goods/list" id="form">
        <input type="hidden" value="${page!'0'}" name="page"/>
        <input class="mysub" type="submit" value="查询" />
        <#--
        <p class="fr pl10 c3">价格&nbsp;&nbsp;<input type="text" style="width:50px;">&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" style="width:50px;"></p>
        <input class="mytext" type="text" onFocus="if(value=='批发商') {value=''}" onBlur="if (value=='') {value='批发商'}"  value="批发商" style="width:150px;" />
        -->
        <input class="mytext" type="text" name="keywords" value="${keywords!''}" id="keywords"/>
        <select  id="providerId" name="providerId" class="myselect" onchange="searchGoods()">
            <option value="">选择批发商</option>
            <#if provider_list??>
                <#list provider_list as c>
                    <option value="${c.id?c}" <#if providerId?? && providerId==c.id>selected="selected"</#if>>${c.title!""}</option>
                </#list>
            </#if>
        </select>
        </form>
        <div class="clear"></div>
    </div>
    <table>
            <tr class="mymember_infotab_tit01">
                <th width="50"></th>
                <th width="300">商品名称</th>
                <th width="170">批发商</th>
                <th>批发价</th>
                <th>库存</th>
                <th>操作</th>
             </tr>
            <#if proGoods_page??>
                <#list proGoods_page.content as pgoods>
                    <input id="shopReturnRation${pgoods.id?c}" type="text" value="<#if pgoods.shopReturnRation??>${pgoods.shopReturnRation?string('0.00')}<#else>0</#if>" style="display:none">
                    <tr id="tr_1424195166">
                        <td colspan="2">
                            <a target="_blank" ><strong><img width="80" height="80" src="${pgoods.goodsCoverImageUri!''}"  /></strong>
                            <p class="fr" style="width:170px;text-align:left;padding-top:20px;" id="goodsTitle${pgoods.id?c}">${pgoods.goodsTitle!''}</p></a> 
                        </td>
                        <td class="tb01"><span id="providerTitle${pgoods.id?c}">${pgoods.providerTitle!''}</span></td>
                        <td class="tb02">￥<span id="outFactoryPrice${pgoods.id?c}">${pgoods.outFactoryPrice?string('0.00')}</span></td>
                        <td><span id="number${pgoods.id?c}">${pgoods.leftNumber?c!'0'}</span></td>
                        <td>
                            <p><a href="javascript:showSub(${pgoods.id?c});">添加</a></p>
                            <p><a href="javascript:collect(${pgoods.id?c});">收藏</a></p>
                        </td>
                     </tr>
                </#list>
            </#if>
        </table>
        <div class="myclear" style="height:10px;"></div>
            <div class="mymember_page"> 
            <#if proGoods_page??>
                <#assign continueEnter=false>
                <#if proGoods_page.totalPages gt 0>
                    <#list 1..proGoods_page.totalPages as page>
                        <#if page <= 3 || (proGoods_page.totalPages-page) < 3 || (proGoods_page.number+1-page)?abs<3 >
                            <#if page == proGoods_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/goods/list?keywords=${keywords!''}&page=${page-1}&providerId=${providerId!''}">${page}</a>
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

function showSub(pid)
{
    var leftNumber =$("#number"+pid).html();
    if(undefined == leftNumber || leftNumber == 0){
        alert("该商品已无存货！")
        return;
    }
    var goodsTitle = $("#goodsTitle"+pid).html();
    var providerTitle = $("#providerTitle"+pid).html();
    var outFactoryPrice = $("#outFactoryPrice"+pid).html();
    
    $("#goodsId").attr("value",pid);
    $("#goodsTitle").attr("value",goodsTitle);
    $("#providerTitle").attr("value",providerTitle);
    $("#outFactoryPrice").attr("value",outFactoryPrice);
    $("#leftNumber").attr("value",leftNumber);
    
    $('.sub_form').css('display','block');
}
 
function searchGoods(){
    $("#form").submit();
}  
    
function subDistribution(){
    var goodsId = $("#goodsId").val();
    var goodsTitle = $("#goodsTitle").val();
    var goodsPrice = $("#goodsPrice").val();
    
    if(undefined == goodsTitle || ""==goodsTitle)
    {
        alert("请输入商品标题");
        return;
    }
     var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
        alert("请输入商品销售价");
        return ;
    }
    
    $.ajax({
        type : "post",
        url : "/distributor/isDisbution",
        data : {"proGoodsId":goodsId,"goodsTitle":goodsTitle,"goodsPrice":goodsPrice,},
        dataType : "json",
        success:function(data){
            $('.sub_form').css('display','none');
            alert(data.msg);
        }
    })
}
</script>
        
