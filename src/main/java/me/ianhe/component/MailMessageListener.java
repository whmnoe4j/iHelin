package me.ianhe.component;

import me.ianhe.model.MailModel;
import me.ianhe.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * 邮件发送
 *
 * @author iHelin
 * @since 2017/6/23 09:51
 */
@Component
@EnableJms
public class MailMessageListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MailService mailService;

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "mail")
    public void onMessage(ObjectMessage message) {
        logger.debug("接收到一条邮件消息。");
        try {
            MailModel mail = (MailModel) message.getObject();
            mailService.sendHTMLMail(mail.getToAddress(), mail.getSubject(), mail.getContent());
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}
