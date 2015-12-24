<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>超市中心</title>
<meta name="keywords" content="${site.seoKeywords!''}">
<meta name="description" content="${site.seoDescription!''}">
<meta name="copyright" content="${site.copyright!''}" />

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">
<link href="/client/css/mymember.css" rel="stylesheet" type="text/css" />
<!--<link href="/client/css/member.css" rel="stylesheet" type="text/css" />-->

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script src="/client/js/mymember.js"></script>
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
            <a href="#" class="a1">新增</a>
            <a href="#" class="a3">全选</a>
            <a href="#" class="a4">删除</a>
            <div class="clear h10"></div>
          </div>
          <table>
            <tr>
              <th>选择</th>
              <th>广告名称</th>
              <th>开始时间</th>
              <th>到期时间</th>
              <th>链接</th>
              <th>状态</th>
              <th>发布时间</th>
              <th>操作</th>
            </tr>
            <tr>
              <td><input type="checkbox" /></td>
              <td>列表页广告</td>
              <td>2015-01-05  17:09:15</td>
              <td>2015-01-05  17:09:15</td>
              <td>广告链接</td>
              <td>正常</td>
              <td>2015-01-05  17:09:15</td>
              <td><a href="#">修改</a></td>
            </tr>
            <tr>
              <td><input type="checkbox" /></td>
              <td>列表页广告</td>
              <td>2015-01-05  17:09:15</td>
              <td>2015-01-05  17:09:15</td>
              <td>广告链接</td>
              <td>正常</td>
              <td>2015-01-05  17:09:15</td>
              <td><a href="#">修改</a></td>
            </tr>
            <tr>
              <td><input type="checkbox" /></td>
              <td>列表页广告</td>
              <td>2015-01-05  17:09:15</td>
              <td>2015-01-05  17:09:15</td>
              <td>广告链接</td>
              <td>正常</td>
              <td>2015-01-05  17:09:15</td>
              <td><a href="#">修改</a></td>
            </tr>
          </table>
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