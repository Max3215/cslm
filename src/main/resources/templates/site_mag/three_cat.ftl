<select  datatype="*" sucmsg=" " id="categoryId" onchange="findParam();"  name="categoryId">
    <#if !goods??>
    <option value="">请选择类别...</option>
    </#if>
    <#if categoeyList??>
        <#list categoeyList as c>
            <option value="${c.id?c}" <#if goods?? && goods.categoryId==c.id>selected="selected"</#if>>${c.title!""}</option>
        </#list>
    </#if>
</select>
<#--  onchange="parameter(this.value);"    -->