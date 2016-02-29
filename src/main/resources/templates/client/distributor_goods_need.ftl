
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
<script type="text/javascript" src="/client/js/Validform_v5.3.2_min.js"></script>
<!--
<script type="text/javascript" src="/mag/js/WdatePicker.js"></script>
-->
<script type="text/javascript">
$(document).ready(function(){
     //初始化表单验证
    $("#form1").Validform({
        tiptype: 3
    })

  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

  $("#nav_down li").hover(function(){
    $(this).find(".nav_show").fadeIn(10);
  },function(){
    $(this).find(".nav_show").stop(true,true).fadeOut(10);
  })  

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})
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
      <div class="mymember_info">
        <h3 style="font-weight:400;">商品供求</h3>		
		      <form action="" method="post" id="form1">
		      <div class="haoh pt15 geren_rig">
              <div class="h20"></div>
              <div style="padding-left:40px;">
                <table class="s_d_table">
                  <tr>
                    <th>供需类型：</th>
                    <td>
                      <span><input type="radio" value="1" name="type" checked="checked"/>&nbsp;需求</span>
                      <span><input type="radio" value="2" name="type"/>&nbsp;供应</span>
                    </td>
                  </tr>
                  <tr>
                    <th>商品名称：</th>
                    <td><input type="text" class="text big" datatype="*" name="content"/></td>
                  </tr>
                  <tr>
                    <th>商品类别：</th>
                    <td>
                      <select name="categoryId" >
                        <option value="">请选择类别...</option>
                        <#if category_list??>
                            <#list category_list as c>
                                <option value="${c.id?c}" <#if categoryId?? && categoryId==c.id>selected="selected"</#if>><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                            </#list>
                        </#if>
                    </select>
                    </td>
                  </tr>
                  <tr>
                    <th>商品价格：￥</th>
                    <td><input type="text" class="text lit" datatype="n"/>&emsp;</td>
                  </tr>
                  <tr>
                    <th>商品数量：</th>
                    <td><input type="text" class="text lit" datatype="n" name="number"/></td>
                  </tr>
                   <tr>
                        <th>需/供时间：</th>
                        <td>
                           <input name="needTime" type="text" value="" class="text big" >
                        </td>
                   </tr>
                  <tr>
                    <th></th>
                    <td><input type="submit" class="sub" value="提交" /></td>
                  </tr>
                </table>
              </div>     
          </div>
          </form>
	     </div>
      <!--mymember_info END-->
    
    </div>
    <!--mymember_center END-->   
    <div class="myclear"></div>
  </div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl">
</body>
</html>