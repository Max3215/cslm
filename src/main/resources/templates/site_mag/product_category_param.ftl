<#if paramLlist??>
<select  name="paramCategory" datatype="n0-100">
    <option value="0">无关联参数</option>
        <#list paramLlist as c>
            <option value="${c.id?c!""}" >${c.title!""}</option>
        </#list>
</select>
</#if>