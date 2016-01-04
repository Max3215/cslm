function cateChange(){
	var categoryId = $("#oneCat").val();
	$.ajax({
		type : "post",
		url : "/Verwalter/brand/category",
		data : {"categoryId":categoryId,"type":"two"},
		success : function(data){
			$("#threeCatDiv").css({"display": "none"});
			$("#twoCatDiv").html(data);
		}
	})
	 var url = "/Verwalter/brand/check?catId=" + $(this).val() + "<#if brand??>&id=${brand.id?c}</#if>";
    $("#idBrandTitle").attr("ajaxurl", url);
}

function twoChange(){
	var categoryId = $("#twoCat").val();
	$.ajax({
		type : "post",
		url : "/Verwalter/brand/category",
		data : {"categoryId":categoryId,"type":"three"},
		success : function(data){
			$("#threeCatDiv").css({"display":"block"});
			$("#threeCatDiv").html(data);
		}
	})
	 var url = "/Verwalter/brand/check?catId=" + $(this).val() + "<#if brand??>&id=${brand.id?c}</#if>";
    $("#idBrandTitle").attr("ajaxurl", url);
}
