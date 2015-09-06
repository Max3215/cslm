<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="copyright" content="">
<!--[if IE]>
   <script src="js/html5.js"></script>
<![endif]-->
<title>首页</title>

<link href="/client/css/common.css" rel="stylesheet" type="text/css">
<link href="/client/css/main.css" rel="stylesheet" type="text/css">

<script src="/client/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="/client/js/common.js"></script>

<script type="text/javascript">
$(document).ready(function(){
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

</head>

<body>
	<!--  顶部  -->
    <#include "/client/common_header.ftl" />

	
	<!--END nav-->
	<section class="bread_nav main">
		<p>您的位置：<a href="#">首页</a>>商家入驻</p>
	</section>

	<!--右边悬浮框-->
    <#include "/client/common_float_box.ftl" />

	<!--main-->
	<div class="main">
		<section class="apply">
			<h3>商家入驻申请</h3>
			<table>
				<tr>
					<th>用户名：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>请输入用户昵称（选填）：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>密码：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>确认密码：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>超市名称：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>所属地区：</th>
					<td>
						<select>
							<option>省</option>
						</select>
						<select>
							<option>市</option>
						</select>
						<select>
							<option>区</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>详细地址：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>联系人：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>联系人电话：</th>
					<td><input class="text" type="text"></td>
				</tr>
				<tr>
					<th>超市介绍：</th>
					<td>
						<textarea></textarea>
						<p>上传营业执照等</p>
					</td>
				</tr>
				<tr>
					<th></th>
					<td><input class="sub" type="submit" value="提交申请"></td>
				</tr>
			</table>
		</section>
	</div>


	<!--  底部    -->
    <#include "/client/common_footer.ftl" />

</body>
</html>