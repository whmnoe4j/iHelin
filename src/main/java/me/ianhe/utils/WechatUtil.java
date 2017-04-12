package me.ianhe.utils;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import me.ianhe.model.Button;
import me.ianhe.model.ClickButton;
import me.ianhe.model.Menu;
import me.ianhe.model.ViewButton;
import me.ianhe.model.WXAccessToken;
import me.ianhe.model.req.LocationMessage;
import me.ianhe.model.resp.Article;
import me.ianhe.model.resp.ImageMessage;
import me.ianhe.model.resp.MusicMessage;
import me.ianhe.model.resp.NewsMessage;
import me.ianhe.model.resp.TextMessage;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
 * 微信常用工具类
 * <p>
 * Created by iHelin on 16/11/4.
 */
public class WechatUtil {

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

    private static final String MEDIA_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
    private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatUtil.class);

    // get请求，返回String
    public static String doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            return EntityUtils.toString(entity, CharEncoding.UTF_8);
        } catch (Exception e) {
            LOGGER.warn("error while doGet url:" + url, e);
            return null;
        } finally {
            httpGet.releaseConnection();
        }
    }

    // post请求
    public static String doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpEntity reqEntity = new StringEntity(outStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(reqEntity);
            HttpResponse response = client.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            return EntityUtils.toString(respEntity, CharEncoding.UTF_8);
        } catch (Exception e) {
            LOGGER.warn("error while doPost url:" + url, e);
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
            LOGGER.warn("error to load xml", e);
            return msgMap;
        }
        return msgMap;
    }

    // 获取微信access_token
    public static WXAccessToken getAccessToken(String appId, String secret) {
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(secret)) {
            throw new RuntimeException("appId or secret is empty,please check your configuration!");
        }
        String url = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", secret);
        String res = doGetStr(url);
        WXAccessToken token = JSON.parseObject(res, WXAccessToken.class);
        LOGGER.info("从微信服务器获取token成功，有效期为" + token.getExpires_in() + "秒");
        return token;
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
        text.setMsgType(MESSAGE_TEXT);
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

    // 组装菜单
    public static Menu initMenu() {
        Menu menu = new Menu();
        List<Button> buttons = Lists.newArrayList();
        ClickButton button11 = new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");
        buttons.add(button11);

        ViewButton button2 = new ViewButton();
        button2.setName("官方网站");
        button2.setType("view");
        button2.setUrl("http://www.tcqcw.cn");
        buttons.add(button2);

        List<Button> subButtons = Lists.newArrayList();
        ClickButton button31 = new ClickButton();
        button31.setName("扫码");
        button31.setType("scancode_push");
        button31.setKey("31");
        subButtons.add(button31);

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");
        subButtons.add(button32);

        Button button = new Button();
        button.setName("新菜单");
        button.setSub_button(subButtons);
        buttons.add(button);

        menu.setButton(buttons);
        return menu;
    }

    // 创建菜单
    public static String createMenu(String accessToken, String menu) {
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
        return doPostStr(url, menu);
    }

    // 查询菜单
    public static String queryMenu(String token) {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        return doGetStr(url);
    }

    // 删除菜单
    public static String deleteMenu(String token) {
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        return doGetStr(url);
    }

    // 组装地理位置消息
    public static LocationMessage MapToLocation(Map<String, String> map) {
        LocationMessage location = new LocationMessage();
        location.setLabel(map.get("Label"));
        location.setLocation_X(map.get("Location_X"));
        location.setLocation_Y(map.get("Location_Y"));
        location.setScale(Integer.parseInt(map.get("Scale")));
        location.setMsgId(Long.valueOf(map.get("MsgId")));
        return location;
    }

    private WechatUtil() {

    }

}
