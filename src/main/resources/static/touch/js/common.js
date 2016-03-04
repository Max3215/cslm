//页面计算问题
function setRootSize() {
	var deviceWidth = document.documentElement.clientWidth; 
	if(deviceWidth>640){deviceWidth = 640;}  
	document.documentElement.style.fontSize = deviceWidth / 6.4 + 'px';
}
setRootSize();
window.addEventListener('resize', function () {
    setRootSize();
}, false);
$(document).ready(function(){
	setRootSize();
});

//首页banner效果 - 本效果由昆明天度网络IRIS原创制作
function indexBanner(boxid,sumid,_go,_speed,numid){
	var startX,startY,endX,endY;//定义判断变量
	var _box = document.getElementById(boxid);
	var _sum = $("#"+sumid);
	var _arr = $("#"+sumid+" li");
	var _length = _arr.length;
	var _index = 0;
	var _nexti = 0;
	
	_sum.append(_sum.html());
	_arr = $("#"+sumid+" li");
	
	var _str = "<div id='"+numid+"'><a href='javascript:void(0);' class='sel'></a>";
	for(var i=1;i<_length;i++){
		_str += "<a href='javascript:void(0);'></a>";
		}
	_str += "</div>";
	$("#"+boxid).append(_str);
	var _num = $("#"+numid+" a");
	
	//计算宽度
    //var _width = $(window).width();
	var _width = $("#"+boxid).width();
	var _resize = function(){
		_width = $("#"+boxid).width();
		_arr.width(_width);
		var _move = -_width * _index;
		_sum.css("left",_move+"px");
		};
	_resize();
	$(window).resize(function(){_resize();});
	
	var nextImg = function(){
		_index++;
		_nexti++;
		
		if(_index >= _length){
			_index = 0;
			}
		if(_nexti > _length){
			_nexti = _index;
			_sum.css("left","0px");
			}
		if(_sum.is(":animated")){
			_sum.stop(true,true);
			}
		var _move = -_width * _nexti;
		_sum.animate({left:_move+"px"},_go);
		_num.eq(_index).addClass("sel").siblings().removeClass("sel");
	};
	
	var lastImg = function(){
		_index--;
		_nexti--;
		
		if(_index < 0){
			_index = _length-1;
			}
		if(_nexti < 0){
			var _m = -_width * _length;
			_sum.css("left",_m+"px");
			_nexti = _index;
			}
		if(_sum.is(":animated")){
			_sum.stop(true,true);
			}
		var _move = -_width * _nexti;
		_sum.animate({left:_move+"px"},_go);
		_num.eq(_index).addClass("sel").siblings().removeClass("sel");
	};
	
	var cartoon = setInterval(nextImg,_speed);
	
	var touchStart = function(event){
		clearInterval(cartoon);
		var touch = event.touches[0];
        startX = touch.pageX;
		startY = touch.pageY;
		};
	var touchMove = function(event){
		var touch = event.touches[0];
		var endPos = {x:startX-touch.pageX,y:startY-touch.pageY};
		var isScrolling = Math.abs(endPos.x)< Math.abs(endPos.y) ? 1:0;//isScrolling为1时，表示纵向滑动，0为横向滑动
		if(isScrolling === 0){
			event.preventDefault();//这里很重要！！！
			endX = (startX-touch.pageX);
		    //endY = (startY-touch.pageY);
			}
		};
	var touchEnd = function(event){
		if(endX > 30){
			nextImg();
			}
		if(endX < -30){
			lastImg();
			}
		cartoon = setInterval(nextImg,_speed);
		};
	
	_box.addEventListener("touchstart", touchStart, false);
    _box.addEventListener("touchmove", touchMove, false);
    _box.addEventListener("touchend", touchEnd, false);
	
}//该方法结束


function proMenuDown(obj){
	var _obj = $(obj);
	var _box = _obj.parent().find(".pro_menu_part");
	if(_obj.hasClass("sel")){
		_obj.removeClass("sel");
		_box.slideUp(200);
		}else{
			_obj.addClass("sel");
			_box.slideDown(200);
			}
}



function menuCheckShow(menuid,mname,sumid,sname,_hover,_starnum){
	var _menu = $("#"+menuid).find(mname);
	var _arr = $("#"+sumid).find(sname);
	var _index = _starnum;
	_menu.eq(_index).addClass(_hover).siblings().removeClass(_hover);
	_arr.eq(_index).css("display","block").siblings().css("display","none");

	_menu.click(function(){
		_index = $(this).index();
		_menu.eq(_index).addClass(_hover).siblings().removeClass(_hover);
	_arr.eq(_index).css("display","block").siblings().css("display","none");
		});
}








