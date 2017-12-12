package me.ianhe.component;

import me.ianhe.wechat.beans.BaseMsg;
import me.ianhe.wechat.core.MessageHandler;
import me.ianhe.wechat.enums.MsgTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * @author iHelin
 * @since 2017/12/12 14:58
 */
@Component
@EnableJms
public class WeChatMessageListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected MessageHandler messageHandler;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "weChatMsg")
    public void onMessage(ObjectMessage message) {
        logger.debug("接收到一条weChat消息。");
        try {
            BaseMsg baseMsg = (BaseMsg) message.getObject();
            if (MsgTypeEnum.TEXT.getType().equals(baseMsg.getType())) {
                messageHandler.handleTextMsg(baseMsg);
            } else if (MsgTypeEnum.PIC.getType().equals(baseMsg.getType())) {
                messageHandler.handlePicMsg(baseMsg);
            } else if (MsgTypeEnum.VOICE.getType().equals(baseMsg.getType())) {
                messageHandler.handleVoiceMsg(baseMsg);
            } else if (MsgTypeEnum.VIEDO.getType().equals(baseMsg.getType())) {
                messageHandler.handleVideoMsg(baseMsg);
            } else if (MsgTypeEnum.NAMECARD.getType().equals(baseMsg.getType())) {
                messageHandler.handleNameCardMsg(baseMsg);
            } else if (MsgTypeEnum.SYS.getType().equals(baseMsg.getType())) {
                messageHandler.handleSysMsg(baseMsg);
            } else if (MsgTypeEnum.VERIFYMSG.getType().equals(baseMsg.getType())) {
                messageHandler.handleVerifyAddFriendMsg(baseMsg);
            } else if (MsgTypeEnum.MEDIA.getType().equals(baseMsg.getType())) {
                messageHandler.handleMediaMsg(baseMsg);
            } else {
                logger.debug("应该不会到这里:{}", baseMsg.getType());
            }
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}