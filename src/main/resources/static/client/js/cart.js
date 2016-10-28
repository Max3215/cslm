// 点击复选框
function toggleSelect(id)
{
    $.ajax({
        type:"post",
        url:"/cart/toggleSelect",
        data:{"id":id},
        success:function(data){
            $("#cartgoods").html(data);
        }
    });
}

// 点击全选框
function toggleAllSelect(sid)
{
    $.ajax({
        type:"post",
        url:"/cart/toggleAll",
        data:{"sid":sid},
        success:function(data){
            $("#cartgoods").html(data);
        }
    });
}

// 商品数量加1
function addNum(id)
{
    $.ajax({
        type:"post",
        url:"/cart/numberAdd",
        data:{"id":id},
        success:function(data){
            $("#cartgoods").html(data);
        }
    });
}

// 商品数量减1
function minusNum(id)
{
    $.ajax({
        type:"post",
        url:"/cart/numberMinus",
        data:{"id":id},
        success:function(data){
            $("#cartgoods").html(data);
        }
    });
}

function delCartItem(id)
{
    if (null == id)
    {
        return;
    }
    layer.confirm('确定要把此商品移除购物车？',{
		btn: ['确定','取消'] //按钮
	}, function(){
		$.ajax({
	        type:"post",
	        url:"/cart/del",
	        data:{"id": id},
	        success:function(data){
	        	layer.closeAll();
	            $("#cartgoods").html(data);
	        }
	    });
	}, function(){
		layer.closeAll();
	});
}

function goNext(goodsNum)
{
	
    if (0==goodsNum)
    {
    	layer.msg('请至少选择一种商品!', {icon: 2,time: 2000});
        return false;
    }
    window.location.href="/order/info";
}


// 输入数量
function chechQuantity(cid,quantity)
{
	$.ajax({
        type:"post",
        url:"/cart/changeQuantity",
        data:{"id": cid,"quantity":quantity},
        success:function(data){
            if(data.code==1){
            	window.location.reload();
            }else{
            	layer.alert(data.msg)
            }
        }
    });
}