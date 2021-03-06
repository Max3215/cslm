package com.cslm.payment.alipay;

public class AlipayConfig {
    // ISO-8859-1编码
    public static final String ISO_8859_1 = "ISO-8859-1";
    
    // 编码方式
    public static final String CHARSET = "gbk";
    
    // 合作身份者ID
    public static final String PARTNER = "2088121425421082";

    // 收款支付宝账号
    public static String SELLER_EMAIL = "13987418399@163.com";
    
    // 收款支付宝ID
    public static String SELLER_ID = PARTNER;

    // 商户的私钥
    public static String KEY = "705nmp52l3kmumpn18jws3mcjuzuox9n";

    // 签名方式
    public static String SIGN_TYPE = "MD5";
    
    // 即时到账
    public static final String CREATE_TRADE_SERVICE = "create_direct_pay_by_user";
    
    // 手机网站支付
    public static final String CREATE_MOBILE_SERVICE = "alipay.wap.create.direct.pay.by.user";
    // 纯担保交易
//    public static final String CREATE_TRADE_SERVICE = "create_partner_trade_by_buyer"
    
    // 商品购买
    public static final String PAYMENT_TYPE = "1";
    
    // 合作伙伴名称
    public static final String SUBJECT = "云南联超商贸有限公司";
    
    // 发货快递
    public static final String DEFAULT_EXPRESS = "EXPRESS";
    
    // 快递费用承担方,卖家承担
    public static final String SELLER_PAY = "SELLER_PAY";
    
    // 快递费用,线下经营免快递费
    public static final String LOGISTICS_FREE = "0";
    
    // 货物数量
    public static final String DEFAULT_QUANTITY = "1";
    
    // 用户确认收货过期自动确认收货时间
    public static final String RECORD_POST = "1d";
    
    // 支付超时时间
    public static final String PAY_POST = "1d";
    
    // 确认收货
    public static final String CONFIRM_GOODS_SERVICE = "send_goods_confirm_by_platform";
    
    // 发货快递
    public static final String DEFAULT_TRANSPORT_TYPE = "EXPRESS";
    
    /*
     * 支付请求地址
     */
    public static final String REAUESTURL = "https://mapi.alipay.com/gateway.do";
    
    /*
     * 支付宝消息验证地址
     */
    public static final String HTTPS_VERIFY_URL = REAUESTURL + "?service=notify_verify&";
}
