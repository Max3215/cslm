<div id="comment-list">
    <div class="evaluate">
        <ul>
            <#if comment_page??>
                <#list comment_page.content as item>
                    <li>
                        <p class="p1">${item.content!''}<span></span>用户：${item.username!''}</p>
                        <p class="p2">${item.commentTime!''}</p>
                         <#if item.isReplied?? && item.isReplied>
                            <p class="p3">商家回复：${item.reply!''}</p>
                         </#if>
                    </li>
                </#list>
            </#if> 
        </ul>
    </div>
    <#if comment_page??>
    <div class="pages">
        <span>共${comment_page.content?size!''}条记录&nbsp;&nbsp;&nbsp;&nbsp;<#if comment_page.totalPages==0>0<#else>${comment_page.number+1}</#if>/${comment_page.totalPages!'0'}页</span>
        <#assign continueEnter=false>
        <#if comment_page?? && comment_page.number+1 == 1>
            <a  href="javascript:;">上一页</a>
        <#else>
            <a href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${comment_page.number-1});">上一页</a>
        </#if>
        
        <#if comment_page.totalPages gt 0>
            <#list 1..comment_page.totalPages as page> 
                <#if page <= 3 || (comment_page.totalPages-page) < 3 || (comment_page.number+1-page)?abs<3 >
                    <#if page == comment_page.number+1>
                        <a class="sel" href="javascript:;"">${page}</a>
                    <#else>
                        <a href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${page-1});">${page}</a>
                    </#if>
                    <#assign continueEnter=false>
                <#else>
                    <#if !continueEnter>
                        <span> ... </span>
                        <#assign continueEnter=true>
                    </#if>
                </#if>
            </#list>
        </#if>
        
        <#if comment_page.number+1 == comment_page.totalPages || comment_page.totalPages==0>
            <a href="javascript:;">下一页</a>
        <#else>
            <a  href="javascript:getCommentByStars(${goodsId}, ${stars!'0'}, ${comment_page.number+1});">下一页</a>
        </#if>
    </div>
    </#if>
    </div>
