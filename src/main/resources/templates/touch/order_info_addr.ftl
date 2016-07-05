<section class="address_list" id="address_list">
    <#assign defaul = false>
    <#if user.shippingAddressList?? && user.shippingAddressList?size gt 0>
        <#list user.shippingAddressList as address>
            <div class="part <#if address.isDefaultAddress?? && address.isDefaultAddress>act</#if>">
            <#if address.isDefaultAddress?? && address.isDefaultAddress>
            <#assign defaul = true>   
            <input type="hidden" value="${address.id?c}" name="addressId"  datatype="n" nullmsg="请选择地址!">
             <a style="color:#fff" href="javascript:addrDefault(${address.id?c});">
             <p>姓名：${address.receiverName!''}<span>邮编：${address.postcode!''}</span></p>
              <p>手机号码：${address.receiverMobile!''}</p>
              <p>省市：${address.province!''}${address.city!''}${address.disctrict!''}</p>
              <p>详细地址：${address.detailAddress!''}</p>
              </a>
              <#else>
              <a href="javascript:addrDefault(${address.id?c});">
                 <p>姓名：${address.receiverName!''}<span>邮编：${address.postcode!''}</span></p>
                  <p>手机号码：${address.receiverMobile!''}</p>
                  <p>省市：${address.province!''}${address.city!''}${address.disctrict!''}</p>
                  <p>详细地址：${address.detailAddress!''}</p>
                  </a>
              </#if>
            <menu>
                <a href="/touch/user/address/update?id=${address.id?c}&type=order">修改</a>
                <a href="/touch/user/address/delete?id=${address.id?c}">删除</a>
              <menu>
              </div>
        </#list>
        <#if defaul == false>
        <input type="hidden" value="" name="addressId"  datatype="n" nullmsg="请选择地址!">
        </#if>
   <#else>
   <input type="hidden" value="" name="addressId"  datatype="n" nullmsg="请先添加地址!">
   </#if> 
   <a href="/touch/user/address/update?type=order" class="add_btn">添加收货地址</a>
</section>

<script type="text/javascript">
function addrDefault(id){
    $.ajax({
        type : "post",
        url : "/touch/order/address",
        data :{"id":id},
        success:function(res){
            $("#address_list").html(res);
        }
    })
}
</script>