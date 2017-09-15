package me.ianhe.utils;

import com.google.common.collect.Maps;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 微信常用工具类
 * Created by iHelin on 16/11/4.
 */
public class WechatUtil {

    private static final HttpClientBuilder HTTP_CLIENT_BUILDER = HttpClientBuilder.create();

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatUtil.class);

    /**
     * get请求，返回String
     *
     * @author iHelin
     * @since 2017-05-10 16:54
     */
    public static String doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, CharEncoding.UTF_8);
        } catch (Exception e) {
            LOGGER.warn("error while doGet url:{}", url, e);
            return null;
        } finally {
            httpGet.releaseConnection();
        }
    }

    /**
     * post请求
     *
     * @author iHelin
     * @since 2017-05-10 16:54
     */
    public static String doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = HTTP_CLIENT_BUILDER.build();
        HttpEntity reqEntity = new StringEntity(outStr, ContentType.APPLICATION_JSON);
        httpPost.setEntity(reqEntity);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            return EntityUtils.toString(respEntity, CharEncoding.UTF_8);
        } catch (Exception e) {
            LOGGER.warn("error while doPost url:{}", url, e);
            return null;
        } finally {
            httpPost.releaseConnection();
        }
    }

    /**
     * 解析微信发来的请求 xml转为Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> xml2Map(HttpServletRequest request) {
        Map<String, String> msgMap = Maps.newHashMap();// 将解析结果存储在Map中
        // 从request中取得输入流
        try (InputStream inStream = request.getInputStream()) {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(inStream);
            Element root = doc.getRootElement();// 得到xml根元素
            List<Element> elementList = root.elements();// 得到根元素的所有子节点
            // 遍历所有子节点
            for (Element e : elementList) {
                msgMap.put(e.getName(), e.getText());
            }
        } catch (Exception e) {
            LOGGER.warn("error to load xml", e);
            return msgMap;
        }
        return msgMap;
    }

    public static String map2XML(Map<String, Object> map) {
        String xml = "<xml>";
        Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            String key = entry.getKey();
            String val = entry.getValue().toString();
            if (IsNumeric(val)) {
                xml += "<" + key + ">" + val + "</" + key + ">";
            } else
                xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
        }
        xml += "</xml>";
        return xml;
    }

    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (IsNumeric(text)) {
                        writer.write(text);
                    } else {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    }
                }
            };
        }
    });

    /**
     * 判断是否为number
     *
     * @param str
     * @return
     */
    public static boolean IsNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private WechatUtil() {
        //工具类不允许实例化
    }

}
