<p class="tit">进货清单<a  onclick="$('.sub_form').css('display','none')">×</a></p>
    <div class="info_tab">
      <table>
        <tr>
          <th>商品名称：</th>
          <td>
          		<input type="text" class="add_width" name="goodsTitle" value="<#if proGoods??>${proGoods.goodsTitle!''}</#if>" readonly="readonly">
          </td>
        </tr>
        <tr>
          <th>商品副标题：</th>
          <td><input type="text" class="add_width" name="subGoodsTitle" value="<#if proGoods??>${proGoods.subGoodsTitle!''}</#if>" readonly="readonly"></td>
        </tr>
         <tr>
          <th>批发价：</th>
          <td><input type="text" name="goodsMarketPrice" value="<#if proGoods??>${proGoods.goodsMarketPrice?string('0.00')}</#if>" readonly="readonly" id="outFactoryPrice" ></td>
        </tr>
         <tr>
          <th>供货商：</th>
          <td><input type="text" name="providerTitle" class="add_width" value="<#if proGoods??>${proGoods.providerTitle!''}</#if>" readonly="readonly" id="providerTitle"></td>
        </tr>
        </tr>
        <#if specList?? && specList?size gt 0>
         <tr>
          <th>规格：</th>
          <td>
          		<#list specList as sp>
          		<a class="type" onclick="sheckSpec($(this),${sp.id?c})">${sp.specifict!''}</a>
          		</#list>
          </td>
        </tr>
        <input type="hidden" value="true" id="isSpec">
		<#else>
		<input type="hidden" value="false" id="isSpec">
        </#if>
        <input type="hidden" value="" id="specId">
        <tr>
          <th>进货数量：</th>
          <td>
        	<input type="hidden" value="<#if proGoods??>${proGoods.leftNumber?c!'0'}</#if>" id="leftNumber">  
            <input type="number" name="shopReturnRation" min="1" max="<#if proGoods??>${proGoods.leftNumber?c!'0'}</#if>"  id="quantity"  onblur="checkNumber(this.value)">
      		<label id="left_label">库存：<#if proGoods??>${proGoods.leftNumber?c!'0'}</#if></label>
          </td>
        </tr>
        <tr>
          <th></th>
          <td><input type="submit" class="sub" onclick="addGoods(<#if proGoods??>${proGoods.id?c}</#if>);" value="确认提交"></td>
        </tr>
      </table>
</div>
<style>
.sub_form table td .type{padding: 4px 10px;
    border: 1px solid #ddd;
    margin-right: 10px;color:#666;}
.sub_form table td .type.sel{border-color:#ff5b7d;color:#ff5b7d;}
</style>


