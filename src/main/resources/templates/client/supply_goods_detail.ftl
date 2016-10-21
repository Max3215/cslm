<p class="tit">商品上架<a href="javascriput:void(0);" onclick="$('.sub_form').css('display','none')">×</a></p>
<div class="info_tab">
  <table>
    <tr>
      <th><b class="fs14">基本信息</b></th>
      <td></td>
    </tr>
    <input id="sup_goodsId" type="hidden" value="<#if sup_goods??>${sup_goods.id?c}</#if>">
    <input id="goodsId" type="hidden" value="<#if goodsId??>${goodsId?c}</#if>">
    <tr>
      <th>商品名称：</th>
      <td><input type="text" readonly="readonly" value="<#if sup_goods??>${sup_goods.goodsTitle!''}<#elseif goods??>${goods.title!''}</#if>" class="add_width"></td>
    </tr>
    <tr>
      <th>商品副名称：</th>
      <td><input type="text"  value="<#if sup_goods??>${sup_goods.subGoodsTitle!''}<#elseif goods??>${goods.subTitle!''}</#if>" class="add_width" id="subGoodsTitle" ></td>
    </tr>
    <tr>
      <th>商品编码：</th>
      <td><input type="text" value="<#if sup_goods??>${sup_goods.code!''}<#elseif goods??>${goods.code!''}</#if>" class="add_width" id="code"></td>
    </tr>
    <tr>
      <th>*市场价：</th>
      <td><input type="text" name="goodsMarketPrice" value="<#if sup_goods?? && sup_goods.goodsMarketPrice??>${sup_goods.goodsMarketPrice?string('0.00')}<#elseif goods??>${goods.marketPrice?string('0.00')}</#if>" id="marketPrice" ></td>
    </tr>
     <tr>
      <th>*商品售格：</th>
      <td>
      <input type="text" value="<#if sup_goods?? && sup_goods.outFactoryPrice??>${sup_goods.outFactoryPrice?string('0.00')}</#if>" id="goodsPrice">
      &emsp;单位：<input type="text" value="<#if sup_goods??>${sup_goods.unit!''}<#elseif goods??>${goods.unit!''}</#if>" name="unit" id="unit"></td>
      </td>
    </tr>
     <tr>
      <th>库存：</th>
      <td><input type="text" value="<#if sup_goods??>${sup_goods.leftNumber?c!'0'}</#if>" id="leftNumber" onkeyup="value=value.replace(/[^0-9]/g,'')"></td>
    </tr>
    <tr>
      <th>*分销比例：</th>
      <td><input type="text" name="shopReturnRation" value="<#if sup_goods?? && sup_goods.shopReturnRation??>${sup_goods.shopReturnRation?string('0.00')}</#if>"  id="shopReturnRation"></td>
    </tr>
    <tr>
      <th><b class="fs14">规格</b></th>
      <td>
        <div class="add_specifications" id="specifica_div">
          
        </div>
      </td>
    </tr>

    <tr>
      <th></th>
      <td><input type="submit" class="sub" onclick="editSaveGoods();" value="确认提交"></td>
    </tr>
  </table>
</div>

<script type="text/javascript">
	<#if goodsId??>
	$(function(){
	    searchSpecifica(${goodsId?c},null);
	});
	</#if>
</script>