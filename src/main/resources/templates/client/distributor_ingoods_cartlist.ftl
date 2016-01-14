<#if cart_goods_list??>
<h3>进货列表</h3>
<div style="max-height:300px;overflow:auto;">
          <table class="car_list">
            <tbody>
                <tr>
                    <th colspan="3">商品信息</th>
                    <th>进货单价</th>
                    <th>数量</th>
                    <th>合计</th>
                    <th>操作</th>
                 </tr>
                    
                    <#assign allChecked=true >
                    <#assign totalGoods=0>
                    <#assign totalPrice=0>
                  <#list cart_goods_list as cg>
                    <tr>
                    <td width="20"><input type="checkbox" onclick="javascript:toggleSelect(${cg.id?c});" <#if cg.isSelected?? && cg.isSelected>checked="checked"<#else><#assign allChecked=false></#if>/></td>
                    <td width="110"><a><img src="${cg.goodsCoverImageUri!''}" width="100" height="100"/></a></td>
                    <td width="230" style="text-align:left;"><a >${cg.goodsTitle!''}</a></td>
                          <td class="red" width="150">￥${cg.price?string("0.00")}</td>
                          <td width="170" class="num">
                            <a href="javascript:minusNum(${cg.goodsId?c});"> - </a>
                            <input class="text" id="number${cg.goodsId?c}" type="text" value="${cg.quantity!''}" onblur="changeNumber(${cg.goodsId?c})"/>
                            <a href="javascript:addNum(${cg.goodsId?c});"> + </a>
                          </td>
                          <td class="red" width="150">￥${(cg.price*cg.quantity)?string("0.00")}</td>
                          <td><a class="del" href="javascript:delCartItem(${cg.id?c});">删除</a></td>
                            <#if cg.isSelected>
                                <#assign totalGoods=totalGoods+cg.quantity>
                                <#assign totalPrice=totalPrice+cg.price*cg.quantity>
                            </#if>
                    </tr>  
                   </#list>
            </tbody>
          </table>
        </div>
        <div class="clear"></div>
        <div class="car_btn">
            <ul>
                <li width="20"><input type="checkbox" <#if allChecked>checked="checked" onclick="javascript:toggleAllSelect(1);"<#else>onclick="javascript:toggleAllSelect(0);"</#if>/></li>
                <li colspan="2"><span class="mr20">全选</span></li>
                <li colspan="4" class="li3" style="font-size:16px; line-height:35px;">
                   商品<span class="fc fw-b"><#if cart_goods_list??>${totalGoods!'0'}</#if></span>件&nbsp;&nbsp;
                   总价：<span class="fc fw-b">￥<#if cart_goods_list??>${totalPrice?string("0.00")}</#if></span>&nbsp;&nbsp;
                </li>
            </ul>
            <input class="sub" type="submit" id="submit" onclick="goNext(${totalGoods!'0'})" value="提交并付款订货单">
        </div>
</#if>