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


	navDownList("nav_down","li",".nav_show");
	//menuDownList("mainnavdown","#nav_down",".a2","sel");

	adChange("banner_box","banner_sum","banner_num",3000,1000);
	//楼层计算
	indexFloor("indexfloor","a");

	$(".float_box .ewm").hover(function(){
		$(this).next().show();
	},function(){
		$(this).next().hide();
	})
})
</script>

</head>

<body>
	<header class="main_top">
		<div class="main">
			<h1>您好！欢迎光临王明辉超市！</h1>
			<a href="#">请登陆</a>
			<a href="#">注册</a>
			<menu class="top_menu">
				<a href="#">我的订单<span>丨</span></a>
				<a href="#">我的购物车<span>丨</span></a>
				<a href="#">超市会员<span>丨</span></a>
				<a href="#">客户服务<span>丨</span></a>
				<a href="#">我的收藏</a>
			</menu>
			<div class="clear"></div>
		</div>
	</header>
	<!--logo 搜索框部分-->
	<section class="main">
		<a href="#" class="logo"><img src="images/logo.png"></a>
		<div class="choose_mar">
			<a href="javascript:void(0);" class="click_a" onclick="$('#mar_box').fadeIn(300);">选择地区超市</a>
		</div>
		<div class="m_box">
			<div class="search_box">
				<input class="text" type="text">
				<a href="#">搜索</a>
			</div>
			<menu class="hot_search">
				<a href="#">洗发水<span>丨</span></a>
				<a href="#">牙膏<span>丨</span></a>
				<a href="#">纸巾<span>丨</span></a>
				<a href="#">垃圾袋<span>丨</span></a>
				<a href="#">桶<span>丨</span></a>
				<a href="#">零食<span>丨</span></a>
			</menu>
		</div>
		<div class="gu_car">
			<a href="#">去购物车结算<span>5</span></a>
		</div>
		<div class="clear"></div>
	</section>

	<!--选择超市弹出框-->
	<aside class="winbox" id="mar_box">
		<div class="mar_box">
			<p class="tit">请选择超市<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().fadeOut(300);"></a></p>
			<div class="select">
				<span>云南省</span>
				<select>
					<option>请选择&nbsp;&nbsp;市</option>
					<option>昆明市</option>
				</select>
				<select>
					<option>请选择&nbsp;&nbsp;区</option>
					<option>五华区</option>
				</select>
				<select>
					<option>请选择&nbsp;&nbsp;超市</option>
					<option>超市名字</option>
				</select>
				<input class="sub" type="submit" value="确定">
			</div>
			<div class="mar_list">
				<table>
					<tr>
						<th width="100">昆明市：</th>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
					</tr>
					<tr>
						<th width="100">昆明市：</th>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
						<td>
							<p>五华区</p>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>
							<a href="#">超市名字</a>	
						</td>
					</tr>
				</table>
			</div>

		</div>
	</aside>

	<!--导航部分-->
	<nav class="nav_box">
		<div class="main">
			<section class="nav_list" id="mainnavdown">
				<a href="#" class="a2">全部商品分类</a>
				<ul id="nav_down" class="nav_down">
					<li>
						<a href="#" class="list">食品、酒水、茗茶 </a>
						<span>></span>
						<div class="nav_show">
							<table>
								<tr>
									<th width="60">进口牛奶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">酒水</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">茗茶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
							</table>
						</div>
					</li>
					<li>
						<a href="#" class="list">粮油副食 </a>
						<span>></span>
						<div class="nav_show">
							<table>
								<tr>
									<th width="60">进口牛奶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">酒水</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">茗茶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
							</table>
						</div>
					</li>
					<li>
						<a href="#" class="list">美容洗护 </a>
						<span>></span>
						<div class="nav_show">
							<table>
								<tr>
									<th width="60">进口牛奶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">酒水</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
								<tr>
									<th width="60">茗茶</th>
									<td>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
										<a href="#">牛奶</a>
										<a href="#">牛奶奶</a>
									</td>
								</tr>
							</table>
						</div>
					</li>
				</ul>
			</section>
			<a href="#" class="a1 sel">首页</a>
			<a href="#" class="a1">商品推荐</a>
			<a href="#" class="a1">本地特产</a>
			<a href="#" class="a1">商品预定</a>
			<a href="#" class="a1">进口商品</a>
			<a href="#" class="a1">政企采购</a>
			<a href="#" class="a1">生活服务</a>
			<a href="#" class="a1">商家入驻</a>

			<div class="right_kx">
				<h3>超市快讯<a href="#">更多></a></h3>
				<ul>
					<li><a href="#">恒生系统近2周强平150亿</a></li>
					<li><a href="#">恒生系统近2周强平150亿</a></li>
					<li><a href="#">恒生系统近2周强平150亿</a></li>
					<li><a href="#">恒生系统近2周强平150亿</a></li>
					<li><a href="#">恒生系统近2周强平150亿</a></li>
				</ul>
				<div class="kx_banner"><a href="#"><img src="images/pic01.jpg"></a></div>
				<div class="ewm">
					<div class="clear"></div>
					<img src="images/ewm.jpg" width="93" height="93">
					<p class="pt20">微信扫描</p>
					<p>二维码</p>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</nav>
	<!--END nav-->
	<!--banner-->
	<section id="banner_box">
		<ul id="banner_sum">
			<li style="display:block;">
				<a href="#">
					<img src="images/banner01.jpg" alt="">
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/banner02.jpg" alt="">
				</a>
			</li>
			<li>
				<a href="#">
					<img src="images/banner03.jpg" alt="">
				</a>
			</li>
		</ul>
	</section>

	<!--左边楼层浮动-->
	<aside class="index_floor" id="indexfloor">
	    <a class="sel" href="javascript:void(0);"><span>进口商品</span><span>进口商品</span></a>
	    <a href="javascript:void(0);" class=""><span>食品饮料</span><span>食品饮料</span></a>
	    <a href="javascript:void(0);" class=""><span>粮油副食</span><span>粮油副食</span></a>
	    <a href="javascript:void(0);" class=""><span>美容洗护</span><span>美容洗护</span></a>
	    <a href="javascript:void(0);" class=""><span>家居家电</span><span>家居家电</span></a>
	    <a href="javascript:void(0);" class=""><span>家庭清洁</span><span>家庭清洁</span></a>
	    <a href="javascript:void(0);" class=""><span>母婴用品</span><span>母婴用品</span></a>
	    <a href="javascript:void(0);" class=""><span>生活服务</span><span>生活服务</span></a>
	</aside>

	<!--右边悬浮框-->
	<aside class="float_box">
		<a href="#" class="user"></a>
		<a href="#" class="car">购物车<span>1</span></a>
		<a href="#" class="col"></a>
		<a href="#" class="history"></a>
		<a href="#" class="ewm"></a>
		<div class="ewm_show"><img src="images/ewm.jpg"></div>
		<a href="#" class="service"></a>
		<a href="#" class="go_top">TOP</a>
	</aside>

	<!--main-->
	<section class="main">
		<div class="index_tj">
			<h3>新品推荐</h3>
			<div class="left_pic"><a href="#"><img src="images/pic02.jpg" width="400" height="400"></a></div>
			<ul class="right_pro">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_one">
				<p class="tit">进口商品</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_two">
				<p class="tit">食品饮料</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_three">
				<p class="tit">粮油副食</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_four">
				<p class="tit">美容洗护</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_five">
				<p class="tit">家居家电</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_six">
				<p class="tit">家庭清洁</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_seven">
				<p class="tit">母婴用品</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>

		<div class="index_part">
			<div class="clear"></div>
			<div class="left_eight">
				<p class="tit">生活服务</p>
				<menu>
					<div class="clear"></div>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口牛奶</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口母婴用品</a>
					<a href="#">进口牛奶</a>
					<div class="clear"></div>
				</menu>
				<a href="#" class="b_pic"><img src="images/pic01.png"></a>
			</div>
			<ul class="right_list">
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic04.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<li>
					<a href="#">
						<img src="images/pic03.jpg">
						<p class="p1">￥12.5</p>
						<p class="p2">菲律宾进口CEBU宿雾牌芒果干</p>
					</a>
				</li>
				<div class="clear"></div>
			</ul>
			<div class="clear"></div>
		</div>
	</section>

	<section class="main">
		<div class="slogen">
			<span><i><img src="images/bottom01.png"></i>品质保证  服务精致</span>
			<span><i><img src="images/bottom02.png"></i>品类齐全  一站购物</span>
			<span><i><img src="images/bottom03.png"></i>中心直发  配送快捷</span>
			<span class="pr0"><i><img src="images/bottom04.png"></i>退换随心  低价无忧</span>
		</div>
	</section>

	<footer class="foot">
		<div class="main">
			<dl>
				<dt>新手指南</dt>
				<dd><a href="#">用户注册</a></dd>
				<dd><a href="#">购物流程</a></dd>
				<dd><a href="#">新手帮助</a></dd>
				<dd><a href="#">隐私声明</a></dd>
			</dl>
			<dl>
				<dt>支付方式</dt>
				<dd><a href="#">在线支付</a></dd>
				<dd><a href="#">支付宝支付</a></dd>
				<dd><a href="#">网银支付</a></dd>
				<dd><a href="#">公司转账</a></dd>
			</dl>
			<dl>
				<dt>配送方式</dt>
				<dd><a href="#">送货上门</a></dd>
				<dd><a href="#">配货点自提</a></dd>
				<dd><a href="#">配送标准</a></dd>
				<dd><a href="#">配送查询</a></dd>
			</dl>
			<dl>
				<dt>售后服务</dt>
				<dd><a href="#">退货流程</a></dd>
				<dd><a href="#">售后政策</a></dd>
			</dl>
			<dl>
				<dt>帮助中心</dt>
				<dd><a href="#">常见问题</a></dd>
				<dd><a href="#">关于我们</a></dd>
				<dd><a href="#">联系我们</a></dd>
				<dd><a href="#">交易条款</a></dd>
				<dd><a href="#">商家入住</a></dd>
			</dl>
			<div class="b_contact">
				<a href="#" class="qq"><img src="images/qq01.png"></a>
				<span><i><img src="images/phone01.png"></i>13888888888</span>
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

</body>
</html>