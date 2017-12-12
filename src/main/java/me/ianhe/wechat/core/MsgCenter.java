package me.ianhe.wechat.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.enums.MsgCodeEnum;
import me.ianhe.wechat.enums.MsgTypeEnum;
import me.ianhe.wechat.utils.CommonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

/**
 * 消息处理中心
 *
 * @author iHelin
 * @since 2017/12/11 14:28
 */
public class MsgCenter {

    private static Logger logger = LoggerFactory.getLogger(MsgCenter.class);
    private static Core core = Core.getInstance();

    /**
     * 接收消息，放入队列
     *
     * @param msgList
     * @return
     */
    public static JSONArray produceMsg(JSONArray msgList) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < msgList.size(); i++) {
            JSONObject msg = msgList.getJSONObject(i);
            msg.put("groupMsg", false);
            if (msg.getString("FromUserName").contains("@@") || msg.getString("ToUserName").contains("@@")) {
                // 群聊消息
                if (msg.getString("FromUserName").contains("@@")
                        && !core.getGroupIdList().contains(msg.getString("FromUserName"))) {
                    core.getGroupIdList().add((msg.getString("FromUserName")));
                } else if (msg.getString("ToUserName").contains("@@")
                        && !core.getGroupIdList().contains(msg.getString("ToUserName"))) {
                    core.getGroupIdList().add((msg.getString("ToUserName")));
                }
                String content = msg.getString("Content");
                // 群消息与普通消息不同的是在其消息体（Content）中会包含发送者id及":<br/>"消息，
                // 这里需要处理一下，去掉多余信息，只保留消息内容
                if (content.contains("<br/>")) {
                    content = content.substring(content.indexOf("<br/>") + 5);
                    msg.put("Content", content);
                    msg.put("groupMsg", true);
                }
            } else {
                CommonTools.msgFormatter(msg, "Content");
            }
            Integer msgType = msg.getInteger("MsgType");
            if (MsgCodeEnum.MSGTYPE_TEXT.getCode().equals(msgType)) {
                JSONObject msgJSON = new JSONObject();
                if (msg.getString("Url").length() != 0) {
                    String regEx = "(.+?\\(.+?\\))";
                    Matcher matcher = CommonTools.getMatcher(regEx, msg.getString("Content"));
                    String data = "Map";
                    if (matcher.find()) {
                        data = matcher.group(1);
                    }
                    msgJSON.put("Type", "Map");
                    msgJSON.put("Text", data);
                } else {
                    msgJSON.put("Type", MsgTypeEnum.TEXT.getType());
                    msgJSON.put("Text", msg.getString("Content"));
                }
                msg.put("Type", msgJSON.getString("Type"));
                msg.put("Text", msgJSON.getString("Text"));
            } else if (MsgCodeEnum.MSGTYPE_IMAGE.getCode().equals(msgType)
                    || MsgCodeEnum.MSGTYPE_EMOTICON.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.PIC.getType());
            } else if (MsgCodeEnum.MSGTYPE_VOICE.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.VOICE.getType());
            } else if (MsgCodeEnum.MSGTYPE_VERIFYMSG.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.VERIFYMSG.getType());
            } else if (MsgCodeEnum.MSGTYPE_SHARECARD.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.NAMECARD.getType());
            } else if (MsgCodeEnum.MSGTYPE_VIDEO.getCode().equals(msgType)
                    || MsgCodeEnum.MSGTYPE_MICROVIDEO.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.VIEDO.getType());
            } else if (MsgCodeEnum.MSGTYPE_MEDIA.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.MEDIA.getType());
            } else if (MsgCodeEnum.MSGTYPE_STATUSNOTIFY.getCode().equals(msgType)) {
                logger.info(" 微信初始化init消息：{}", msg);
            } else if (MsgCodeEnum.MSGTYPE_SYS.getCode().equals(msgType)) {
                msg.put("Type", MsgTypeEnum.SYS.getType());
            } else if (MsgCodeEnum.MSGTYPE_RECALLED.getCode().equals(msgType)) {
                logger.info("撤回消息：{}", msg);
            } else {
                logger.info("未知消息类型：{}", msgType);
            }
            logger.info("收到一条消息,type:{}", msgType);
            result.add(msg);
        }
        return result;
    }

    /**
     * 从队列取出消息并进行分发
     *
     * @param msgHandler
     */
    public static void handleMsg(MessageHandler msgHandler) {
        while (true) {
            if (core.getMsgList().size() > 0 && core.getMsgList().get(0).getContent() != null) {
                BaseMsg msg = core.getMsgList().get(0);
                if (MsgTypeEnum.TEXT.getType().equals(msg.getType())) {
                    msgHandler.handleTextMsg(msg);
                } else if (MsgTypeEnum.PIC.getType().equals(msg.getType())) {
                    msgHandler.handlePicMsg(msg);
                } else if (MsgTypeEnum.VOICE.getType().equals(msg.getType())) {
                    msgHandler.handleVoiceMsg(msg);
                } else if (MsgTypeEnum.VIEDO.getType().equals(msg.getType())) {
                    msgHandler.handleVideoMsg(msg);
                } else if (MsgTypeEnum.NAMECARD.getType().equals(msg.getType())) {
                    msgHandler.handleNameCardMsg(msg);
                } else if (MsgTypeEnum.SYS.getType().equals(msg.getType())) {
                    msgHandler.handleSysMsg(msg);
                } else if (MsgTypeEnum.VERIFYMSG.getType().equals(msg.getType())) {
                    msgHandler.handleVerifyAddFriendMsg(msg);
                } else if (MsgTypeEnum.MEDIA.getType().equals(msg.getType())) {
                    msgHandler.handleMediaMsg(msg);
                } else {
                    logger.debug("应该不会到这里:{}", msg.getType());
                }
                core.getMsgList().remove(0);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
