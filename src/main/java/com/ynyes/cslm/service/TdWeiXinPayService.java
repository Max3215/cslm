package com.ynyes.cslm.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.net.ssl.HttpsURLConnection;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.ynyes.cslm.dao.WxPayReturnData;
import com.ynyes.cslm.dao.WxPaySendData;

@Service
public class TdWeiXinPayService
{
  private final String appid = "wxa76a33ac9941d5a0"; //wxa76a33ac9941d5a0

  private final String mch_id = "1386963202"; //1386963202 

//  private final String notify_url = "http://116.55.233.141:8018/touch/order/pay/result/wx";
  private final String notify_url = "http://www.chinacslm.cc/touch/order/pay/result/wx";

  private final String key = "chaoshilianmeng201609140928byMax";//chaoshilianmeng201609140928byMax

  private final String unifiedOrderUri = "https://api.mch.weixin.qq.com/pay/unifiedorder";

  private final String[] hexDigits = { "0", "1", "2", "3", "4", "5", 
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

  public String getAppid()
  {
    return "wxa76a33ac9941d5a0";
  }

  public String getMch_id() {
    return "1386963202";
  }

  public WxPayReturnData unifiedOrder(String body, String out_trade_no, String spbill_create_ip, int total_fee, String trade_type)
  {
    String nonce_str = getRandomStringByLength(32);

    SortedMap<Object,Object> parameters = new TreeMap<Object, Object>();
    parameters.put("appid", appid);
    parameters.put("mch_id", mch_id);
    parameters.put("notify_url", notify_url);
    parameters.put("nonce_str", nonce_str);
    parameters.put("body", body);
    parameters.put("spbill_create_ip", spbill_create_ip);
    parameters.put("out_trade_no", out_trade_no);
    parameters.put("total_fee", Integer.valueOf(total_fee));
    parameters.put("trade_type", trade_type);

    String sign = createSign(parameters);

    WxPaySendData data = new WxPaySendData();
    data.setAppid(appid);
    data.setMch_id(mch_id);
    data.setNonce_str(nonce_str);
    data.setBody(body);
    data.setSpbill_create_ip(spbill_create_ip);
    data.setOut_trade_no(out_trade_no);
    data.setNotify_url(notify_url);
    data.setTotal_fee(total_fee);
    data.setTrade_type(trade_type);
    data.setSign(sign);

    XStream xs = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
    xs.alias("xml", WxPaySendData.class);

    String xml = xs.toXML(data);

    System.out.print("发送XML: " + xml);

    String returnXml = doPostForString("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);

    System.out.print("返回结果:" + returnXml);

    WxPayReturnData reData = new WxPayReturnData();
    XStream xs1 = new XStream(new DomDriver());
    xs1.alias("xml", WxPayReturnData.class);
    reData = (WxPayReturnData)xs1.fromXML(returnXml);

    return reData;
  }

  public String createSign(SortedMap<Object, Object> parameters)
  {
    StringBuffer sb = new StringBuffer();
    Set es = parameters.entrySet();
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = 
        (Map.Entry)it
        .next();
      String k = (String)entry.getKey();
      Object v = entry.getValue();
      if ((v != null) && (!"".equals(v)) && (!"sign".equals(k)) && 
        (!"key".equals(k))) {
        sb.append(k + "=" + v + "&");
      }
    }
    sb.append("key="+key);
    System.out.print("加密前字符串" + sb.toString());
    String sign = MD5Encode(sb.toString(), "UTF-8").toUpperCase();
    return sign;
  }

  public String getRandomStringByLength(int length)
  {
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  public String doPostForString(String uri, String xml) {
    HttpsURLConnection urlCon = null;
    String resXML = null;
    try {
      urlCon = (HttpsURLConnection)new URL(uri).openConnection();

      urlCon.setDoInput(true);
      urlCon.setDoOutput(true);
      urlCon.setRequestMethod("POST");
      urlCon.setRequestProperty("Content-Length", 
        String.valueOf(xml.getBytes().length));
      urlCon.setUseCaches(false);

      urlCon.getOutputStream().write(xml.getBytes("utf-8"));
      urlCon.getOutputStream().flush();
      urlCon.getOutputStream().close();
      BufferedReader in = new BufferedReader(new InputStreamReader(
        urlCon.getInputStream()));

      String line = null;
      StringBuffer buffer = new StringBuffer();
      while ((line = in.readLine()) != null) {
        buffer.append(line);
      }
      in.close();
      urlCon.disconnect();

      resXML = buffer.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return resXML;
  }

  private String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  private String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256;
    int d1 = n / 16;
    int d2 = n % 16;
    return this.hexDigits[d1] + this.hexDigits[d2];
  }

  public String MD5Encode(String origin, String charsetname) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if ((charsetname == null) || ("".equals(charsetname)))
        resultString = byteArrayToHexString(md.digest(resultString
          .getBytes()));
      else
        resultString = byteArrayToHexString(md.digest(resultString
          .getBytes(charsetname)));
    } catch (Exception localException) {
    }
    return resultString;
  }
}