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

//function selectAll(){  
//    if ($("#checkAll").attr("checked")) {  
//        $(":checkbox").attr("checked", true);  
//    } else {  
//        $(":checkbox").attr("checked", false);  
//    }  
//} 


//超市中心商品上下架
function goodsOnSale(type,disId){
    $.ajax({
        url : "/distributor/goods/onsale",
        data : {"type":type,"page":disId},
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
function deleteDisGoods(disId){
	layer.confirm('确定要把此商品移除购物车？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
		        url : "/distributor/goods/delete",
		        data : {"disId":disId},
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

// 购物车商品
function searchCart(){
	var url = "/distributor/search/cartGoods";
	var loadData = null;
	$("#cart_goodslist").load(url,loadData);
}

// 点击添加，加载批发商品信息
function showProGoods(proId){
	$.ajax({
        type:"post",
        url:"/distributor/proGoods",
        data:{"proId": proId},
        success:function(data){
        	$("#detail_div").html(data);
        	$("#detail_div").css('display','block');
        }
    });
}

//选择规格
function sheckSpec(tag,id){
	$.ajax({
		url : "/distributor/goods/specifica",
		type : "post",
		data : {"id":id},
		success: function(data){
			if(data.code ==1){
				$(".info_tab  a").removeClass("sel");
				$(tag).addClass("sel");
				$("#specId").val(id);
				
				$("#left_label").html("库存"+data.num)
				$("#quantity").attr("max",data.num);
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
//手动输入数量
function checkNumber(num)
{
    var ln = parseInt($("#leftNumber").val());
    
    if (num < ln){
        $("#quantity").val(num);
    }else{
    	layer.msg('已达到库存最大值',  {icon: 2,time: 2000});
        $("#quantity").val(ln);
    }
}


function addGoods(pro_id){
	var isSpec = $("#isSpec").val();
    var specId = $("#specId").val();
	var quantity = parseInt($("#quantity").val());
	
	if( "true" == isSpec){
		if(undefined == specId || ""== specId){
			layer.msg('请先选择规格',  {icon: 2,time: 2000});
			return;
		}
	}
	if(undefined == quantity || ""==quantity || isNaN(quantity)){
		quantity = 1;
	}
    $.ajax({
		url : "/distributor/goods/addOne",
		type : "post",
		data : {"pro_id":pro_id,
				"isSpec":isSpec,
				"specId":specId,
				"quantity":quantity},
		success: function(data){
			if(data.code ==1){
				layer.msg(data.msg,  {icon: 6,time: 2000});
				searchCart();
			}else{
				layer.msg(data.msg,  {icon: 2,time: 2000});
			}
		}
	});
}

//点击复选框
function toggleSelect(id)
{
    $.ajax({
        type:"post",
        url:"/distributor/goods/toggleSelect",
        data:{"id":id},
        success:function(data){
            $("#cart_goodslist").html(data);
        }
    });
}

// 点击全选框
function toggleAllSelect(sid)
{
    $.ajax({
        type:"post",
        url:"/distributor/goods/toggleAll",
        data:{"sid":sid},
        success:function(data){
            $("#cart_goodslist").html(data);
        }
    });
}

// 商品数量加1
function addNum(id)
{
    $.ajax({
        type:"post",
        url:"/distributor/goods/numberAdd",
        data:{"id":id},
        success:function(data){
            $("#cart_goodslist").html(data);
        }
    });
}

// 商品数量减1
function minusNum(id)
{
    $.ajax({
        type:"post",
        url:"/distributor/goods/numberMinus",
        data:{"id":id},
        success:function(data){
            $("#cart_goodslist").html(data);
        }
    });
}

function changeNumber(quantity,id)
{
	var r = /^\+?[1-9][0-9]*$/;　
	if( r.test(quantity)){
		$.ajax({
	        type:"post",
	        url:"/distributor/goods/changQuantity",
	        data:{"id":id,"quantity":quantity},
	        success:function(data){
	        	if(data.code==1){
	        		searchCart();
	            }else{
	            	layer.alert(data.msg)
	            }
	        }
	    });
	}else{
		layer.alert("请正确输入数量！");
		$("#submit").attr("disabled",true);
	}
}

function delCartItem(id)
{
    if (null == id)
    {
        return;
    }
    
    $.ajax({
        type:"post",
        url:"/distributor/goods/del",
        data:{"id": id},
        success:function(data){
            $("#cart_goodslist").html(data);
        }
    });
}


function goNext(goodsNum)
{
	
    if (0==goodsNum)
    {
    	layer.alert("请至少选择一种商品!");
        return false;
    }
    win_show(); 
}

function searchGoods(page){
	var keywords = $("#keywords").val();
	window.location.href="/distributor/goods/list?keywords="+keywords;
}

// 编辑信息
function editGoods(dis_goodsId,goodsId)
{
    $.ajax({
        type:"post",
        url:"/distributor/goods/detail",
        data:{"dis_goodsId": dis_goodsId,"goodsId":goodsId},
        success:function(data){
        	$("#detail_div").html(data);
        	$("#detail_div").css('display','block');
        }
    });
}

// 加载规格
function searchSpecifica(goodsId,id){
	var url = "/distributor/search/specifica";
	var loadData = {"goodsId":goodsId,"id":id};
	$("#specifica_div").load(url,loadData);
}
// 保存规格
function saveSpecifict(){
	var url = "/distributor/specifica/save";
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
		url : "/distributor/specifica/delete",
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
// 编辑信息保存
function editSaveGoods(){
	var url = "/distributor/goods/editOnSale";
	
	var dis_goodsId = $("#dis_goodsId").val();
	var goodsId = $("#goodsId").val();
    var subGoodsTitle = $("#subGoodsTitle").val();
    var code = $("#code").val();
    var goodsMarketPrice = $("#marketPrice").val();
    var goodsPrice = $("#goodsPrice").val();
    var unit = $("#unit").val();
    var leftNumber = $("#leftNumber").val();
    
    var reg = /(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/;
    var num = /^\+?[1-9][0-9]*$/;
    if(undefined == goodsMarketPrice || ""==goodsMarketPrice || !reg.test(goodsMarketPrice))
    {
    	layer.msg('实体店价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == goodsPrice || ""==goodsPrice || !reg.test(goodsPrice))
    {
    	layer.msg('销售价格式输入错误', {icon: 2 ,time: 1000});
        return ;
    }
    if(undefined == leftNumber || ""==leftNumber || !num.test(leftNumber)){
		layer.msg('请输入正确的库存', {icon: 2 ,time: 1000});
	}
	
    $.ajax({
    	url : url,
    	type : "post",
    	data : {"dis_goodsId":dis_goodsId,
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
// 代理商品
function supply(goodsId){
    $.ajax({
        type : "post",
        url : "/distributor/supply",
        data : {"proGoodsId":goodsId},
        success:function(data){
            if(data.code == 1 ){
            	layer.msg(data.msg, {icon: 1 ,time: 2000});
                window.location.reload();
            }else{
            	layer.msg(data.msg, {icon: 2 ,time: 1000});
            }
        }
    });
}


// 加载关联商品
function searchRelevance(goodsId){
	var url = "/distributor/search/relevance";
	var loadData = {"goodsId":goodsId};
	$("#rele_goodslist").load(url,loadData);
}

//加载出售中的商品
function searchSaleGoods(t){
	var url = "/distributor/search/saleGoods";
	var loadData = null;
	
	if(t){
		var goodsId=$("#goodsId").val();
		loadData = {"goodsId":goodsId};
	}else{
		loadData = $("#good_form").serializeArray();
	}
	$("#good_form").load(url,loadData);
}

// 添加关联
function addRelevance(goodsId1,goodsId2){
	$.ajax({
        type : "post",
        url : "/distributor/relevance/add",
        data : {"goodsId1":goodsId1,"goodsId2":goodsId2},
        success:function(data){
            if(data.code == 1 ){
            	layer.msg(data.msg, {icon: 1 ,time: 2000});
            	searchRelevance(goodsId1);
            }else{
            	layer.msg(data.msg, {icon: 2 ,time: 1000});
            }
        }
    });
}

// 解除关联
function deleteRelevance(goodsId1,goodsId2){
	layer.confirm('确定要解除这两个商品的关联吗？',{
		btn: ['确定','取消'] //按钮
		}, function(){
			$.ajax({
		        url : "/distributor/relevance/delete",
		        data : {"goodsId1":goodsId1,"goodsId2":goodsId2},
		        type :"post",
		        success:function(data){
		        	if(data.code == 1 ){
		            	layer.msg(data.msg, {icon: 1 ,time: 2000});
		            	searchRelevance(goodsId1);
		            }else{
		            	layer.msg(data.msg, {icon: 2 ,time: 1000});
		            }
		        }
		    })
		}, function(){
			layer.closeAll();
		});
}

