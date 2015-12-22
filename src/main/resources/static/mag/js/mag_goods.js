function cateChange(){
	var categoryId = $("#oneCat").val();
	$.ajax({
		type : "post",
		url : "/Verwalter/goods/category",
		data : {"categoryId":categoryId,"type":"two"},
		success : function(data){
			$("#threeCatDiv").css({"display": "none"});
			$("#twoCatDiv").html(data);
		}
	})
	
}

function twoChange(){
	var categoryId = $("#twoCat").val();
	$.ajax({
		type : "post",
		url : "/Verwalter/goods/category",
		data : {"categoryId":categoryId,"type":"three"},
		success : function(data){
			$("#threeCatDiv").css({"display":"block"});
			$("#threeCatDiv").html(data);
		}
	})
}

function parameter(){
	var categoryId = $("#categoryId").val();
    $.ajax({
        url : '/Verwalter/goods/edit/parameter/'+categoryId,
        type : 'POST',
        success : function(res) {
            $("#id-param-sec").html(res);
        }
    });
}