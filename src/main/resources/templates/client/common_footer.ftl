<section class="main">
        <div class="slogen">
            <#if service_item_list??>
                <#list service_item_list as item>
                    <#if item_index lt 4 >
                        <span><i><img src="${item.logo!''}"></i>${item.title!''}</span>
                    </#if>
                </#list>
            </#if>
        </div>
    </section>

    <footer class="foot">
        <div class="main">
            <dl>
                <dt>新手指南</dt>
                <dd><a href="#">用户注册</a></dd>
                <dd><a href="#">购物流程</a></dd>
                <dd><a href="#">新手帮助</a></dd>
                <dd><a href="#">隐私声明</a></dd>
            </dl>
            <dl>
                <dt>支付方式</dt>
                <dd><a href="#">在线支付</a></dd>
                <dd><a href="#">支付宝支付</a></dd>
                <dd><a href="#">网银支付</a></dd>
                <dd><a href="#">公司转账</a></dd>
            </dl>
            <dl>
                <dt>配送方式</dt>
                <dd><a href="#">送货上门</a></dd>
                <dd><a href="#">配货点自提</a></dd>
                <dd><a href="#">配送标准</a></dd>
                <dd><a href="#">配送查询</a></dd>
            </dl>
            <dl>
                <dt>售后服务</dt>
                <dd><a href="#">退货流程</a></dd>
                <dd><a href="#">售后政策</a></dd>
            </dl>
            <dl>
                <dt>帮助中心</dt>
                <dd><a href="#">常见问题</a></dd>
                <dd><a href="#">关于我们</a></dd>
                <dd><a href="#">联系我们</a></dd>
                <dd><a href="#">交易条款</a></dd>
                <dd><a href="#">商家入住</a></dd>
            </dl>
            <div class="b_contact">
                <a href="#" class="qq"><img src="/client/images/qq01.png"></a>
                <span><i><img src="/client/images/phone01.png"></i>13888888888</span>
            </div>
            <div class="clear"></div>

            <menu class="bottom_nav">
                <a href="#">首页<span>丨</span></a>
                <a href="#">本地特产<span>丨</span></a>
                <a href="#">政企采购<span>丨</span></a>
                <a href="#">生活服务<span>丨</span></a>
                <a href="#">商家入驻</a>
            </menu>
            <p class="copyright">copyright©2015&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;公司版权所有&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;技术支持：<a href="http://www.ynyes.com">昆明天度网络信息技术有限公司</a></p>
        </div>
    </footer>