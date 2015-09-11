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


//商品详情图片效果

;(function(){ 

	var EventUtil,Get,Element; 
	EventUtil = {
		addHandler: function(ele, type, handler) {
			if (ele.addEventListener) {
				ele.addEventListener(type, handler, false)
			} else if (ele.attachEvent) {
				ele.attachEvent("on" + type, handler)
			} else {
				ele["on" + type] = handler;
			}
		},
		removeHandler: function(element, type, handler) {
			if (element.removeEventListener) {
				element.removeEventListener(type, handler, false);
			} else if (element.detachEvent) {
				element.detachEvent("on" + type, handler);
			} else {
				element["on" + type] = null;
			}
		},
		getEvent: function(event) {
			return event ? event : window.event;
		},
		getTarget: function(event) {
			return event.target || event.srcElement;
		},
		preventDefault: function(event) {
			if (event.preventDefault) {
				event.preventDefault();
			} else {
				event.returnValue = false;
			}
		},
		stopPropagation: function(event){
	        if (event.stopPropagation){
	            event.stopPropagation();
	        } else {
	            event.cancelBubble = true;
	        }
	    } 
	};

	Get = {
		byId: function(id) {
			return typeof id === "string" ? document.getElementById(id) : id
		},
		byClass: function(sClass, oParent) {
			var aClass = [];
			var reClass = new RegExp("(^| )" + sClass + "( |$)");
			var aElem = this.byTagName("*", oParent);
			for (var i = 0; i < aElem.length; i++) reClass.test(aElem[i].className) && aClass.push(aElem[i]);
			return aClass
		},
		byTagName: function(elem, obj) {
			return (obj || document).getElementsByTagName(elem)
		}
	}; 
 	
 	Element = {
 		hasClass:function(obj,name){
			return (' '+obj.className+' ').indexOf(' '+name+' ') > -1 ? true : false;
		},
		addClass : function(obj,name){
			if(this.hasClass(obj,name)) return;
			obj.className += ' ' + name;
		},
		removeClass : function(obj,name){
			obj.className = obj.className.replace(new RegExp('(^|\\s)' +name+ '(?:\\s|$)'),'$1').replace(/\s{1,}/g,' ');
		}
 	}

	function MagnifierF(){ 
		this.init.apply(this,arguments);  
	}

	MagnifierF.prototype = {
		init: function(id){
			var _is = this; 
			this.magWrap = Get.byId(id);
			this.magMain = this.magWrap.children[0];
			this.mW = this.magMain.offsetWidth;
			this.mH = this.magMain.offsetHeight;
			this.magImg = this.magMain.getElementsByTagName('img')[0];
			this.mImgSrc = this.magImg.getAttribute('src').slice(0,-4);

			this.specBox = Get.byClass("propic_num",this.magWrap)[0];
			this.specUl = this.specBox.getElementsByTagName('ul')[0];
			this.specItem = this.specBox.getElementsByTagName('li');

			_is.specFn();
			_is.setEventFn().dragEvent();
			
		},
		setEleFn: function(){   
			var _is = this,
				_html1 = "",
				oFrag = document.createDocumentFragment(),
				oFrag2 = document.createDocumentFragment(); 

			_is.oMD = document.createElement('div');
			_is.oMD.className = "MagnifierDrag"; 
			_is.oMD.style.cssText = 'width:' + _is.mW/2 +'px;height:' + _is.mH/2 + 'px;';
			_is.oMD.innerHTML = "&nbsp;";

			_is.oMP =  document.createElement('div');
			_is.oMP.className = 'MagnifierPop';
			_is.oMP.style.cssText =  'width:' + _is.mW +'px;height:' + _is.mH + 'px;right:' + (-_is.mW-10) + 'px;';  
			
			_is.oMI = document.createElement('div');
			_is.oMI.className ='MagnifierImg';
			_is.oMI.style.cssText = 'width:' + _is.mW*2 + 'px;height:' + _is.mH*2 + 'px;';
			_html1 = '<img style="width:100%;height:100%;" src="' + _is.mImgSrc + '.jpg">'
			_is.oMI.innerHTML = _html1; 

			_is.oMP.appendChild(_is.oMI)

			oFrag.appendChild(_is.oMD);
			oFrag2.appendChild(_is.oMP);   

			_is.magMain.appendChild(oFrag);
			_is.magWrap.appendChild(oFrag2);  

		},
		removeFn :function(){
			var _is = this;
				_is.magMain.removeChild(_is.oMD);
				_is.magWrap.removeChild(_is.oMP);  
		},
		setMousemoveFn :function(event){
			var _is = this,
				 
				_WinScrLeft = document.documentElement.scrollLeft || document.body.scrollLeft,
				_WinScrTop = document.documentElement.scrollTop || document.body.scrollTop;

				_x = event.clientX + _WinScrLeft -  
				(_is.magWrap.getBoundingClientRect().left  + _WinScrLeft) - _is.oMD.offsetWidth/2;

				_y = event.clientY  + _WinScrTop - 
				(_is.magMain.getBoundingClientRect().top  + _WinScrTop) - _is.oMD.offsetHeight/2;
				
				_l = _is.magMain.offsetWidth - _is.oMD.offsetWidth;
				_t = _is.magMain.offsetHeight - _is.oMD.offsetHeight;
				
				_l2 = - (_is.oMI.offsetWidth - _is.magMain.offsetWidth);
				_t2 = - (_is.oMI.offsetHeight - _is.magMain.offsetHeight);

				if( _x < 0 )
		        {
		            _x = 0;  
		        }
		        else if( _x > _l )
		        {
		            _x = _l;
		        }
		         
		        if( _y < 0 )
		        {
		            _y = 0;  
		        }
		        else if( _y > _t )
		        {
		            _y = _t;
		        }
			 	
				 
				_is.oMD.style.left = _x + "px";
				_is.oMD.style.top  = _y + "px";
				 
				
				_bigx = _x / _l;
				_bigy = _y / _t;
					

				_is.oMI.style.left = _bigx * _l2 + "px";
				_is.oMI.style.top = _bigy * _t2 + "px";  
		},
		setEventFn: function(){
			var _is = this,
				_x = 0,
				_y = 0,
				_l = 0,
				_t = 0,
				_bigx = 0,
				_bigy = 0,
				_l2 = 0, 
				_t2 = 0;

			function handleEvent(event){
				event = EventUtil.getEvent(event);   

				switch(event.type){
					case "mouseenter":  
						_is.setEleFn(); 
					break;
					case "mousemove": 
						if (_is.oMD) {
							_is.setMousemoveFn(event);
						}
					break;
					case "mouseleave":   
						_is.removeFn(); 
					break;
				} 
				
			}	
			return {
				dragEvent: function() { 

					EventUtil.addHandler(_is.magMain, "mouseenter", handleEvent);
					EventUtil.addHandler(_is.magMain, "mousemove", handleEvent);
					EventUtil.addHandler(_is.magMain, "mouseleave", handleEvent);  
				} 
			}
			
		},
		specFn: function(){
			var _is = this, _oSpImg, _oISrc,
				oLBtn = Get.byClass("spe_leftBtn",_is.magWrap)[0],
				oRBtn = Get.byClass("spe_rightBtn",_is.magWrap)[0],
				oLiW = this.specUl.getElementsByTagName('li')[0].offsetWidth + 6,
				_len = _is.specItem.length,  
				n = 0,
				l = null, 
				r = null;  

			function TabFn(event){   
				var target = EventUtil.getTarget(event),
					i = 0;  

				if (target.nodeName != "UL") { 

					if (target.nodeName == "IMG") {
						target = target.parentNode;
					}
					for (; i < _len; i++){
						_is.specItem[i].className = '';
					}
					target.className = 'on';

					_oSpImg = target.getElementsByTagName('img')[0]; 
					_oISrc = _oSpImg.getAttribute('src'); 
					
					_is.magImg.setAttribute('src',_oISrc);
					_is.mImgSrc =  _oISrc.slice(0,-4);  
				}
				 
			}   
			EventUtil.addHandler(_is.specUl,"mouseover",TabFn);

			function moveFn(event){
				var target = EventUtil.getTarget(event);
				 
				if (target.className.indexOf("spe_rightBtn") > -1 ) {
					r = ++n; 
					if (r > _len - 6)
							Element.removeClass(target,"on");

					if (r > _len - 5) { 

						n = _len - 5; 
						return false;
					}else{   

						_is.buttur(_is.specUl, {left:-(r*oLiW)}); 
						Element.addClass(oLBtn,"on");
					} 
				} 
				if (target.className.indexOf("spe_leftBtn") > -1 ) {
					l = --n; 
					if (l < 1)
						Element.removeClass(target,"on");

					if (l < 0) {

						n = 0;  
						return false; 
					}else{ 

						_is.buttur(_is.specUl, {left:-(l*oLiW)});  
						Element.addClass(oRBtn,"on"); 
					}  
				} 
			}

			if(_len > 4 ){

				Element.addClass(oRBtn,"on");  
				EventUtil.addHandler(_is.magWrap,"click",moveFn);
			}
			 

				 			 
		},
		buttur: function(ele, obj) {

	        window.clearTimeout(ele.timer);

	        var _this = this,
	            end = null;

	        for (direc in obj) {

	            var direc1 = direc.toLowerCase(),
	                strOffset = "offset" + direc1.substr(0, 1).toUpperCase() + direc1.substring(1).toLowerCase(),
	                target = obj[direc],
	                nSpeed = (target - ele[strOffset]) / 8; 
	                
	            nSpeed = nSpeed >= 0 ? Math.ceil(nSpeed) : Math.floor(nSpeed);
	            ele.style[direc1] = ele[strOffset] + nSpeed + "px";
	            end += nSpeed;

	        }

	        if (end)

	            if (typeof fnCallback == "function") {
	                fnCallback.call(ele);
	            } else {

	                ele.timer = window.setTimeout(function() {
	                    _this.buttur(ele, obj)
	                }, 20);

	            }
	    }  

	} 
    
	window['MagnifierF'] = function(id){
		return new MagnifierF(id);
	}

})()


window.onload = function(){
 

	  MagnifierF("proinfo_left");
		 
}

//弹出框
function submitSearch(){
    var search = document.getElementById("search_form");
    search.submit();
}




























