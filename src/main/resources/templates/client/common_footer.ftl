<section class="main">
        <div class="slogen">
            <#if service_item_list??>
                <#list service_item_list as item>
                    <#if item_index lt 4 >
                        <span><i><img src="${item.logo!''}"></i>${item.title!''}</span>
                    </#if>
                </#list>
            </#if>
        </div>
    </section>

    <footer class="foot">
        <div class="main">
           <#if td_art_list??>
               <#list td_art_list as item>
                    <dl>
                        <dt>${item.title!''}</dt>
                        <#if ("second_level_"+item_index+"_category_list")?eval??>
                            <#list ("second_level_"+item_index+"_category_list")?eval as second_item>
                                <dd><a href="/info/content/${second_item.id?c}?mid=12" target="_blank">${second_item.title!''}</a></dd>
                            </#list>
                         </#if>
                    </dl>
                </#list>
             </#if>
            <div class="b_contact">
                <a  class="qq" href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>"><img src="/client/images/qq01.png"></a>
                <span><i><img src="/client/images/phone01.png"></i><#if site.telephone??>${site.telephone}</#if></span>
            </div>
            <div class="clear"></div>

            <menu class="bottom_nav">
                友情链接：
                <#if site_link_list??>
                <#list site_link_list as item>
                    <a href="${item.linkUri!''}" target="_blank">${item.title!''}<span>丨</span></a>
                </#list>
                </#if>
            </menu>
            <p class="copyright"> ${site.copyright!''}<br>
            技术支持：<a href="http://www.ynyes.com" target="_blank">昆明天度网络信息技术有限公司</a></p>
        </div>
    </footer>