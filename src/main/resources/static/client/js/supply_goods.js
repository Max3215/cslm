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
function goodsAudit(type,pgId,page){
    $.ajax({
        url : "/supply/goods/onsale/"+pgId,
        data : {"type":type,"page":page},
        type : "post",
       success:function(res){
            $("#dis_goods_table").html(res);
        }
    })
}

//超市中心删除商品
function deleteGoods(type,pgId,page){
    $.ajax({
        url : "/supply/goods/delete/"+pgId,
        data : {"type":type,"page":page},
        type :"post",
        success:function(res){
            $("#dis_goods_table").html(res);
        }
    })
}