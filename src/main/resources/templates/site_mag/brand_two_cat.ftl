<select  datatype="*" sucmsg=" " id="twoCat" onchange="twoChange();">
    <#if !goods??>
    <option value="">请选择类别...</option>
    </#if>
    <#if cateList??>
        <#list cateList as c>
            <option value="${c.id?c}" <#if goods?? && goods.categoryId==c.id>selected="selected"</#if>>${c.title!""}</option>
        </#list>
    </#if>
</select>