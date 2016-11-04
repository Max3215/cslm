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