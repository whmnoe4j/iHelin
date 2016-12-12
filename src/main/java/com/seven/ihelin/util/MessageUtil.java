package com.seven.ihelin.util;

import com.google.common.collect.Maps;
import com.seven.ihelin.resp.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by iHelin on 16/11/4.
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVNET = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE = "scancode_push";

    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    /**
     * 解析微信发来的请求 xml转为Map
     *
     * @param request
     * @return
     */
    public static Map<String, String> xml2Map(HttpServletRequest request) {
        Map<String, String> msgMap = new HashMap<>();// 将解析结果存储在Map中
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
            logger.warn("error to load xml", e);
            return msgMap;
        }
        return msgMap;
    }

    /**
     * 文本消息组装
     *
     * @param toUserName
     * @param fromUserName
     * @param content
     * @return
     */
    public static String sendTextMsg(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(System.currentTimeMillis());
        text.setContent(content);
        xstream.alias("xml", TextMessage.class);
        return xstream.toXML(text);
    }

    /**
     * 图文消息组装
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String sendArticleMsg(String toUserName, String fromUserName, NewsMessage newsMessage) {
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        xstream.alias("xml", NewsMessage.class);
        xstream.alias("item", Article.class);
        return xstream.toXML(newsMessage);

    }

    /**
     * 图片消息组装
     *
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String sendImageMsg(String toUserName, String fromUserName, ImageMessage imageMessage) {
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        xstream.alias("xml", ImageMessage.class);
        return xstream.toXML(imageMessage);
    }

    /**
     * 音乐消息转XML
     *
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", MusicMessage.class);
        return xstream.toXML(musicMessage);
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

    public static boolean IsNumeric(String str) {
        try {
            Long.parseLong(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
