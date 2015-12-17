function cateChange(){
	var categoryId = $("#oneCat").val();
	console.debug(categoryId);
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
	console.debug(categoryId);
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