<#if virtualPage?? && virtualPage.content??>
    <#list virtualPage.content as re>
        <tr>
          <td>${re.createTime?string('yyyy-MM-dd')}</td>
          <td><span><#if re.realPrice??>${re.realPrice?string('0.00')}<#else>0.00</#if></span></td>
          <td>${re.cont!''}</td>
        </tr>
    </#list>
</#if>