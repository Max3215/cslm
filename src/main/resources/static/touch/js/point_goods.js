function addPoint(id){
	$.ajax({
		url :"/point/goods/check",
		type : "post",
		data:{"id":id},
		success : function(data){
			if(data.code==1){
					window.location.href = "/touch/login";
			}else if(data.code==2){
				window.location.href = "/touch/point/goods/exChange?id="+id;
			}else{
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}
		}
	})
}

// 提交兑换
function subForm(){
	var addressId = $("#addressId").val();
	
	var num = /^\+?[1-9][0-9]*$/;
	if(undefined == addressId || ""==addressId || !num.test(addressId)){
		layer.msg('请选择地址', {icon: 2 ,time: 1000});
		return ;
	}
	
	$("#form1").submit();
}

// 兑换订单状态修改
function orderFinish(orderId,statusId){
	var msg ="确定已收到兑换商品？";
	if(statusId ==4){
		msg="确定取消兑换此商品，取消后积分将返回。";
	}
	layer.confirm(msg,{
		btn: ['确定','取消'] //按钮
	}, function(){
		$.ajax({
			url :"/touch/point/order/param",
			type : "post",
			data:{"orderId":orderId,"statusId":statusId},
			success : function(data){
					if(data.code ==1){
						layer.msg(data.msg, {icon: 1 ,time: 2000});
						window.location.reload();
					}else{
						layer.msg(data.msg, {icon: 2 ,time: 2000});
					}
				}
			})
		
	}, function(){
		layer.closeAll();
	});
}


