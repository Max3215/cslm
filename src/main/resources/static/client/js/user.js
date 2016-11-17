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

function drawFrom(bankId){
	var url = "/user/draw/from";
	var loadData = {"bankId":bankId};
	$("#draw_from").load(url,loadData);
}

function searchBank(){
	var url = "/user/search/bank";
	var loadData = null;
	$("#bank_list").load(url,loadData);
}

function deleteBank(bankId){
	layer.confirm('确定要删除这张卡的信息记录？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
		        url : "/user/delete/bank",
		        data : {"bankId":bankId},
		        type :"post",
		        success:function(data){
		        	if(data.code == 1 ){
		        		layer.closeAll();
		        		searchBank();
		            }else{
		            	layer.msg(data.msg, {icon: 2 ,time: 1000});
		            }
		        }
		    })
		}, function(){
			layer.closeAll();
		});
}