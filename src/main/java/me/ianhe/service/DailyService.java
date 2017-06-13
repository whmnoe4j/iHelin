package me.ianhe.service;

import com.beust.jcommander.internal.Maps;
import me.ianhe.model.AccessTokenTimerTask;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.JSON;
import me.ianhe.utils.WechatUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author iHelin
 * @since 2017/6/13 15:53
 */
@Service
public class DailyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 设置定时器，定期检查
    @PostConstruct
    public void init() {
        Timer t = new Timer("daily post");
        Date date = new Date();
        DateUtils.setHours(date, 18);
        DateUtils.setMinutes(date, 0);
        DateUtils.setSeconds(date, 0);
        DateUtils.setMilliseconds(date, 0);
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                englishDaily();
            }
        }, date, 1000L * 60 * 60 * 24);
    }

    private static void englishDaily() {
        Map<String, Object> contentMap = Maps.newHashMap();
        String res = WechatUtil.doGetStr("http://open.iciba.com/dsapi");
        Map<String, Object> resMap = JSON.parseMap(res);
        contentMap.put("title", "葫芦娃学英语");
        String text = "## 葫芦娃英语\n" +
                "![screenshot](" + resMap.get("picture") + ")\n" +
                "##### " + resMap.get("content") + " \n" +
                "> " + resMap.get("note") + " \n";
        contentMap.put("text", text);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "markdown");
        data.put("markdown", contentMap);
        DingUtil.doSay(JSON.toJson(data));
    }

    public static void main(String[] args) {
        englishDaily();
    }

}
