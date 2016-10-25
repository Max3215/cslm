<#if cateList?? && cateList?size gt 0>
    <select name="category" datatype="*" sucmsg=" " id="twoCat" onchange="twoChange();">
        <option value="">请选择类别...</option>
            <#list cateList as c>
            	<option value="${c.id?c}" <#if parameter?? && parameter.categoryTree?contains("["+c.id+"]")>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
            </#list>
    </select>
</#if>