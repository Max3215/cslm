<#assign defaul = false>
<#if user.shippingAddressList?? && user.shippingAddressList?size gt 0>
<#list user.shippingAddressList as address>
      <#if address.isDefaultAddress?? && address.isDefaultAddress>
        <input type="hidden" value="${address.id?c}" name="addressId"  id="addressId" datatype="n"  nullmsg="请选择地址!">
      <#assign defaul = true>
      </#if>
    <section class="address_list">
        <div class="tit">
          <p>收货人信息</p>
          <#if address.isDefaultAddress?? && address.isDefaultAddress>
          <a href="javascript:addrDefault(${address.id?c});" class="on">默认</a>
          <#else>
          <a href="javascript:addrDefault(${address.id?c});" >设为默认</a>
          </#if>
          <a href="/touch/user/address/update?id=${address.id?c}&type=order"></a>
          <a href="/touch/user/address/delete?id=${address.id?c}&type=order"></a>
        </div>
        <p class="name">${address.receiverName!''}&nbsp;&nbsp;&nbsp;&nbsp;${address.receiverMobile!''}</p>
        <p class="add">${address.province!''}${address.city!''}${address.disctrict!''}${address.detailAddress!''}</p>
    </section>
</#list>
</#if>
<#if defaul == false>
    <input type="hidden" value="" name="addressId"   id="addressId" datatype="n"  nullmsg="请选择地址!">
</#if>
<p class="add_btn_box"><a href="/touch/user/address/update?type=order" class="add_btn">添加收货地址</a></p>

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