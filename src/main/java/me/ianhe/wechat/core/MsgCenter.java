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
            JSONObject msg = new JSONObject();
            JSONObject m = msgList.getJSONObject(i);
            m.put("groupMsg", false);
            Integer msgType = m.getInteger("MsgType");
            if (m.getString("FromUserName").contains("@@") || m.getString("ToUserName").contains("@@")) {
                // 群聊消息
                if (m.getString("FromUserName").contains("@@")
                        && !core.getGroupIdList().contains(m.getString("FromUserName"))) {
                    core.getGroupIdList().add((m.getString("FromUserName")));
                } else if (m.getString("ToUserName").contains("@@")
                        && !core.getGroupIdList().contains(m.getString("ToUserName"))) {
                    core.getGroupIdList().add((m.getString("ToUserName")));
                }
                // 群消息与普通消息不同的是在其消息体（Content）中会包含发送者id及":<br/>"消息，这里需要处理一下，去掉多余信息，只保留消息内容
                if (m.getString("Content").contains("<br/>")) {
                    String content = m.getString("Content").substring(m.getString("Content").indexOf("<br/>") + 5);
                    m.put("Content", content);
                    m.put("groupMsg", true);
                }
            } else {
                CommonTools.msgFormatter(m, "Content");
            }
            if (msgType.equals(MsgCodeEnum.MSGTYPE_TEXT.getCode())) {
                // 文本消息
                if (m.getString("Url").length() != 0) {
                    String regEx = "(.+?\\(.+?\\))";
                    Matcher matcher = CommonTools.getMatcher(regEx, m.getString("Content"));
                    String data = "Map";
                    if (matcher.find()) {
                        data = matcher.group(1);
                    }
                    msg.put("Type", "Map");
                    msg.put("Text", data);
                } else {
                    msg.put("Type", MsgTypeEnum.TEXT.getType());
                    msg.put("Text", m.getString("Content"));
                }
                m.put("Type", msg.getString("Type"));
                m.put("Text", msg.getString("Text"));
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_IMAGE.getCode())
                    || msgType.equals(MsgCodeEnum.MSGTYPE_EMOTICON.getCode())) {
                // 图片消息
                m.put("Type", MsgTypeEnum.PIC.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_VOICE.getCode())) {
                // 语音消息
                m.put("Type", MsgTypeEnum.VOICE.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_VERIFYMSG.getCode())) {
                // 好友确认消息
                // MessageTools.addFriend(core, userName, 3, ticket); // 确认添加好友
                m.put("Type", MsgTypeEnum.VERIFYMSG.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_SHARECARD.getCode())) {
                // 共享名片
                m.put("Type", MsgTypeEnum.NAMECARD.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_VIDEO.getCode())
                    || msgType.equals(MsgCodeEnum.MSGTYPE_MICROVIDEO.getCode())) {
                // viedo
                m.put("Type", MsgTypeEnum.VIEDO.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_MEDIA.getCode())) {
                // 多媒体消息
                m.put("Type", MsgTypeEnum.MEDIA.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_STATUSNOTIFY.getCode())) {
                // 微信初始化init消息
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_SYS.getCode())) {
                // 系统消息
                m.put("Type", MsgTypeEnum.SYS.getType());
            } else if (msgType.equals(MsgCodeEnum.MSGTYPE_RECALLED.getCode())) {
                // 撤回消息
            } else {
                logger.info("Useless msg");
            }
            logger.info("收到一条消息,type:{}", msgType);
            result.add(m);
        }
        return result;
    }

    /**
     * 接收消息进行分发处理
     *
     * @param msgHandler
     */
    public static void handleMsg(MessageHandler msgHandler) {
        while (true) {
            if (core.getMsgList().size() > 0 && core.getMsgList().get(0).getContent() != null) {
                BaseMsg msg = core.getMsgList().get(0);
                if (msg.getType() != null) {
                    try {
                        if (msg.getType().equals(MsgTypeEnum.TEXT.getType())) {
                            //文本消息
                            msgHandler.textMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.PIC.getType())) {
                            msgHandler.picMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.VOICE.getType())) {
                            msgHandler.voiceMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.VIEDO.getType())) {
                            msgHandler.videoMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.NAMECARD.getType())) {
                            msgHandler.nameCardMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.SYS.getType())) {
                            // 系统消息
                            msgHandler.sysMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.VERIFYMSG.getType())) {
                            // 确认添加好友消息
                            msgHandler.verifyAddFriendMsgHandle(msg);
                        } else if (msg.getType().equals(MsgTypeEnum.MEDIA.getType())) {
                            // 多媒体消息
                            msgHandler.mediaMsgHandle(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
