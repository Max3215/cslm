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
                    <td width="110"><a><img src="${cg.goodsCoverImageUri!''}" width="100" /></a></td>
                    <td width="250" style="text-align:left;"><a >${cg.goodsTitle!''}</a></td>
                          <td class="red" width="150">￥${cg.price?string("0.00")}</td>
                          <td width="150" class="num">
                            <a href="javascript:minusNum(${cg.goodsId});"> - </a>
                            <input class="text" type="text" value="${cg.quantity!''}" />
                            <a href="javascript:addNum(${cg.goodsId});"> + </a>
                          </td>
                          <td class="red" width="150">￥${(cg.price*cg.quantity)?string("0.00")}</td>
                          <td><a class="del" href="javascript:delCartItem(${cg.id?c});">删除</a></td>
                            <#if cg.isSelected>
                                <#assign totalGoods=totalGoods+cg.quantity>
                                <#assign totalPrice=totalPrice+cg.price*cg.quantity>
                            </#if>
                    </tr>  
                   </#list>
                  
                  <#--
                  <tr>
                    <td width="20"><input type="checkbox"></td>
                    <td colspan="2" style="text-align:left;"><span class="mr20">全选</span><a class="c9" href="#">删除选中的商品</a></td>
                    <td colspan="4" style="text-align:right; font-size:16px; line-height:35px;">
                      商品<span class="fc fw-b">3</span>件&nbsp;&nbsp;
                      总价：<span class="fc fw-b">￥15999.00</span>&nbsp;&nbsp;
                      商品总计（不含运费）：<span class="fc fw-b">￥15999.00</span>
                    </td>
                  </tr>
                  -->
                   <tr>
                          <td width="20"><input type="checkbox" <#if allChecked>checked="checked" onclick="javascript:toggleAllSelect(1);"<#else>onclick="javascript:toggleAllSelect(0);"</#if>/></td>
                          <td colspan="2" style="text-align:left;"><span class="mr20">全选</span>
                          <#--<a class="c9" href="#">删除选中的商品</a>-->
                          </td>
                          <td colspan="4" style="text-align:right; font-size:16px; line-height:35px;">
                                商品<span class="fc fw-b"><#if cart_goods_list??>${totalGoods!'0'}</#if></span>件&nbsp;&nbsp;
                                总价：<span class="fc fw-b">￥<#if cart_goods_list??>${totalPrice?string("0.00")}</#if></span>&nbsp;&nbsp;
                          </td>
                    </tr>
            </tbody>
          </table>
        </div>
        <div class="clear"></div>
        <div class="car_btn">
            <input class="sub" type="submit" value="提交订货单">
        </div>
</#if>