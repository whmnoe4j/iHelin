package me.ianhe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * @author iHelin
 * @since 2017/6/23 09:56
 */
@Service
public class JMSProducerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    public void sendMessage(Destination destination, final String message) {
        logger.debug("-----生产者发送了一个消息--------");
        jmsTemplate.send(destination, session -> session.createTextMessage(message));
    }

    public void sendMessage(Destination destination, final Serializable message) {
        logger.debug("-----生产者发送了一个消息--------");
        jmsTemplate.send(destination, session -> session.createObjectMessage(message));
    }
}
