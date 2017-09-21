package me.ianhe.service;

import com.beust.jcommander.internal.Maps;
import com.thankjava.wqq.SmartQQClient;
import com.thankjava.wqq.consts.MsgType;
import com.thankjava.wqq.entity.msg.SendMsg;
import com.thankjava.wqq.entity.wqq.GroupInfo;
import com.thankjava.wqq.entity.wqq.GroupsList;
import me.ianhe.config.QQNotifyListener;
import me.ianhe.dao.PoemMapper;
import me.ianhe.db.entity.Poem;
import me.ianhe.utils.DingUtil;
import me.ianhe.utils.JSON;
import me.ianhe.utils.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author iHelin
 * @since 2017/6/13 15:53
 */
@Service
public class DailyService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PoemMapper poemMapper;

    @Autowired
    private QQNotifyListener listener;

    public void run() {
        dailyEnglish();
    }

    public void poemRun() {
        Poem poem = poemMapper.getByRandom();
        String msg = poem.getContent() + "      --" + poem.getTitle();
        DingUtil.say(msg);
        sendqq(msg);
    }

    private void dailyEnglish() {
        Map<String, Object> contentMap = Maps.newHashMap();
        String res = WechatUtil.doGetStr("http://open.iciba.com/dsapi");
        Map<String, Object> resMap = JSON.parseMap(res);
        contentMap.put("title", "葫芦娃学英语");
        String text = "## 葫芦娃学英语\n" +
                "![screenshot](" + resMap.get("picture") + ")\n" +
                "##### " + resMap.get("content") + " \n" +
                "> " + resMap.get("note") + " \n";
        contentMap.put("text", text);
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "markdown");
        data.put("markdown", contentMap);
        String jsonData = JSON.toJson(data);
        logger.debug("每日一句：{}", jsonData);
        DingUtil.doSay(jsonData);
        sendqq(resMap.get("content") + "    " + resMap.get("note"));
    }

    private void sendqq(String msg) {
        SmartQQClient smartQQClient = listener.getSmartQQClient();
        GroupsList groupsList = smartQQClient.getGroupsList(true);
        logger.debug(JSON.toJson(groupsList.getGroups()));
        Map<Long, GroupInfo> groupInfoMap = groupsList.getGroups();
        for (Map.Entry<Long, GroupInfo> groupInfoEntry : groupInfoMap.entrySet()) {
            if (groupInfoEntry.getValue().getName().equals("一点点学习小组")) {
                long gid = groupInfoEntry.getKey();
                SendMsg sendMsg = new SendMsg(gid, MsgType.group_message, msg);
                smartQQClient.sendMsg(sendMsg);
                logger.debug("定时发送");
            }
        }
    }

}
