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

    //葫芦娃的机器人
    private static final String DING_ROBOT_URL = "https://oapi.dingtalk.com/robot/send?access_token" +
            "=0822db7059b63a7f73a12e0b665574310108c73649e256c87c646394e63fc6a2";

    private static final Logger LOGGER = LoggerFactory.getLogger(DingUtil.class);

    public static void say(String content) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", content);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "text");
        data.put("text", map);
        String res = WechatUtil.doPostStr(DING_ROBOT_URL, JSON.toJson(data));
        LOGGER.info("Robut return {}", res);
    }

    public static void main(String[] args) {
        say("！");
    }

    private DingUtil() {
        //工具类不允许实例化
    }

}
