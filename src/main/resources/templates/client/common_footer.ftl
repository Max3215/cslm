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
                                <dd><a href="/info/content/${second_item.id?c}?mid=12">${second_item.title!''}</a></dd>
                            </#list>
                         </#if>
                    </dl>
                </#list>
             </#if>
            <div class="b_contact">
                <a href="#" class="qq"><img src="/client/images/qq01.png"></a>
                <span><i><img src="/client/images/phone01.png"></i><#if site.telephone??>${site.telephone}</#if></span>
            </div>
            <div class="clear"></div>

            <menu class="bottom_nav">
                <a href="#">首页<span>丨</span></a>
                <a href="#">本地特产<span>丨</span></a>
                <a href="#">政企采购<span>丨</span></a>
                <a href="#">生活服务<span>丨</span></a>
                <a href="#">商家入驻</a>
            </menu>
            <p class="copyright">copyright©2015&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公司版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术支持：<a href="http://www.ynyes.com">昆明天度网络信息技术有限公司</a></p>
        </div>
    </footer>