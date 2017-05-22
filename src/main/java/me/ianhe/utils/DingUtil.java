package me.ianhe.utils;

import com.beust.jcommander.internal.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 钉钉机器人
 *
 * @author iHelin
 * @create 2017-03-01 22:34
 */
public class DingUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DingUtil.class);
    private static final String ADDRESS = Global.getValue("ding.robot.url");

    /**
     * 发送指定文本
     *
     * @author iHelin
     * @since 2017/5/17 11:16
     */
    public static String say(String content) {
        Map<String, Object> contentMap = Maps.newHashMap();
        contentMap.put("content", content);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "text");
        data.put("text", contentMap);
        return doSay(JSON.toJson(data));
    }

    public static String doSay(String data) {
        String res = WechatUtil.doPostStr(ADDRESS, data);
        LOGGER.info("Robot return {}", res);
        return res;
    }

    private DingUtil() {
        //工具类不允许实例化
    }

}
