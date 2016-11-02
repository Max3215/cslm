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

function calDefault(id){
	layer.confirm('确定要讲这个地址取消默认地址吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	window.location.href="/touch/user/address/calDefault?id="+id
    }, function(){
    	layer.closeAll();
    });
}

function defaultAddr(id){
	layer.confirm('确定要这个地址设为默认地址吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	window.location.href="/touch/user/address/default?id="+id
    }, function(){
    	layer.closeAll();
    });
}

function deleteAddr(id){
	layer.confirm('确定要删除这个地址吗？',{
        btn: ['确定','取消'] //按钮
    }, function(){
    	window.location.href="/touch/user/address/delete?id="+id
    }, function(){
    	layer.closeAll();
    });
}

