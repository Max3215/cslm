function categoryGoods(tag,catId){
	$("#slide_check menu a").removeClass("cur");
	$(tag).addClass("cur");
	var url = "/touch/search/goods";
	var loadData = {"catId":catId};
	$("#cateGoods").load(url,loadData);
}

