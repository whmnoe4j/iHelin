package me.ianhe.service;

import com.alibaba.fastjson.JSON;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.core.MessageHandler;
import me.ianhe.wechat.enums.MsgTypeEnum;
import me.ianhe.wechat.utils.CommonTools;
import me.ianhe.wechat.utils.WeChatTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * 消息处理类
 *
 * @author iHelin
 * @since 2017/12/11 21:29
 */
@Service
public class MessageHandlerService implements MessageHandler {

    @Autowired
    private FileService fileService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String familyID = "@@2b50dc80a3091e79eca1bea77c617a389b47873c385a29897f368169d2867cf5";

    @PostConstruct
    public void init() {
        logger.info("防止SSL错误，设置系统变量：jsse.enableSNIExtension");
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    @Override
    public void handleTextMsg(BaseMsg msg) {
        if (!msg.isGroupMsg()) {
            String text = msg.getText();
            logger.info(text);
            if ("222".equals(text)) {
                WeChatTools.remarkNameByNickName("yaphone", "Hello");
            }
            if ("333".equals(text)) {
                // 测试群列表
                System.out.println(WeChatTools.getGroupNickNameList());
                System.out.println(WeChatTools.getGroupIdList());
                System.out.println(Core.getInstance().getGroupMemeberMap());
            }
            if ("4".equals(text)) {
                System.out.println(WeChatTools.getContactNickNameList());
            }
            if ("5".equals(text)) {
                System.out.println(WeChatTools.getUserNameByNickName("下辈子一定要做只猪"));
            }
            if ("6".equals(text)) {
                System.out.println(WeChatTools.getContactList());
            }
            if (!msg.getFromUserName().equals(Core.getInstance().getUserName())) {
//                WeChatTools.sendTextMsgByUsername("谢谢", msg.getFromUserName());
//                TODO 回复别人的消息
            }
        } else {
            logger.debug("[群消息]" + msg.getFromUserName() + ":" + msg.getText());
        }
    }

    @Override
    public void handleSysMsg(BaseMsg msg) {
        logger.debug("系统消息：{}", JSON.toJSONString(msg));
        String content = msg.getContent();
        //收到红包，请在手机上查看
        //发出红包，请在手机上查看
        if (content.contains("红包，请在手机上查看")
                && familyID.equals(msg.getFromUserName())) {

        }
    }

    @Override
    public void handleVerifyAddFriendMsg(BaseMsg msg) {
        /*WeChatTools.addFriend(msg, true);
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String nickName = recommendInfo.getNickName();
        String province = recommendInfo.getProvince();
        String city = recommendInfo.getCity();
        String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
        WeChatTools.sendTextMsgByUsername(text, msg.getFromUserName());*/
    }

    @Override
    public void handlePicMsg(BaseMsg msg) {
        String fileName = "wechat/pic/" + new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()) + ".jpg";
        fileService.uploadFile(fileName, WeChatTools.downloadMsg(msg, MsgTypeEnum.PIC.getType()));
    }

    @Override
    public void handleVoiceMsg(BaseMsg msg) {
//        String fileName = "wechat/voice/" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3";
//        fileService.uploadFile(fileName, WeChatTools.downloadMsg(msg, MsgTypeEnum.VOICE.getType()));
    }

    @Override
    public void handleVideoMsg(BaseMsg msg) {
//        String fileName = "wechat/video/" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp4";
//        fileService.uploadFile(fileName, WeChatTools.downloadMsg(msg, MsgTypeEnum.VIEDO.getType()));
    }

    @Override
    public void handleNameCardMsg(BaseMsg msg) {

    }

    @Override
    public void handleMediaMsg(BaseMsg msg) {
        System.out.println(JSON.toJSONString(msg));
        //我已经在百词斩上坚持了157天，今日过招27个单词。
        if (familyID.equals(msg.getFromUserName())) {
            Matcher matcher = CommonTools.getMatcher(".*百词斩上坚持了(\\d+)天.*招(\\d+)个单词.*", msg.getFileName());
            if (matcher.find()) {
                String day = matcher.group(1);
                String count = matcher.group(2);
                WeChatTools.sendTextMsgByUsername("很好，继续努力", msg.getFromUserName());
            }
        }
    }

    public static void main(String[] args) {
        Matcher matcher = CommonTools.getMatcher(".*百词斩上坚持了(\\d+)天.*招(\\d+)个单词.*", "我已经在百词斩上坚持了157天，今日过招27个单词。");
        if (matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }
    }
}
