<#if relevance_list??>
<h3>关联商品列表</h3>
<div style="max-height:300px;overflow:auto;">
          <table class="car_list">
            <tbody>
                <tr>
                    <th width="80"></th>
	                <th width="200">商品名称</th>
	                <th width="200">副标题</th>
	                <th>编码</th>
	                <th>售价</th>
	                <th>库存</th>
	                <th>操作</th>
                 </tr>
                  <#list relevance_list as goods>
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
                            <p><a onclick="deleteRelevance(${goodsId?c},${goods.id?c});">解除关联</a></p>
                        </td>
                  </tr> 
               	</#list>
            </tbody>
          </table>
        </div>
        <div class="clear"></div>
</#if>