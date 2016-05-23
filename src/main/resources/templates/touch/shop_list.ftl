<div class="show_list">
    <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
    <div style="height:100%;overflow:auto;">
        <dl>
            <dt>附近商家:</dt>
        </dl>
      <#if shop_list?? && shop_list?size gt 0>
          <#list shop_list as shop>
                <dl>
                    <dd><a href="javascript:;" onclick="chooseDistributor(${shop.id?c});">${shop.title!''}</a></dd>
                </dl>
           </#list>
       <#elseif more_list??>
            <#list more_list as shop>
                <dl>
                    <dd><a href="javascript:;" onclick="chooseDistributor(${shop.id?c});">${shop.title!''}</a></dd>
                </dl>
           </#list>
       </#if>
    </div>
</div>

<!--
<div class="show_list">
    <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
    <div style="height:100%;overflow:auto;">
      <#if city_list??>
      <#list city_list as city>
                <#if ("disc_"+city_index+"_list")?eval??>
                    <#list ("disc_"+city_index+"_list")?eval as disc>
                    <dl>
                    <dt>${city!''}-${disc!''}</dt>
                        <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                             <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                <dd><a href="javascript:;" onclick="chooseDistributor(${dis.id?c});">${dis.title!''}</a></dd>
                            </#list>
                        </#if>
                    </dl>
                </#list>
             </#if>
       </#list>
       </#if>
    </div>
</div>
-->