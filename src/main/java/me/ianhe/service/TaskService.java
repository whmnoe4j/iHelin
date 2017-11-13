package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.dao.PoemMapper;
import me.ianhe.db.entity.Poem;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.JsonUtil;
import me.ianhe.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/6/13 15:53
 */
@Service
public class TaskService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PoemMapper poemMapper;

    public Poem getPoemByRandom() {
        return poemMapper.getByRandom();
    }

    public void run() {
        dailyEnglish();
    }

    /**
     * 古诗
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    public void poemRun() {
        Poem poem = poemMapper.getByRandom();
        String msg = poem.getContent() + "      --" + poem.getTitle();
        DingUtil.say(msg);
    }

    /**
     * 英语
     *
     * @author iHelin
     * @since 2017/11/13 17:17
     */
    private void dailyEnglish() {
        Map<String, Object> contentMap = Maps.newHashMap();
        String res = WechatUtil.doGetStr("http://open.iciba.com/dsapi");
        Map<String, Object> resMap = JsonUtil.parseMap(res);
        contentMap.put("title", "葫芦娃学英语");
        String text = "## 葫芦娃学英语\n" +
                "![screenshot](" + resMap.get("picture") + ")\n" +
                "##### " + resMap.get("content") + " \n" +
                "> " + resMap.get("note") + " \n";
        contentMap.put("text", text);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "markdown");
        data.put("markdown", contentMap);
        String jsonData = JsonUtil.toJson(data);
        logger.debug("每日一句：{}", jsonData);
        DingUtil.doSay(jsonData);
    }

    /**
     * 倒计时
     *
     * @author iHelin
     * @since 2017/11/13 17:16
     */
    public void countDown() {
        Calendar examDate = new Calendar.Builder().setDate(2017, Calendar.OCTOBER, 14)
                .build();
        long examDateLong = examDate.getTimeInMillis();
        long nowLong = System.currentTimeMillis();
        long betweenDays = (examDateLong - nowLong) / (1000L * 3600 * 24) + 1;
        if (betweenDays > 0) {
            DingUtil.say("今天距离考试还剩" + betweenDays + "天！");
        }
    }

}
