// 收藏商品
function addCollect(goodsId)
{
    if (undefined == goodsId)
    {
        return;
    }
    
    $.ajax({
        type:"post",
        url:"/touch/user/collect/add",
        data:{"disgId": goodsId},
        dataType: "json",
        success:function(res){
            // 需登录
            if (res.code==1){
		    	layer.confirm(res.message,{
		    		btn: ['确定','取消'] //按钮
				}, function(){
					window.location.href = "/touch/login?goodsId="+goodsId;
				}, function(){
					layer.closeAll();
				});
		    }
        }
    });
}
// 收藏店铺
function collectShop(disId,goodsId){
    if(undefined == disId){
        return ;
    }
    
    $.ajax({
        type:"post",
        url:"/touch/user/collect/shop",
        data:{"disId": disId},
        dataType: "json",
        success:function(res){
            if (res.code==1){
            	layer.confirm(res.message,{
            		btn: ['确定','取消'] //按钮
				}, function(){
					window.location.href = "/touch/login?goodsId="+goodsId;
				}, function(){
					layer.closeAll();
				});
            }else{
            	layer.msg(res.message);
            }
        }
    });
    
}

// 选择规格
function chooseSpec(tag,id){
	$.ajax({
		url : "/goods/specifica",
		type : "post",
		data : {"id":id},
		success: function(data){
			if(data.code ==1){
				$(".part dl dd a").removeClass("act");
				$(tag).addClass("act");
				$("#specId").val(id);
				
				$("#left_font").html("（库存："+data.num+"）")
				$("#leftNumber").val(data.num);
				
				var q = parseInt($("#quantity").val());
				if (q > data.num){
			        layer.msg('已达到库存最大值',  {icon: 2,time: 2000});
			        $("#quantity").val(data.num);
			    }
			}else{
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}
		}
		
	})
}

// 增加商品数量
function addNum(){
    var q = parseInt($("#quantity").val());
    var ln = parseInt($("#leftNumber").val());
    
    if (q < ln){
        $("#quantity").val(q+1);
    }else{
    	layer.msg('已达到库存最大值',  {icon: 2,time: 2000});
        $("#quantity").val(ln);
    }
    
}
// 减少商品数量
function minusNum(){
    var q = parseInt($("#quantity").val());
        
    if (q > 1){
        $("#quantity").val(q-1);
        $("#number").val(q-1);
    }
}

// 手动输入数量
function checkNumber(num)
{
	var q = parseInt($("#quantity").val());
    var ln = parseInt($("#leftNumber").val());
    if(undefined == q || ""==q || isNaN(q)){
		quantity = 1;
	}
    
    if (q < ln){
        $("#quantity").val(q+1);
    }else{
    	layer.msg('已达到库存最大值',  {icon: 2,time: 2000});
        $("#quantity").val(ln);
    }
}

function addCart(dis_id){
	var isSpec = $("#isSpec").val();
    var specId = $("#specId").val();
	var quantity = parseInt($("#quantity").val());
	
	if( "true" == isSpec){
		if(undefined == specId || ""== specId){
			$('.pro_eject').fadeIn(300);
			return;
		}
	}
	
	if(undefined == quantity || ""==quantity || isNaN(quantity)){
		quantity = 1;
	}
	
    $.ajax({
		url : "/cart/addIncart",
		type : "post",
		data : {"dis_id":dis_id,
				"isSpec":isSpec,
				"specId":specId,
				"quantity":quantity},
		success: function(data){
			if(data.code ==1){
				layer.msg(data.msg,  {icon: 6,time: 2000});
				$('.pro_eject').fadeOut(300);
				$("#specId").val('');
				$(".part dl dd a").removeClass("act");
			}else{
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}
		}
	});
}
function byGoodsNow(dis_goodsId){
	var isSpec = $("#isSpec").val();
    var specId = $("#specId").val();
	var quantity = parseInt($("#quantity").val());
	
	if("true" == isSpec){
		if(undefined == specId || ""== specId){
			$('.pro_eject').fadeIn(300);
			return;
		}
	}
	if(undefined == quantity || ""==quantity || isNaN(quantity)){
		quantity = 1;
	}
	$.ajax({
		url : "/cart/byNow",
		type : "post",
		data : {"dis_id":dis_goodsId,
				"isSpec":isSpec,
				"specId":specId,
				"quantity":quantity},
		success: function(data){
			if(data.code ==0){
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}else if(data.code ==1){
				layer.confirm(data.msg,{
            		btn: ['确定','取消'] //按钮
				}, function(){
					window.location.href = "/touch/login?goodsId="+dis_goodsId;
				}, function(){
					layer.closeAll();
				});
			}else if(data.code ==2){
				window.location.href = "/touch/order/info";
			}
		}
	})
	
}