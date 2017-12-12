package me.ianhe.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.vdurmont.emoji.EmojiParser;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 *
 * @author iHelin
 * @since 2017/12/11 14:30
 */
public class CommonTools {

    /**
     * 保存文件
     *
     * @author iHelin
     * @since 2017/12/11 15:08
     */
    public static void saveFile(File file, HttpEntity entity) {
        try {
            translateToOutputStream(new FileOutputStream(file), entity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存文件
     *
     * @author iHelin
     * @since 2017/12/11 15:08
     */
    public static void translateToOutputStream(OutputStream outputStream, HttpEntity entity) {
        try {
            byte[] bytes = EntityUtils.toByteArray(entity);
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 正则表达式处理工具
     *
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月9日 上午12:27:10
     */
    public static Matcher getMatcher(String regEx, String text) {
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(text);
    }

    /**
     * xml解析器
     *
     * @param text
     * @return
     * @author https://github.com/yaphone
     * @date 2017年4月9日 下午6:24:25
     */
    public static Document xmlParser(String text) {
        Document doc = null;
        StringReader sr = new StringReader(text);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 处理emoji表情
     *
     * @param msg
     * @param key
     */
    public static void emojiFormatter(JSONObject msg, String key) {
        Matcher matcher = getMatcher("<span class=\"emoji emoji(.{1,10})\"></span>", msg.getString(key));
        StringBuilder sb = new StringBuilder();
        String content = msg.getString(key);
        int lastStart = 0;
        while (matcher.find()) {
            String str = matcher.group(1);
            if (str.length() == 6) {

            } else if (str.length() == 10) {

            } else {
                str = "&#x" + str + ";";
                String tmp = content.substring(lastStart, matcher.start());
                sb.append(tmp + str);
                lastStart = matcher.end();
            }
        }
        if (lastStart < content.length()) {
            sb.append(content.substring(lastStart));
        }
        if (sb.length() != 0) {
            msg.put(key, EmojiParser.parseToUnicode(sb.toString()));
        } else {
            msg.put(key, content);
        }
    }

    /**
     * 消息格式化
     *
     * @param msg 消息体
     * @param key key
     */
    public static void msgFormatter(JSONObject msg, String key) {
        msg.put(key, msg.getString(key).replace("<br/>", "\n"));
        emojiFormatter(msg, key);
    }

}
