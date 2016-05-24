<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>回复评论</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
    });
</script>
</head>

<body class="mainbody"><div class="" style="left: 0px; top: 0px; visibility: hidden; position: absolute;"><table class="ui_border"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;"></div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/user/comment/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" value="${statusId!""}">
<input type="hidden" name="userCommentId" value="${userCommentId!""}">
</div>
<!--导航栏-->
<div class="location">
  <a href="/Verwalter/user/comment/list?statusId=${statusId!""}" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <span>评论与退换货</span>
  <i class="arrow"></i>
  <span>商品评论</span>
  <i class="arrow"></i>
  <span>回复评论</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">回复评论</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>商品名称</dt>
    <dd><#if user_comment??>${user_comment.goodsTitle!""}</#if></dd>
  </dl>
  <dl>
    <dt>评论用户</dt>
    <dd><#if user_comment??>${user_comment.username!""}</#if></dd>
  </dl>
  <dl>
    <dt>相关订单</dt>
    <dd><#if user_comment??>${user_comment.orderNumber!""}</#if></dd>
  </dl>
  <dl>
    <dt>评论得分</dt>
    <dd>
        ${user_comment.goodsStar!''}星
        <#if user_comment?? && user_comment.stars??>
            <#if user_comment.stars==1>
                差评
            <#elseif user_comment.stars==2>
                中评
            <#else>
                好评
            </#if>
        </#if>
    </dd>
  </dl>
  <dl>
    <dt>评论内容</dt>
    <dd><#if user_comment??>${user_comment.content!""}</#if></dd>
  </dl>
  <dl>
    <dt>评论时间</dt>
    <dd><#if user_comment??>${user_comment.commentTime!""}</#if></dd>
  </dl>
  <dl>
    <dt>审核状态</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span style="display: none;">
            <input type="radio" name="statusId" value="1" <#if user_comment?? && user_comment.statusId?? && user_comment.statusId==1>checked="checked"</#if>>
            <label>已审核</label>
            <input type="radio" name="statusId" value="0" <#if !user_comment?? || !user_comment.statusId?? || user_comment.statusId==0>checked="checked"</#if>>
            <label>待审核</label>
        </span>
      </div>
    </dd>
  </dl>
  <dl>
    <dt>回复内容</dt>
    <dd>
      <textarea name="reply" rows="2" cols="20" class="input normal"><#if user_comment??>${user_comment.reply!""}</#if></textarea>
    </dd>
  </dl>
</div>
<!--/内容-->

<!--工具栏-->
<div class="page-footer">
  <div class="btn-list">
    <input type="submit" name="btnSubmit" value="提交保存" id="btnSubmit" class="btn">
    <input name="btnReturn" type="button" value="返回上一页" class="btn yellow" onclick="javascript:history.back(-1);">
  </div>
  <div class="clear"></div>
</div>
<!--/工具栏-->
</form>


</body></html>