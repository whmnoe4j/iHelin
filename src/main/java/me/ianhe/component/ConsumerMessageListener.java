package me.ianhe.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author iHelin
 * @since 2017/6/23 09:51
 */
@Component
@EnableJms
public class ConsumerMessageListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "seven")
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        logger.debug("接收到一个纯文本消息。");
        try {
            logger.debug("消息内容是：{}", textMsg.getText());
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}
