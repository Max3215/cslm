<div id="record_list" >
    <div class="deal_list">
        <table>
            <thead>
                <tr>
                    <td>买家</td>
                    <td>款式/型号</td>
                    <td>数量</td>
                    <td>时间</td>
                </tr>
            </thead>
            <tbody>
                <#if bargain_record_page?? && bargain_record_page.content?size gt 0 >
                    <#list bargain_record_page.content as item>
                        <#if item.orderGoodsList?? && item.orderGoodsList?size gt 0>
                            <#list item.orderGoodsList as og>
                                <#if goods??>
                                    <#if og.goodsId?c == dis_goods.id?c>
                                        <tr>
                                            <td>${item.username!''}</td>
                                            <td>${og.selectOneValue!''}</td>
                                            <td>${og.quantity!'0'}</td>
                                            <td>${item.orderTime!''}</td>
                                        </tr>
                                    </#if>
                                </#if>
                            </#list>
                        </#if>
                    </#list>
                </#if>
            </tbody>
        </table>
    </div>
    <#if bargain_record_page??>
    <div class="pages">
        <span>共${bargain_record_page.content?size!''}条记录&nbsp;&nbsp;&nbsp;&nbsp;${bargain_record_page.number+1}/${bargain_record_page.totalPages!'0'}页</span>
        <#assign continueEnter=false>
        <#if bargain_record_page?? && bargain_record_page.number+1 == 1>
            <a  href="javascript:;">上一页</a>
        <#else>
            <a href="javascript:getBargainRecord(${goodsId},  ${bargain_record_page.number-1});">上一页</a>
        </#if>
        
        <#if bargain_record_page.totalPages gt 0>
            <#list 1..bargain_record_page.totalPages as page> 
                <#if page <= 3 || (bargain_record_page.totalPages-page) < 3 || (bargain_record_page.number+1-page)?abs<3 >
                    <#if page == bargain_record_page.number+1>
                        <a class="sel" href="javascript:;"">${page}</a>
                    <#else>
                        <a href="javascript:getBargainRecord(${goodsId}, ${page-1});">${page}</a>
                    </#if>
                    <#assign continueEnter=false>
                <#else>
                    <#if !continueEnter>
                        <span> ... </span>
                        <#assign continueEnter=true>
                    </#if>
                </#if>
            </#list>
        </#if>
        
        <#if bargain_record_page.number+1 == bargain_record_page.totalPages || bargain_record_page.totalPages==0>
            <a href="javascript:;">下一页</a>
        <#else>
            <a  href="javascript:getBargainRecord(${goodsId}, ${bargain_record_page.number+1});">下一页</a>
        </#if>
    </div>
    </#if>
</div>