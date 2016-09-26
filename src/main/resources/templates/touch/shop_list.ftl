<#if !isIOS?? ||　isIOS !=true>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=5ZBClgucj8qbtCxOFFd003zZ"></script>

<script type="text/javascript">
var lng =0;
var lat =0;
  // 百度地图API功能
  var map = new BMap.Map("allmap");
  
  var geolocation = new BMap.Geolocation();
  geolocation.getCurrentPosition(function(r){
    if(this.getStatus() == BMAP_STATUS_SUCCESS){
      lng = r.point.lng;
      lat = r.point.lat;
      
  <#if lng?? && lat??>
        <#if !index??>
        distance(${lng},${lat});
        </#if>
  <#else>
      distance(lng,lat);
   </#if>
     
    }
    else {
      alert('failed'+this.getStatus());
    }        
  },{enableHighAccuracy: true})
    

function distance(lng,lat){
    
    $.ajax({
        type: "post",
        data : {"lng":lng,"lat":lat},
        url : "/touch/distance",
        success:function(data){
            $("#shopList").html(data)
        }
    })
}


function chooseDistributor(disId){
    $.ajax({
        url : "/touch/distributor/change",
        async : true,
        type : 'GET',
        dataType : "json",
        data : {"disId":disId},
        success : function(data){
            if(data.msg){
                alter(data.msg)
            }
            var url = window.location.href;          
            if(undefined==url || ""==url){
                window.location.href="/";
             }else{
                 window.location.href = url; 
             }
        }
    })
}


</script>
</#if>
<div class="show_list">
    <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
    <div style="height:100%;overflow:auto;">
        <dl>
            <dt>附近商家:</dt>
        </dl>
        <dl>
           <#if shop_list?? && shop_list?size gt 0>
           <#list shop_list as shop>
                <dd><a href="javascript:;" onclick="chooseDistributor(${shop.id?c});">${shop.title!''}</a></dd>
           </#list>
           </#if>
           <#--
           <#if more_list??>
           <#list more_list as shop>
                <dd><a href="javascript:;" onclick="chooseDistributor(${shop.id?c});">${shop.title!''}</a></dd>
           </#list>
           </#if>
           -->
    </div>
</div>
<div id="allmap" style="display:none"></div>
<!--
<div class="show_list">
    <a href="javascript:void(0)" class="close" onclick="$(this).parent().parent().fadeOut(300);"></a>
    <div style="height:100%;overflow:auto;">
      <#if city_list??>
      <#list city_list as city>
                <#if ("disc_"+city_index+"_list")?eval??>
                    <#list ("disc_"+city_index+"_list")?eval as disc>
                    <dl>
                    <dt>${city!''}-${disc!''}</dt>
                        <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                             <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                <dd><a href="javascript:;" onclick="chooseDistributor(${dis.id?c});">${dis.title!''}</a></dd>
                            </#list>
                        </#if>
                    </dl>
                </#list>
             </#if>
       </#list>
       </#if>
    </div>
</div>
-->