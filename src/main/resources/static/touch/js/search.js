/**
 * app无刷新加载数据，需要服务端配合输出，以及要添加到页面的html结构代码
 * @param {type} $ jquery简写
 * @param {url} 需要get发送到的url地址
 * @param {node} node是需要在页面那个元素内部插入返回html结构内容
 * @param {page} page是页码 
 * @returns 无返回
 * example:
 * <div id="list">
 *      <a href="#">测试数据</a>
 * </div>
 * 服务端只需要组装以 <a href="#">测试数据</a>为基础的数据。：echo $str = '<a href="#">测试数据1</a><a href="#">测试数据2</a>'
 * $("#list").refresh('http://xxx/list.php',"#list",1);
 */
(function($){
    $.fn.extend({
        "refresh":function(url,node,page){
            var div = $('<div id="loading"></div>');
            div.css({position:"relative",top:"54px",height:"30px",lineHeight:"30px"});
            $(node).after(div)
            $(document).scroll(function(){
                var viewHeight = window.innerHeight;
                var pageHeight = $(document).height();
                var srollHeight = pageHeight-viewHeight;
                if($(document).scrollTop()-srollHeight == 0){
                    div.html('<span style="color:#987474;line-height:32px;text-align: center;display:block;">正在加载中...</span>');
                    $.post(url,{page:page+1},function(data){
                        if(data != 0){
                            $(node).append(data);
                            div.html("");
                            page++;
                        }else{
                           div.html('<span style="color:#987474;line-height:32px;text-align: center;display:block;">已经到了最后一页</span>'); 
                        }
                    });
                }
            });
        }
    });
})(jQuery)