function subAddress(){
	
	layer.confirm('需要将此地址设为默认地址吗？',{
        btn: ['确定','取消'] //按钮
        }, function(){
            $("#defaultAddr").val(true);
            $("#form1").submit();
        }, function(){
        	$("#form1").submit();
        });
	
}
function defaultAddr(id){
	layer.confirm('确定要将这个地址设为默认地址吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	window.location.href="/user/address/default?id="+id
    }, function(){
    	layer.closeAll();
    });
}

function deleteAddr(id){
	layer.confirm('确定要删除这个地址吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	window.location.href="/user/address/delete?id="+id
    }, function(){
    	layer.closeAll();
    });
}

function deleteCollect(id){
	layer.confirm('确定要取消收藏吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	$.ajax({
    		type:"post",
    		url:"/user/collect/del",
    		data:{"id":id},
    		success:function(data){
    			if(data.code==1){
    				if(data.code==1){
    		  			  layer.msg(data.msg, {icon: 1 ,time: 1000});
    		  			  window.location.reload();
    		      	   }else{
    		  			  layer.msg(data.msg, {icon: 2 ,time: 1000});
    		      	   }
    			}
    		}
    	});
    }, function(){
    	layer.closeAll();
    });
}