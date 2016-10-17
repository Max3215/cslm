<#if point_page?? && point_page.content??>
    <#list point_page.content as re>
        <tr>
          <td>${re.pointTime?string('yyyy-MM-dd')}</td>
          <td><span>${re.point!'0'}</span></td>
          <td>${re.detail}</td>
        </tr>
    </#list>
</#if>