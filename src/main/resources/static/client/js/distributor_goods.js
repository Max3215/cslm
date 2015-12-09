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
function goodsOnSale(type,disId,page){
    $.ajax({
        url : "/distributor/goods/onsale/"+disId,
        data : {"type":type,"page":page},
        type : "post",
       success:function(res){
            $("#dis_goods_table").html(res);
        }
    })
}

//超市中心删除商品
function deleteDisGoods(type,disId,page){
    $.ajax({
        url : "/distributor/goods/delete/"+disId,
        data : {"type":type,"page":page},
        type :"post",
        success:function(res){
            $("#dis_goods_table").html(res);
        }
    })
}

//超市中心选择批发商品
function addGoods(){
    var page = $("#page").val();
    var quantity = parseInt($("#quantity").val());
    var gid = $("#goodsId").val();
    var leftNumber = parseInt($("#leftNumber").val());
    
    if(undefined == quantity || quantity == 0){
    	alert("请输入进货数量！")
    	return;
    }
    console.debug(quantity>leftNumber);
    if(quantity > leftNumber){
    	alert("库存不足！")
    	return ;
    }
    
    $.ajax({
        url : "/distributor/goods/addOne",
        data : {"pgId":gid,"quantity":quantity},
        type :"post",
        success:function(res){
            $("#cart_goodslist").html(res);
            alert("添加成功")
            $('.sub_form').css('display','none');
        }
    })
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
        	console.debug(id)
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

function changeNumber(id)
{
	var quantity = $("#number"+id).val();
	console.debug(quantity);
	var r = /^\+?[1-9][0-9]*$/;　
	if( r.test(quantity)){
		$.ajax({
	        type:"post",
	        url:"/distributor/goods/changQuantity",
	        data:{"id":id,"quantity":quantity},
	        success:function(data){
	            $("#cart_goodslist").html(data);
	        }
	    });
	}else{
		alert("请正确输入数量！");
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
        alert("请至少选择一种商品!");
        return false;
    }
    $.ajax({
    	type : "post",
    	url : "/distributor/order/info",
    	success:function(data){
    		if(data.code==0)
    		{
    			alert(data.msg);
    		}
    		if(data.code==1)
    		{
    			window.location.href="/distributor/inOrder/list/0";
    		}
    	}
    })
}

function searchGoods(page){
	var keywords = $("#keywords").val();
	window.location.href="/distributor/goods/list?keywords="+keywords+"&page="+page;
}