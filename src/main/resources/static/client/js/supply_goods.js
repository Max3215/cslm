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
function goodsAudit(type,pgId){
    $.ajax({
        url : "/supply/goods/onsale",
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
	layer.confirm('确定要把此商品删除？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
		        url : "/supply/goods/delete",
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
// 编辑信息，弹窗详情框
function editGoods(sup_goodsId,goodsId)
{
    $.ajax({
        type:"post",
        url:"/supply/goods/detail",
        data:{"sup_id": sup_goodsId,"goodsId":goodsId},
        success:function(data){
        	$("#detail_div").html(data);
        	$("#detail_div").css('display','block');
        }
    });
}

// 加载规格
function searchSpecifica(goodsId,id){
	var url = "/supply/search/specifica";
	var loadData = {"goodsId":goodsId,"id":id};
	$("#specifica_div").load(url,loadData);
}

// 保存规格
function saveSpecifict(){
	var url = "/supply/specifica/save";
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
// 删除规格
function delSpecifica(goodsId,id){
	$.ajax({
		url : "/supply/specifica/delete",
		type : "post",
		data : {"id":id},
		success : function(data){
			  if(data.code==1){
				  searchSpecifica(goodsId,null)
			  }else{
				  layer.msg(data.msg, {icon: 2 ,time: 1000});
			  }
		}
	})
}

// 保存规格
function editSaveGoods(){
	var url = "/supply/distribution";
	
	var sup_goodsId = $("#sup_goodsId").val(); // 分销商品ID
	var goodsId = $("#goodsId").val(); // 商品库ID
    var subGoodsTitle = $("#subGoodsTitle").val(); // 副标题
    var code = $("#code").val(); // 编码
    var goodsMarketPrice = $("#marketPrice").val(); // 原价
    var goodsPrice = $("#goodsPrice").val(); // 售价
    var unit = $("#unit").val(); // 单位
    var leftNumber = $("#leftNumber").val(); // 库存
    var shopReturnRation = $("#shopReturnRation").val(); // 返利比
    
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/; // 价格验证
    var num = /^\+?[1-9][0-9]*$/;  // 正整数验证
    var  retion = /(^[0]+(.[0-9]{2})?$)/;   // 小数验证
    
    if(undefined == goodsMarketPrice || ""==goodsMarketPrice || !reg.test(goodsMarketPrice))
    {
    	layer.msg('商品原价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
    	layer.msg('销售价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == shopReturnRation || ""==shopReturnRation || !retion.test(shopReturnRation)){
		layer.msg('返利比格式输入错误', {icon: 2 ,time: 1000});
	}
    
    if(undefined == leftNumber || ""==leftNumber || !num.test(leftNumber)){
		layer.msg('请输入正确的库存', {icon: 2 ,time: 1000});
	}
	
    $.ajax({
    	url : url,
    	type : "post",
    	data : {"sup_goodsId":sup_goodsId,
    			"goodsId":goodsId,
    			"goodsPrice":goodsPrice,
    			"goodsMarketPrice":goodsMarketPrice,
    			"subGoodsTitle":subGoodsTitle,
    			"code":code,
    			"unit":unit,
    			"leftNumber":leftNumber,
    			"shopReturnRation":shopReturnRation},
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