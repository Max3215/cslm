<ul class="list">
    <#if spec_list??>
    <#list spec_list as sp>
    <li>
      <p><span>属性：${sp.specifict!''}</span>&nbsp;&nbsp;<span>库存：${sp.leftNumber!''}</span></p>
      <a href="javascript:;" onclick="searchSpecifica(${sp.goodsId?c},${sp.id?c})" class="edit"></a>
      <a href="javascript:;"  onclick="delSpecifica(${sp.goodsId?c},${sp.id?c})" class="del">&times;</a>
    </li>
    </#list>
    </#if>
  </ul>
<div class="show">
	<form action="/distributor/specifica/save" method="post" id="spec_form">
	<input type="hidden" name="id" value="<#if specifica??>${specifica.id?c}</#if>">
	<input type="hidden" name="goodsId" value="${goodsId?c}">
	<input type="hidden" name="type" value="<#if specifica??>${specifica.type}</#if>">
	<input type="hidden" name="shopId" value="<#if specifica??>${specifica.shopId?c}</#if>">
	<label>属性：<input type="text" class="text" name="specifict" value="<#if specifica??>${specifica.specifict!''}</#if>"/></label>
	<label>库存：<input type="text" class="text lit" value="<#if specifica??>${specifica.leftNumber!'0'}</#if>" name="leftNumber" id="leftNumber" onfocus="if(value=='1'||value=='0') {value='1'}" onblur="checkNumber(this.value)" onkeyup="value=value.replace(/[^0-9]/g,'')"/></label>
	<a href="javascript:;" onclick="saveSpecifict()" class="btn">保存</a>
</div>
<script>
function checkNumber(num)
{
    if (num==''|| num=='0') 
    {
        $("#leftNumber").val(1);
        return ;
    }
}
</script>