package me.ianhe.service;

import com.alibaba.fastjson.JSON;
import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.beans.RecommendInfo;
import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.core.MessageHandler;
import me.ianhe.wechat.enums.MsgTypeEnum;
import me.ianhe.wechat.utils.DownloadTools;
import me.ianhe.wechat.utils.WeChatTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息处理类
 *
 * @author iHelin
 * @since 2017/12/11 21:29
 */
@Service
public class MessageHandlerService implements MessageHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FileService fileService;

    @PostConstruct
    public void init() {
        logger.info("防止SSL错误，设置系统变量：jsse.enableSNIExtension");
        System.setProperty("jsse.enableSNIExtension", "false");
    }

    @Override
    public void textMsgHandle(BaseMsg msg) {
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
                WeChatTools.sendMsgByUsername("谢谢", msg.getFromUserName());
            }
        } else {
            System.out.println("[群消息]" + msg.getFromUserName() + ":" + msg.getText());
        }
    }

    @Override
    public void sysMsgHandle(BaseMsg msg) {
        // 收到系统消息
        String text = msg.getContent();
        logger.debug("系统消息：{}", text);
        if ("发出红包，请在手机上查看".equals(text)) {

        }
    }

    @Override
    public void verifyAddFriendMsgHandle(BaseMsg msg) {
        WeChatTools.addFriend(msg, true);
        RecommendInfo recommendInfo = msg.getRecommendInfo();
        String nickName = recommendInfo.getNickName();
        String province = recommendInfo.getProvince();
        String city = recommendInfo.getCity();
        String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
        WeChatTools.sendMsgByUsername(text, msg.getFromUserName());
    }

    @Override
    public void picMsgHandle(BaseMsg msg) {
        String fileName = new SimpleDateFormat("wechat/pic/yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg";
        fileService.uploadFile(fileName, DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType()));
    }

    @Override
    public void voiceMsgHandle(BaseMsg msg) {
        String fileName = "wechat/voice/" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3";
        fileService.uploadFile(fileName, DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType()));
    }

    @Override
    public void videoMsgHandle(BaseMsg msg) {
        String fileName = "wechat/video/" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp4";
        fileService.uploadFile(fileName, DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType()));
    }

    @Override
    public void nameCardMsgHandle(BaseMsg msg) {

    }

    @Override
    public void mediaMsgHandle(BaseMsg msg) {
        System.out.println(JSON.toJSONString(msg));
        //我已经在百词斩上坚持了157天，今日过招27个单词。
        if (msg.getFileName().contains("我已经在百词斩上坚持了")) {
            WeChatTools.sendMsgByUsername("很好，继续努力", msg.getFromUserName());
        }
    }
}
