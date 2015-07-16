<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>编辑物流配送</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
    $(function () {
        //初始化表单验证
        $("#form1").initValidform();
        //物流编码赋值
        $("#ddlExpressCode").change(function () {
            if ($(this).find("option:selected").attr("value") != "") {
                $("#code").val($(this).find("option:selected").attr("value"));
                $("#code").focus();
            }
        });
    });
</script>
</head>

<body class="mainbody"><div style="position: absolute; left: -9999em; top: 236px; visibility: visible; width: auto; z-index: 1976;"><table class="ui_border ui_state_visible ui_state_focus"><tbody><tr><td class="ui_lt"></td><td class="ui_t"></td><td class="ui_rt"></td></tr><tr><td class="ui_l"></td><td class="ui_c"><div class="ui_inner"><table class="ui_dialog"><tbody><tr><td colspan="2"><div class="ui_title_bar"><div class="ui_title" unselectable="on" style="cursor: move;">视窗 </div><div class="ui_title_buttons"><a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a><a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a><a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a><a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">×</a></div></div></td></tr><tr><td class="ui_icon" style="display: none;"></td><td class="ui_main" style="width: auto; height: auto;"><div class="ui_content" style="padding: 10px;"><div class="ui_loading"><span>loading...</span></div></div></td></tr><tr><td colspan="2"><div class="ui_buttons" style="display: none;"></div></td></tr></tbody></table></div></td><td class="ui_r"></td></tr><tr><td class="ui_lb"></td><td class="ui_b"></td><td class="ui_rb" style="cursor: se-resize;"></td></tr></tbody></table></div>
<form name="form1" method="post" action="/Verwalter/order/setting/delivery/save" id="form1">
<div>
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}">
<input name="deliveryTypeId" type="text" value='<#if delivery_type??>${delivery_type.id}</#if>' style="display:none">
</div>

<!--导航栏-->
<div class="location">
  <a href="/Verwalter/order/setting/delivery/list" class="back"><i></i><span>返回列表页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i>
  <a href="/Verwalter/order/setting/delivery/list"><span>配送方式</span></a>
  <i class="arrow"></i>
  <span>编辑配送方式</span>
</div>
<div class="line10"></div>
<!--/导航栏-->

<!--内容-->
<div class="content-tab-wrap">
  <div id="floatHead" class="content-tab">
    <div class="content-tab-ul-wrap">
      <ul>
        <li><a href="javascript:;" onclick="tabs(this);" class="selected">编辑信息</a></li>
      </ul>
    </div>
  </div>
</div>

<div class="tab-content">
  <dl>
    <dt>标题名称</dt>
    <dd><input name="title" type="text" value="<#if delivery_type??>${delivery_type.title!""}</#if>" class="input normal" datatype="*2-100" sucmsg=" "> <span class="Validform_checktip">*物流公司的中文名称</span></dd>
  </dl>
  <dl>
    <dt>物流编码</dt>
    <dd>
      <input name="code" type="text" id="code" value="<#if delivery_type??>${delivery_type.code!""}</#if>" class="input normal" datatype="s0-100" sucmsg=" ">
      <div class="rule-single-select single-select">
        <select id="ddlExpressCode" name="ddlExpressCode" style="display: none;">
            <option value="">@参考编码</option>
            <option value="aae">A-aae全球专递</option>
            <option value="anjie">A-安捷快递</option>
            <option value="anxindakuaixi">A-安信达快递</option>
            <option value="biaojikuaidi">B-彪记快递</option>
            <option value="bht">B-bht</option>
            <option value="baifudongfang">B-百福东方国际物流</option>
            <option value="coe">C-中国东方（COE）</option>
            <option value="changyuwuliu">C-长宇物流</option>
            <option value="datianwuliu">D-大田物流</option>
            <option value="debangwuliu">D-德邦物流</option>
            <option value="dhl">D-dhl</option>
            <option value="dpex">D-dpex</option>
            <option value="dsukuaidi">D-d速快递</option>
            <option value="disifang">D-递四方</option>
            <option value="ems">E-ems快递</option>
            <option value="fedex">F-fedex（国外）</option>
            <option value="feikangda">F-飞康达物流</option>
            <option value="fenghuangkuaidi">F-凤凰快递</option>
            <option value="feikuaida">F-飞快达</option>
            <option value="guotongkuaidi">G-国通快递</option>
            <option value="ganzhongnengda">G-港中能达物流</option>
            <option value="guangdongyouzhengwuliu">G-广东邮政物流</option>
            <option value="gongsuda">G-共速达</option>
            <option value="huitongkuaidi">H-汇通快运</option>
            <option value="hengluwuliu">H-恒路物流</option>
            <option value="huaxialongwuliu">H-华夏龙物流</option>
            <option value="haihongwangsong">H-海红</option>
            <option value="haiwaihuanqiu">H-海外环球</option>
            <option value="jiayiwuliu">J-佳怡物流</option>
            <option value="jinguangsudikuaijian">J-京广速递</option>
            <option value="jixianda">J-急先达</option>
            <option value="jjwl">J-佳吉物流</option>
            <option value="jymwl">J-加运美物流</option>
            <option value="jindawuliu">J-金大物流</option>
            <option value="jialidatong">J-嘉里大通</option>
            <option value="jykd">J-晋越快递</option>
            <option value="kuaijiesudi">K-快捷速递</option>
            <option value="lianb">L-联邦快递（国内）</option>
            <option value="lianhaowuliu">L-联昊通物流</option>
            <option value="longbanwuliu">L-龙邦物流</option>
            <option value="lijisong">L-立即送</option>
            <option value="lejiedi">L-乐捷递</option>
            <option value="minghangkuaidi">M-民航快递</option>
            <option value="meiguokuaidi">M-美国快递</option>
            <option value="menduimen">M-门对门</option>
            <option value="ocs">O-OCS</option>
            <option value="peisihuoyunkuaidi">P-配思货运</option>
            <option value="quanchenkuaidi">Q-全晨快递</option>
            <option value="quanfengkuaidi">Q-全峰快递</option>
            <option value="quanjitong">Q-全际通物流</option>
            <option value="quanritongkuaidi">Q-全日通快递</option>
            <option value="quanyikuaidi">Q-全一快递</option>
            <option value="rufengda">R-如风达</option>
            <option value="santaisudi">S-三态速递</option>
            <option value="shenghuiwuliu">S-盛辉物流</option>
            <option value="shentong">S-申通</option>
            <option value="shunfeng">S-顺丰</option>
            <option value="sue">S-速尔物流</option>
            <option value="shengfeng">S-盛丰物流</option>
            <option value="saiaodi">S-赛澳递</option>
            <option value="tiandihuayu">T-天地华宇</option>
            <option value="tiantian">T-天天快递</option>
            <option value="tnt">T-tnt</option>
            <option value="ups">U-ups</option>
            <option value="wanjiawuliu">W-万家物流</option>
            <option value="wenjiesudi">W-文捷航空速递</option>
            <option value="wuyuan">W-伍圆</option>
            <option value="wxwl">W-万象物流</option>
            <option value="xinbangwuliu">X-新邦物流</option>
            <option value="xinfengwuliu">X-信丰物流</option>
            <option value="yafengsudi">Y-亚风速递</option>
            <option value="yibangwuliu">Y-一邦速递</option>
            <option value="youshuwuliu">Y-优速物流</option>
            <option value="youzhengguonei">Y-邮政包裹挂号信</option>
            <option value="youzhengguoji">Y-邮政国际包裹挂号信</option>
            <option value="yuanchengwuliu">Y-远成物流</option>
            <option value="yuantong">Y-圆通速递</option>
            <option value="yuanweifeng">Y-源伟丰快递</option>
            <option value="yuanzhijiecheng">Y-元智捷诚快递</option>
            <option value="yunda">Y-韵达快运</option>
            <option value="yuntongkuaidi">Y-运通快递</option>
            <option value="yuefengwuliu">Y-越丰物流</option>
            <option value="yad">Y-源安达</option>
            <option value="yinjiesudi">Y-银捷速递</option>
            <option value="zhaijisong">Z-宅急送</option>
            <option value="zhongtiekuaiyun">Z-中铁快运</option>
            <option value="zhongtong">Z-中通速递</option>
            <option value="zhongyouwuliu">Z-中邮物流</option>
            <option value="zhongxinda">Z-忠信达</option>
            <option value="zhimakaimen">Z-芝麻开门</option>
        </select>
      </div>
      <span class="Validform_checktip">快递100物流编码</span>
    </dd>
  </dl>
  
  <dl>
    <dt>物流网址</dt>
    <dd>
      <input name="uri" type="text" value="<#if delivery_type??>${delivery_type.uri!""}</#if>" class="input normal" datatype="url" errormsg="请输入正确的网址" sucmsg=" ">
      <span class="Validform_checktip">物流快递的官网，以“http://”开头</span>
    </dd>
  </dl>
  <dl>
    <dt>配送费用</dt>
    <dd>
      <input name="fee" type="text" value="<#if delivery_type??>${delivery_type.fee?string("0.00")}<#else>0</#if>" class="input small" datatype="/^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/" sucmsg=" ">
      <span class="Validform_checktip">*货币格式，单位为元</span>
    </dd>
  </dl>
  <dl>
    <dt>是否启用</dt>
    <dd>
      <div class="rule-multi-radio multi-radio">
        <span id="rblStatus" style="display: none;">
            <input type="radio" name="isEnable" value="1" <#if delivery_type?? && delivery_type.isEnable?? && !delivery_type.isEnable><#else>checked="checked"</#if>>
            <label>是</label>
            <input type="radio" name="isEnable" value="0" <#if delivery_type?? && delivery_type.isEnable?? && !delivery_type.isEnable>checked="checked"</#if>>
            <label>否</label>
      </div>
      <span class="Validform_checktip">*不启用则不显示该支付方式</span>
    </dd>
  </dl>
  <dl>
    <dt>排序数字</dt>
    <dd>
      <input name="sortId" type="text" value="<#if delivery_type??>${delivery_type.sortId!""}</#if>" class="input small" datatype="n" sucmsg=" ">
      <span class="Validform_checktip">*数字，越小越向前</span>
    </dd>
  </dl>
  <dl>
    <dt>描述说明</dt>
    <dd>
      <textarea name="info" rows="2" cols="20" class="input normal"><#if delivery_type??>${delivery_type.info!""}</#if></textarea>
      <span class="Validform_checktip">物流配送的描述说明，支持HTML代码</span>
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