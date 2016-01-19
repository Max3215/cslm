<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />
<link href="/client/images/cslm.ico" rel="shortcut icon">
<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
<script src="/client/js/distributor_goods.js"></script>
<script type="text/javascript">
    
function deleteAd()
{
    $("#form").submit();
} 

</script>

<!--[if IE]>
   <script src="/client/js/html5.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/client/js/DD_belatedPNG_0.0.8a.js" ></script>
<script>
DD_belatedPNG.fix('.,img,background');
</script>
<![endif]-->
</head>
<body>
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
  
    <#include "/client/common_distributor_menu.ftl">
  
    <div class="mymember_mainbox">
      <div class="mymember_info mymember_info02">
        <div class="mymember_order_search"> 
          <h3>资讯管理</h3>
        </div>
        
        <div class="banner_manage">
          <div class="menu">
            <a href="/distributor/info/edit" class="a1">新增</a>
            <div class="clear h10"></div>
          </div>
          <table>
            <tr>
              <th style=" display:inline-block;  width:23%;">文章标题</th>
              <th style="display:inline-block; width:12%;">更新时间</th>
              <th style="display:inline-block; width:30%;">文章摘要</th>
              <th style="display:inline-block; width:8%;">状态</th>
              <th style="display:inline-block; width:8%;">浏览次数</th>
              <th style="display:inline-block; width:12%;">操作</th>
            </tr>
            <style type="text/css">
                .text_hide{width:23%; display:inline-block;}
                    .text_hide a{
                        float:left;
                        width:100%;
                        text-overflow: ellipsis;
                        white-space: nowrap;
                        overflow: hidden;
                    }
                </style>
            <#if news_page?? && news_page.content??>
            <#list news_page.content as info>
                <tr>
                
                  <td  class="text_hide"><a title="${info.title!''}" href="/distributor/info/edit?id=${info.id?c}">${info.title!''}</a></td>
                  <td style="display:inline-block; width:12%;"><#if info.updateTime??>${info.updateTime?string('yyyy-MM-dd')}</#if></td>
                  <td class="text_hide" style="display:inline-block; width:30%;"><#if info.brief??>${info.brief!''}</#if> &nbsp;</td>
                  <td style="display:inline-block; width:8%;">
                        <#if info.statusId??>
                        <#switch info.statusId>
                            <#case 0>正常<#break>
                            <#case 1>待审核<#break>
                            <#case 2>不显示<#break>
                        </#switch>
                        </#if>
                  </td>
                  <td style="display:inline-block; width:8%;">${info.viewCount!'0'}</td>
                  <td style="display:inline-block; width:12%;">
                        <a href="/distributor/info/edit?id=${info.id?c}">修改</a>&nbsp;/&nbsp;
                        <a href ="/distributor/info/delete?id=${info.id?c}">删除</a>
                  </td>
                </tr>
            </#list>
            </#if>
          </table>
          <div class="mymember_page">
                <#if news_page??>
                <#assign continueEnter=false>
                <#if news_page.totalPages gt 0>
                    <#list 1..news_page.totalPages as page>
                        <#if page <= 3 || (news_page.totalPages-page) < 3 || (news_page.number+1-page)?abs<3 >
                            <#if page == news_page.number+1>
                                <a class="mysel" href="javascript:;">${page}</a>
                            <#else>
                                <a href="/distributor/info/list?page=${page-1}">${page}</a>
                            </#if>
                            <#assign continueEnter=false>
                        <#else>
                            <#if !continueEnter>
                                <b class="pn-break">&hellip;</b>
                                <#assign continueEnter=true>
                            </#if>
                        </#if>
                    </#list>
                </#if>
                </#if>
            </div>
        </div>
        
      </div>
      <!--mymember_info END-->

    </div>
    <!--mymember_center END-->
 
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">


</body>
</html>