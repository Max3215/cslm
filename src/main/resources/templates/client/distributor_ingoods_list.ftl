    <div class="mymember_order_search"> 
        <h3>批发商的商品</h3>
        <input class="mysub" type="submit" onclick="searchGoods(${page!'0'})" value="查询" />
        <#--
        <p class="fr pl10 c3">价格&nbsp;&nbsp;<input type="text" style="width:50px;">&nbsp;&nbsp;至&nbsp;&nbsp;<input type="text" style="width:50px;"></p>
        <input class="mytext" type="text" onFocus="if(value=='批发商') {value=''}" onBlur="if (value=='') {value='批发商'}"  value="批发商" style="width:150px;" />
        -->
        <input class="mytext" type="text"  value="${keywords!''}" id="keywords"/>
    
        <div class="clear"></div>
    </div>
    <table>
            <tr class="mymember_infotab_tit01">
                <th width="50"><input id="yu_1424195166" name="r" type="checkbox" value="1424195166" class="check" onclick="cancelCheck(1424195166)"/>全选</th>
                <th width="300">商品名称</th>
                <th width="170">批发商</th>
                <th>批发价</th>
                <th>库存</th>
                <th>操作</th>
             </tr>
            <#if proGoods_page??>
                <#list proGoods_page.content as goods>
                    <tr id="tr_1424195166">
                        <td width=10><input id="yu_1424195166" name="r" type="checkbox" value="1424195166" class="check" onclick="cancelCheck(1424195166)"/></td>
                        <td>
                            <a target="_blank" ><strong><img width="80" height="80" src="${goods.goodsCoverImageUri!''}"  /></strong>
                            <p class="fr" style="width:170px;text-align:left;padding-top:20px;">${goods.goodsTitle!''}</p></a> 
                        </td>
                        <td class="tb01">${goods.providerTitle!''}</td>
                        <td class="tb02">￥${goods.outFactoryPrice?string('0.00')}</td>
                        <td>${goods.leftNumber!'0'}</td>
                        <td>
                            <p><a href="javascript:addgoods(${goods.id?c});">添加</a></p>
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
                                <a href="/distributor/goods/list?keywords=${keywords!''}&page=${page-1}">${page}</a>
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