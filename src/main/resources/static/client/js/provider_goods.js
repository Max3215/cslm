//全选/取消
function selectAll(){
     var animals = document.getElementsByName("listChkId");         
      for(var i=0;i<animals.length;i++){              
        if(document.getElementById("checkAll").checked==true){  
                   animals[i].checked=true;              
         }else{ 
                          animals[i].checked=false;                  
          }         
     }     
}


//超市中心商品上下架
function goodsOnSale(type,pgId){
    $.ajax({
        url : "/provider/goods/onsale",
        data : {"type":type,"pgId":pgId},
        type : "post",
       success:function(data){
    	   if(data.code==1){
			  layer.msg(data.msg, {icon: 1 ,time: 1000});
			  window.location.reload();
    	   }else{
			  layer.msg(data.msg, {icon: 2 ,time: 1000});
    	   }
        }
    })
}

//超市中心删除商品
function deleteGoods(pgId){
	layer.confirm('确定要把此商品移除购物车？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
				url : "/provider/goods/delete",
		        data : {"pgId":pgId},
		        type :"post",
		        success:function(data){
		        	layer.msg(data.msg, {icon: 1 ,time: 1000});
		        	if(data.code ==1 ){
		        		window.location.reload();
		        	}
		        }
		    })
		}, function(){
			layer.closeAll();
		});
}


//编辑信息
function editGoods(pro_goodsId,goodsId)
{
    $.ajax({
        type:"post",
        url:"/provider/goods/detail",
        data:{"pro_goodsId": pro_goodsId,"goodsId":goodsId},
        success:function(data){
        	$("#detail_div").html(data);
        	$("#detail_div").css('display','block');
        }
    });
}

//加载规格
function searchSpecifica(goodsId,id){
	var url = "/provider/search/specifica";
	var loadData = {"goodsId":goodsId,"id":id};
	$("#specifica_div").load(url,loadData);
}

//保存规格
function saveSpecifict(){
	var url = "/provider/specifica/save";
	var loadData = $("#spec_form").serializeArray();
	
	$.ajax({
		url : url,
		type : "post",
		data : loadData,
		success : function(data){
			  if(data.code==1){
				  searchSpecifica(data.goodsId,null)
			  }else{
				  layer.msg(data.msg, {icon: 2 ,time: 1000});
			  }
		}
	})
}

//编辑信息保存
function editSaveGoods(){
	var url = "/provider/wholesaling";
	
	var pro_goodsId = $("#pro_goodsId").val();
	var goodsId = $("#goodsId").val();
    var subGoodsTitle = $("#subGoodsTitle").val();
    var code = $("#code").val();
    var goodsMarketPrice = $("#marketPrice").val(); // 市场价
    var goodsPrice = $("#goodsPrice").val(); // 批发价
    var unit = $("#unit").val();
    var leftNumber = $("#leftNumber").val();
    
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    var num = /^\+?[1-9][0-9]*$/;
    if(undefined == goodsMarketPrice || ""==goodsMarketPrice || !reg.test(goodsMarketPrice))
    {
    	layer.msg('商品原价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
    	layer.msg('批发价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == leftNumber || ""==leftNumber || !num.test(leftNumber)){
		layer.msg('请输入正确的库存', {icon: 2 ,time: 1000});
	}
	
    $.ajax({
    	url : url,
    	type : "post",
    	data : {"pro_goodsId":pro_goodsId,
    			"goodsId":goodsId,
    			"goodsPrice":goodsPrice,
    			"goodsMarketPrice":goodsMarketPrice,
    			"subGoodsTitle":subGoodsTitle,
    			"code":code,
    			"unit":unit,
    			"leftNumber":leftNumber},
		success : function(data){
			  if(data.code==1){
				  layer.msg(data.msg, {icon: 1 ,time: 1000});
				  window.location.reload();
			  }else{
				  layer.msg(data.msg, {icon: 2 ,time: 1000});
			  }
		}
    	
    })
	
}


function drawFrom(bankId){
	var url = "/provider/draw/from";
	var loadData = {"bankId":bankId};
	$("#draw_from").load(url,loadData);
}

function searchBank(){
	var url = "/provider/search/bank";
	var loadData = null;
	$("#bank_list").load(url,loadData);
}

function deleteBank(bankId){
	layer.confirm('确定要删除这张卡的信息记录？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
		        url : "/provider/delete/bank",
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