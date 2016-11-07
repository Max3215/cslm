function addPoint(id){
	$.ajax({
		url :"/point/goods/check",
		type : "post",
		data:{"id":id},
		success : function(data){
			if(data.code==1){
				layer.confirm(data.msg,{
            		btn: ['确定','取消'] //按钮
				}, function(){
					window.location.href = "/login";
				}, function(){
					layer.closeAll();
				});
			}else if(data.code==2){
				window.location.href = "/point/goods/exChange?id"=id;
			}else{
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}
		}
	})
}