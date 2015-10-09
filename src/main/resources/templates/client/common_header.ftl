<header class="main_top">
        <div class="main">
            <h1>您好！欢迎光临王明辉超市！</h1>
            <#if username??>
                <a href="/user">${username}</a>
                <a href="/logout">退出</a>
            <#else>
                <a href="/login">请登陆</a>
                <a href="/reg">注册</a>
            </#if>
            <menu class="top_menu">
                <a href="/user/order/list/0">我的订单<span>丨</span></a>
                <a href="/cart">我的购物车<span>丨</span></a>
                <a href="/user">超市会员<span>丨</span></a>
                <a href="<#if site.qq1??>http://wpa.qq.com/msgrd?v=3&uin=${site.qq1!''}&site=qq&menu=yes<#else>#</#if>">客户服务<span>丨</span></a>
                <a href="/user/collect/list">我的收藏</a>
            </menu>
            <div class="clear"></div>
        </div>
    </header>
    <!--logo 搜索框部分-->
    <section class="main">
        <a href="/" class="logo"><img src="<#if site??>${site.logoUri!''}</#if>" /></a>
        <div class="choose_mar">
            <a href="javascript:void(0);" class="click_a" onclick="$('#mar_box').fadeIn(300);"><#if distributorTitle??>${distributorTitle!''}<#else>请选择地区超市</#if></a>
        </div>
        <div class="m_box">
            <div class="search_box">
                <form action="/search" method="get" id="search_form" >
                     <input class="text" type="text" id="keywords" name="keywords" value="<#if keywords_list?? && keywords_list[0]??>${keywords_list[0].title!''}</#if>">
                     <a href="javascript:submitSearch()">搜索</a>
                </form>
            </div>
            <menu class="hot_search">
                <#if keywords_list??>
                    <#list keywords_list as item>
                        <#if item_index gt 0>
                            <a href="/search?keywords=${item.title}"  >${item.title}<span>丨</span></a>
                        </#if>
                    </#list>
                </#if>
            </menu>
        </div>
        <div class="gu_car">
            <a href="/cart">去购物车结算<span><#if cart_goods_list??>${cart_goods_list?size}<#else>0</#if></span></a>
        </div>
        <div class="clear"></div>
    </section>

    <!--选择超市弹出框-->
    <aside class="winbox" id="mar_box">
        <div class="mar_box">
            <p class="tit">请选择超市<a href="javascript:void(0);" onclick="$(this).parent().parent().parent().fadeOut(300);"></a></p>
            <div class="select" id="add">
                   <select id="prov" class="prov" style="width: 120px;"></select>
                   <select id="city" class="city" style="width: 120px;"></select>
                   <select id="dist" class="dist" style="width: 120px;"></select>
                   <select id="diys" class="diys" style="width: 120px;" name="shopId" datatype="n" nullmsg="请选择超市" errormsg="请选择超市"></select>
                <input class="sub" type="submit" value="确定" onclick="changeDistributor()">
            </div>
            <div class="mar_list">
                <table>
                <#if city_list??>
                    <#list city_list as city> 
                    <tr>
                        <th width="100">${city!''}：</th>
                        <#if ("disc_"+city_index+"_list")?eval??>
                              <#list ("disc_"+city_index+"_list")?eval as disc>
                                    <td>
                                        <p>${disc!''}</p>
                                        <#if ("distributor_"+city_index+disc_index+"_list")?eval??>
                                             <#list ("distributor_"+city_index+disc_index+"_list")?eval as dis>
                                                 <a href="javascript:;" onclick="chooseDistributor(${dis.id?c})">${dis.title!''}</a>
                                             </#list>
                                         </#if>
                                    </td>
                              </#list>
                         </#if>
                    </tr>
                    </#list>
                 </#if>
                </table>
            </div>

        </div>
    </aside>
    
    <!--导航部分-->
    <nav class="nav_box">
        <div class="main">
            <section class="nav_list" id="mainnavdown">
                <a  class="a2">全部商品分类</a>
                <ul id="nav_down" class="nav_down dis_none">
                    <#if top_cat_list??>
                        <#list top_cat_list as item>
                           <li>
                              <a href="/list/${item.id?c}" class="list">${item.title!''}</a><span>></span>
                              <div class="nav_show">
                                  <#if ("second_level_"+item_index+"_cat_list")?eval?? >
                                       <table>
                                           <#list ("second_level_"+item_index+"_cat_list")?eval as secondLevelItem>
                                            <tr>
                                                 <th width="60"><a href="/list/${secondLevelItem.id?c}">${secondLevelItem.title!''}</a></th>
                                                 <td>
                                                      <#if ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval?? >
                                                            <#list ("third_level_"+item_index+secondLevelItem_index+"_cat_list")?eval as thirdLevelItem>
                                                                <a href="/list/${thirdLevelItem.id?c}">${thirdLevelItem.title!''}</a>
                                                            </#list>
                                                      </#if>
                                    
                                                   </td>
                                            </tr>
                                            </#list>
                                       </table>
                                   </#if>
                                </div>
                             </li>
                          </#list>
                     </#if>
                </ul>
            </section>
            <#if navi_item_list??>
                <#list navi_item_list as item>
                    <a class="a1" href="${item.linkUri!''}">${item.title!''}</a>
                </#list>
             </#if> 
        </div>
    </nav>