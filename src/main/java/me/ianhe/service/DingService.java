package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.utils.JsonUtil;
import me.ianhe.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/14 17:04
 */
@Service
public class DingService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Global global;

    /**
     * 发送指定文本
     *
     * @author iHelin
     * @since 2017/5/17 11:16
     */
    public String say(String content) {
        Map<String, Object> contentMap = Maps.newHashMap();
        contentMap.put("content", content);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "text");
        data.put("text", contentMap);
        return doSay(JsonUtil.toJson(data));
    }

    public String doSay(String data) {
        String res = WechatUtil.doPostStr(global.getValue("ding.robot"), data);
        logger.info("Robot return {}", res);
        return res;
    }

}
