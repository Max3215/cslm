<table>
    <#if city_list??>
        <#list city_list as city>
            <#if selcity??>
                <#if selcity==city>
                <input type="hidden" value="${selcity!''}" id="selcity">
                <tr>
                    <th width="100">${city!''}：</th>
                    <#if ("disc_"+city_index+"_list")?eval??>
                          <#list ("disc_"+city_index+"_list")?eval as disc>
                                <#if dist??>
                                    <#if dist==disc>
                                    <td>
                                        <p>${disc!''}</p>
                                        <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                                             <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                                 <a href="javascript:;" onclick="chooseDistributor(${dis.id?c})">${dis.title!''}</a>
                                             </#list>
                                         </#if>
                                    </td>
                                    </#if>
                                <#else>
                                    <td>
                                        <p>${disc!''}</p>
                                        <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                                             <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                                 <a href="javascript:;" onclick="chooseDistributor(${dis.id?c})">${dis.title!''}</a>
                                             </#list>
                                         </#if>
                                    </td>
                                </#if>
                          </#list>
                     </#if>
                </tr>
                </#if>
             <#else>
                 <tr>
                    <th width="100">${city!''}：</th>
                    <#if ("disc_"+city_index+"_list")?eval??>
                          <#list ("disc_"+city_index+"_list")?eval as disc>
                                <td>
                                    <p>${disc!''}</p>
                                    <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                                         <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                             <a href="javascript:;" onclick="chooseDistributor(${dis.id?c})">${dis.title!''}</a>
                                         </#list>
                                     </#if>
                                </td>
                          </#list>
                     </#if>
                </tr>
             </#if>
        </#list>
     </#if>
</table>