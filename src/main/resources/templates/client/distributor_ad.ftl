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
          <h3>广告位管理</h3>
        </div>
        
        <div class="banner_manage">
          <div class="menu">
            <a href="/distributor/ad/edit" class="a1">新增</a>
            <a href="javascript:deleteAd();"  class="a4" style="width: 60px;">批量删除</a>
            <div class="clear h10"></div>
          </div>
          <form  id="form" action = "/distributor/ad/deleteAll" method="post">
          <table>
            <tr>
              <th ><input id="checkAll" name="r" type="checkbox"  class="check" onclick="selectAll();"/>全选</th>
              <th>广告名称</th>
              <th>开始时间</th>
              <th>到期时间</th>
              <th>链接</th>
              <th>状态</th>
              <th>发布时间</th>
              <th>操作</th>
            </tr>
            <#if ad_list??>
            <#list ad_list as ad>
                <tr>
                  <td>
                        <input id="yu_1424195166" name="listChkId" type="checkbox" value="${ad_index}" class="check""/>
                        <input type="hidden" name="listId" id="listId" value="${ad.id?c}">
                    </td>
                  <td><a href="/distributor/ad/edit?id=${ad.id?c}">${ad.title!''}</a></td>
                  <td>${ad.startTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                  <td><#if ad.endTime??>${ad.endTime?string('yyyy-MM-dd HH:mm:ss')}</#if></td>
                  <td><a target="_blank" href="${ad.linkUri!""}">广告链接</a></td>
                  <td><#if ad.isEnable?? && ad.isEnable><#if ad.endTime?? && ad.endTime gt .now><font color="#009900">正常</font><#else><font color="#990000">过期</font></#if><#else>待审核</#if></td>
                  <td>${ad.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
                  <td>
                        <a href="/distributor/ad/edit?id=${ad.id?c}">修改</a>&nbsp;/&nbsp;
                        <a href ="/distributor/ad/delete?id=${ad.id?c}">删除</a>
                  </td>
                </tr>
            </#list>
            </#if>
          </table>
          </form>
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