<#if cateList?? && cateList?size gt 0>
<select  name="parent" id="twoCat">
    <option value="" <#if cat?? && cat.parentId?? && cat.parentId==0>selected="selected"</#if>>无父级分类</option>
	   <#list cateList as c>
	       <option value="${c.id?c!""}" <#if cat?? && cat.parentId?? && cat.parentId == c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
	   </#list>
</select>
</#if>