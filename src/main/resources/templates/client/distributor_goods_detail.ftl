<p class="tit">商品上架<a href="javascriput:void(0);" onclick="$('.sub_form').css('display','none')">×</a></p>
<div class="info_tab">
  <table>
    <tr>
      <th><b class="fs14">基本信息</b></th>
      <td></td>
    </tr>
    <input id="dis_goodsId" type="hidden" value="<#if dis_goods??>${dis_goods.id?c}</#if>">
    <input id="goodsId" type="hidden" value="<#if goodsId??>${goodsId?c}</#if>">
    <tr>
      <th>商品名称：</th>
      <td><input type="text" readonly="readonly" value="<#if dis_goods??>${dis_goods.goodsTitle!''}<#elseif goods??>${goods.title!''}</#if>" class="add_width"></td>
    </tr>
    <tr>
      <th>商品副名称：</th>
      <td><input type="text"  value="<#if dis_goods??>${dis_goods.subGoodsTitle!''}<#elseif goods??>${goods.subTitle!''}</#if>" class="add_width" id="subGoodsTitle" ></td>
    </tr>
    <tr>
      <th>商品编码：</th>
      <td><input type="text" value="<#if dis_goods??>${dis_goods.code!''}<#elseif goods??>${goods.code!''}</#if>" class="add_width" id="code"></td>
    </tr>
    <tr>
      <th>*实体店价：</th>
      <td><input type="text" name="goodsMarketPrice" value="<#if dis_goods?? && dis_goods.goodsMarketPrice??>${dis_goods.goodsMarketPrice?string('0.00')}<#elseif goods??>${goods.marketPrice?string('0.00')}</#if>" id="marketPrice" ></td>
    </tr>
     <tr>
      <th>*商品售价：</th>
      <td><input type="text" value="<#if dis_goods?? && dis_goods.goodsPrice??>${dis_goods.goodsPrice?string('0.00')}</#if>" id="goodsPrice"></td>
    </tr>
    <tr>
          <th>标签：</th>
          <td>
                <input type="hidden" value="<#if dis_goods??>${dis_goods.tagId!'0'}</#if>" id="tagId" >
                <a class="type  <#if !dis_goods?? || !dis_goods.tagId?? || dis_goods.tagId==0>sel</#if>" onclick="sheckTag($(this),null)">无标签</a>
                <#if tag_list??>
                <#list tag_list as tag>
                <a class="type <#if dis_goods?? && dis_goods.tagId?? && dis_goods.tagId==tag.id>sel</#if>" onclick="sheckTag($(this),${tag.id?c})" >${tag.title!''}</a>
                </#list>
                </#if>
          </td>
        </tr>
    <tr>
      <th>单位：</th>
      <td><input type="text" value="<#if dis_goods??>${dis_goods.unit!''}<#elseif goods??>${goods.unit!''}</#if>" id="unit"></td>
    </tr>
     <tr>
      <th>库存：</th>
      <td><input type="text" value="<#if dis_goods??>${dis_goods.leftNumber?c!'0'}</#if>" id="leftNumber" onkeyup="value=value.replace(/[^0-9]/g,'')"></td>
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
<style>
.sub_form table td .type{padding: 4px 10px;
    border: 1px solid #ddd;
    margin-right: 10px;color:#666;}
.sub_form table td .type.sel{border-color:#ff5b7d;color:#ff5b7d;}
</style>