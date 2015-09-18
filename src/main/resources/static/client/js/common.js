function navDownList(boxid, _sumname, _showname) {
	$("#mainnavdown").append("<div id='mainnavdownbg'></div>");
	var _mybg = $("#mainnavdownbg");
	
    var _box = $("#" + boxid);
    var _arr = _box.find(_sumname);
    var _hover = _box.find(_showname);
    _arr.hover(function () {
        if (_hover.is(":animated")) { _hover.stop(true, true); }
        var _height = $(this).height() + 5;
        $(this).find(".bg").height(_height);
        $(this).find(_showname).css("display","block");

        var _index = $(this).index();
        var _top = 0;
        for (var i = 0; i < _index; i++) {
            _top = _top - _arr.eq(i).height() + 30;
        }
        $(this).find(_showname).css("top", _top + "px");
        _top = -_top - 8;


    }, function () {
        if (_hover.is(":animated")) { _hover.stop(true, true); }
        _hover.css("display","none");
    });
	
	_arr.hover(function(){
		_mybg.css("display","block");
		},function(){
			_mybg.css("display","none");
			});
}

//下拉菜单
function menuDownList(boxid, _showname, _name, _hover) {
    var _box = $("#" + boxid);
    var _show = _box.find(_showname);
    var _chek = _box.find(_name);
    _box.hover(function () {
        if (_show.is(":animated")) { _show.stop(true, true); }
        _chek.addClass(_hover);
        _show.fadeIn(100);
    }, function () {
        if (_show.is(":animated")) { _show.stop(true, true); }
        _chek.removeClass(_hover);
        _show.fadeOut(100);
    });
}

//banner
function adChange(outid,imgid,numid,carspeed,fadespeed){
	//根据ID取值
	var _out = $("#"+outid);
	var _img = $("#"+imgid);
	var _index = 0;//滚动索引
	var _arr = _img.find("li");//图片数组
	var _num = _arr.length;//图片数组数量
	
	//按照图片数量添加相应数字
	var _numstr = "<div id='"+numid+"'><a class='sel'>1</a>";
	if(_num >= 2){
		for(var j=2;j<=_num;j++){
	 	 _numstr += "<a>"+j+"</a>";
	 	 }
		}
	_numstr += "</div>";
	_out.append(_numstr);//判断数量，并添加数字
	var _numarr = $("#"+numid).find("a");//获取已添加的数字信息
	
	//执行动画
	var _cartoon = setInterval(function(){
		  _arr.eq(_index).fadeOut(fadespeed);
			_numarr.eq(_index).removeClass("sel");
			if(_index == _num-1){
				_index = -1;
				}
			_arr.eq(_index+1).fadeIn(fadespeed);
			_numarr.eq(_index+1).addClass("sel");
			_index++;
		},carspeed);
		
	//鼠标事件
	  _arr.find("a").bind("click",function(){
   	  clearInterval(_cartoon);
   	});
    _arr.find("a").bind("click",function(){
   	  _cartoon = setInterval(function(){
		  _arr.eq(_index).fadeOut(fadespeed);
			_numarr.eq(_index).removeClass("sel");
			if(_index == _num-1){
				_index = -1;
				}
			_arr.eq(_index+1).fadeIn(fadespeed);
			_numarr.eq(_index+1).addClass("sel");
			_index++;
		},carspeed);
   	});
   
   //数字事件
   _numarr.bind("click",function(){
   	  clearInterval(_cartoon);
   	  var _nn = _numarr.index(this);
   	  for(var i=0;i<=_num;i++){
   	  	_arr.eq(i).fadeOut(fadespeed);
   	  	_numarr.eq(i).removeClass("sel");
   	  	}
   	  _index = _nn-1;
   	  
   	  _arr.eq(_index).fadeOut(fadespeed);
			_numarr.eq(_index).removeClass("sel");
			if(_index == _num-1){
				_index = -1;
				}
			_arr.eq(_index+1).fadeIn(fadespeed);
			_numarr.eq(_index+1).addClass("sel");
			_index++;
   	});
		
}







//楼层计算
function indexFloor(boxid,_name){
	var _box = $("#"+boxid);
	var _arr = _box.find(_name);
	
	var _floornum = function(_obj,_top,_bot){
		$(window).scroll(function(){
		  var _now = $(window).scrollTop();
		  if(_now>=_top && _now<_bot){
		  _obj.addClass("sel").siblings().removeClass("sel");
		  }
	       });
		};//fun END
	

	_floornum(_arr.eq(0),0,1300);
	_floornum(_arr.eq(1),1300,1750);
	_floornum(_arr.eq(2),1750,2100);
	_floornum(_arr.eq(3),2200,2550);
	_floornum(_arr.eq(4),2650,3100);
	_floornum(_arr.eq(5),3100,3450);
	_floornum(_arr.eq(6),3550,3900);
	_floornum(_arr.eq(7),4000,4450);
	
	
	$(window).scroll(function(){
		var _now = $(window).scrollTop();
		if(_box.is(":animated")){_box.stop(true,true);}
		if(_now > 800 && _now < 4450){
			_box.fadeIn(200);
			}else{
			_box.fadeOut(200);
			}
		});
	
	var _gofloor = function(_seat){
		//$(window).scrollTop(_seat);
		$("html,body").animate({scrollTop:_seat},300);
		};//fun END
	_arr.eq(0).click(function(){ _gofloor(850); });
	_arr.eq(1).click(function(){ _gofloor(1300); });
	_arr.eq(2).click(function(){ _gofloor(1750); });
	_arr.eq(3).click(function(){ _gofloor(2200); });
	_arr.eq(4).click(function(){ _gofloor(2650); });
	_arr.eq(5).click(function(){ _gofloor(3100); });
	_arr.eq(6).click(function(){ _gofloor(3550); });
	_arr.eq(7).click(function(){ _gofloor(4000); });
}



//弹出框
function submitSearch(){
    var search = document.getElementById("search_form");
    search.submit();
}

//超市选择
function changeDistributor(){
	var distributor = $("#diys").val();
	$.ajax({
		url : "/distributor/change",
		async : true,
		type : 'GET',
		dataType : "json",
		data : {"disId":distributor},
		success : function(data){
			if(data.msg){
				alter(data.msg)
			}
			$("#mar_box").hide();
		}
	})
	
}

function chooseDistributor(disId){
	$.ajax({
		url : "/distributor/change",
		async : true,
		type : 'GET',
		dataType : "json",
		data : {"disId":disId},
		success : function(data){
			if(data.msg){
				alter(data.msg)
			}
			$("#mar_box").hide();
		}
	})
}

$(function() {
	 $("#add").citySelect({
	    nodata:"none",
	    prov: "云南",
	    city: "昆明",
	    required:false
	    });

})


















