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
	
	parameter(categoryId);
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
	parameter(categoryId);
}

function findParam(){
	var categoryId = $("#categoryId").val(); 
	console.debug(categoryId);
	parameter(categoryId);
}

function parameter(categoryId){
	
    $.ajax({
        url : '/Verwalter/goods/edit/parameter/'+categoryId,
        type : 'POST',
        success : function(res) {
            $("#id-param-sec").html(res);
        }
    });
}