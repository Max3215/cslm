<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title><#if site??>${site.seoTitle!''}-</#if>会员中心</title>
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
<script src="/client/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>
<script src="/client/js/jquery.diysiteselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    evaluateShow('mymember_eva01','mymember_evabox')
    
  $(".click_a").click(function(){
    if($(this).next().is(":visible")==false){
      $(this).next().slideDown(300);
    }else{
      $(this).next().slideUp(300);
    }
  });//选择超市下拉效果

 navDownList("nav_down","li",".nav_show");
  menuDownList("mainnavdown","#nav_down",".a2","sel");

  $(".float_box .ewm").hover(function(){
    $(this).next().show();
  },function(){
    $(this).next().hide();
  })
})

</script>

<script>
$(document).ready(function(){
    $("#form1").Validform({
        tiptype: 3,
        ajaxPost:true,
        callback: function(data) {
            if (data.code==0)
            {
                alert("提交成功，我们会尽快处理，请耐心等待");
                window.location.reload();
            }
            else
            {
                alert(data.message);
            }
        }
    });
});
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
    <#include "/client/common_header.ftl" />
<!--mymember-->
<div class="myclear"></div>
<div class="mymember_out">
  <div class="mymember_main">
    
    <#include "/client/common_user_menu.ftl"/>
    <div class="mymember_mainbox">    
    <div class="mymember_info mymember_info02">
      <div class="mymember_order_search">留言
          <div class="clear"></div>
        </div>

        <form id="form1" action="/suggestion/add" method="post">
        <table align="left">
         <tr id="mymember_eva01" class="mymember_evabox">
          <td class="td004" colspan="4">
            <span style="position:absolute;right:88px;top:-13px;"></span>
            <div class="mymember_eva_div">
                <b>我要留言：</b>
                在您填写下列投诉内容之前，我们首先代表超市为导致您进行留言的原因（行为）表示歉意，请详细描述事件经过，
                以便我们尽快为您解决问题，我们一定会及时处理，给您一个满意的解决方案，您的满意是我们最大的动力，谢谢！
            </div>
            <div class="mymember_eva_div">
              <b><font>* </font>内容：</b>
              <textarea id="content" name="content" datatype="*" errormsg=""></textarea>
            </div>
            <div class="mymember_eva_div">
              <b><font>* </font>称呼：</b>
              <input type="text" id="name" name="name" datatype="*2-6" errormsg="" />
            </div>
            <div class="mymember_eva_div">
              <b><font>* </font>邮箱：</b>
              <input type="text" id="email" name="mail" datatype="e" errormsg=""/>
            </div>
            <div class="mymember_eva_div">
              <b><font>* </font>联系方式：</b>
              <input type="text" id="mobile" name="mobile" datatype="m" errormsg="请输入联系方式，以便进行联系"/>
            </div>         
            <div class="mymember_eva_div">
              <input class="mysub" type="submit" value="提交我的投诉" />
            </div>
          </td>
        </tr>
        <#--
       <tr >
            <td ><a href="#">25145555555455845</a></td>
            <td align="left"><table width="100" border="0"  align=left>
                <tr>
                  <td><img width="50" height="50" title="海飞丝去屑洗发露丝质柔滑型750ML（新老包装随机发放）" src="http://img10.360buyimg.com/N5/jfs/t700/1/460177142/106757/518f3708/54b33408N943aca15.jpg" /> <br>
                  <a href="会员-退换货详细.html?wareId=206735&orderId=8641147682" ></a></td>
                  
                </tr>
              </table></td>
            <td ><p>2015-02-23</p>
              <p>15:35:11</p></td>
          <td><a href="javascript:evaluateShow('mymember_eva02','mymember_evabox');">我要投诉</a></td>
          </tr>
      
       <tr id="mymember_eva02" class="mymember_evabox">
          <td class="td004" colspan="4">
            <span style="position:absolute;right:88px;top:-13px;"><img src="/client/images/mymember/arrow06.gif" /></span>
            <div class="mymember_eva_div">
      涉及订单：8654519396投诉类型： <select>
              <option>产品相关</option>
              <option>价格相关</option>
              <option>服务相关</option>
              <option>物流相关</option>
              <option>其它相关</option>
            </select>
             
            </div>
            <div class="mymember_eva_div">
              <b><font>* </font>投诉内容：</b>
              <textarea></textarea>
            </div>         
            <div class="mymember_eva_div">
              <input class="mysub" type="submit" value="提交我的投诉" />
            </div>
          </td>
        </tr> -->
        </table>
        </form>
        <div class="myclear" style="height:10px;"></div>
      <#--  <div class="mymember_page"> <a class="mysel" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">5</a> <a href="#">6</a> </div>  -->
    </div><!--mymember_info END-->
    
  </div>
    <!--mymember_center END-->
 
    <div class="myclear"></div>
  </div>
  <!--mymember_main END-->
  <div class="myclear"></div>
</div>
<!--mymember END-->
<div class="clear"></div>
    <#include "/client/common_footer.ftl"/>
</body>
</html>