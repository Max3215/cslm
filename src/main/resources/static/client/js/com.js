$(function () { 
    //全选或全不选 
    $("#checkAll").click(function(){    
        if(this.checked){    
            $(".list :checkbox").prop("checked", true);   
        }else{    
            $(".list :checkbox").prop("checked", false); 
        }    
     });  
    //全选   
    $("#selectAll").click(function () { 
         $("#list :checkbox,#all").prop("checked", true);   
    });   
    //全不选 
    $("#unSelect").click(function () {   
         $("#list :checkbox,#all").prop("checked", false);   
    });   
    //反选  
    $("#reverse").click(function () {  
         $(".list :checkbox").each(function () {   
              $(this).prop("checked", !$(this).prop("checked"));   
         }); 
         allchk(); 
    }); 
     
    //设置全选复选框 
    $("#list :checkbox").click(function(){ 
        allchk(); 
    }); 
  
    //获取选中选项的值 
    $("#getValue").click(function(){ 
        var valArr = new Array; 
        $("#list :checkbox[checked]").each(function(i){ 
            valArr[i] = $(this).val(); 
        }); 
        var vals = valArr.join(','); 
          alert(vals); 
    }); 
});  

function checkAll(tag){
	if(tag.checked){    
        $("#list :checkbox").prop("checked", true);   
    }else{    
        $("#list :checkbox").prop("checked", false); 
    }
}
function reverse(){
	 $("#list :checkbox").each(function () {   
         $(this).prop("checked", !$(this).prop("checked"));   
    }); 
    allchk(); 
}
function allchk(){ 
    var chknum = $(".list :checkbox").size();//选项总个数 
    var chk = 0; 
    $(".list :checkbox").each(function () {   
        if($(this).prop("checked")==true){ 
            chk++; 
        } 
    }); 
    if(chknum==chk){//全选 
        $("#checkAll").prop("checked",true); 
    }else{//不全选 
        $("#checkAll").prop("checked",false); 
    } 
} 