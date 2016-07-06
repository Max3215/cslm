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
//function addgoods(gid){
//    var page = $("#page").val();
//    var quantity = $("#number"+gid).html();
//    
//    if(undefined == quantity || quantity == 0){
//    	alert("该商品已无存货！")
//    	return;
//    }
//    
//    $.ajax({
//        url : "/distributor/goods/addOne",
//        data : {"pgId":gid},
//        type :"post",
//        success:function(res){
//            $("#cart_goodslist").html(res);
//            alert("添加成功")
//        }
//    })
//}
//超市中心选择批发商品
function addGoods(){
	var goodsId = $("#goodsId").val();
    var page = $("#page").val();
    var leftNumber = parseInt($("#number"+goodsId).html());
    var quantity = parseInt($("#quantity").val());
    
    if(undefined == leftNumber || leftNumber == 0 || leftNumber < quantity){
    	alert("该商品存货不足！")
    	return;
    }
    
    $.ajax({
        url : "/distributor/goods/addOne",
        data : {"pgId":goodsId,"quantity":quantity},
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

//function goNext(goodsNum)
//{
//	
//    if (0==goodsNum)
//    {
//        alert("请至少选择一种商品!");
//        return false;
//    }
//    window.location.href="/distributor/order/info";
//}

function goNext(goodsNum)
{
	
    if (0==goodsNum)
    {
        alert("请至少选择一种商品!");
        return false;
    }
    win_show(); 
}
//$.ajax({
//	type :"post",
//	url : "/distributor/order/info",
//	success:function(data){
//		alert(data.msg);
//		if(data.code==1){
//			window.location.href="/distributor/inOrder/list/0";
//		}
//	}
//})

function searchGoods(page){
	var keywords = $("#keywords").val();
	window.location.href="/distributor/goods/list?keywords="+keywords;
}