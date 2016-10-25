//function cateChange(){
//	var categoryId = $("#oneCat").val();
//	$.ajax({
//		type : "post",
//		url : "/Verwalter/parameter/category",
//		data : {"categoryId":categoryId,"type":"two"},
//		success : function(data){
//			$("#threeCatDiv").css({"display": "none"});
//			$("#twoCatDiv").html(data);
//			// 选择类型后修改ajaxurl
//	        var url = "/Verwalter/parameter/check?categoryId=" + categoryId + "<#if parameter??>&id=${parameter.id?c}</#if>";
//	        $("#idParamTitle").attr("ajaxurl", url);
//		}
//	})
//}

