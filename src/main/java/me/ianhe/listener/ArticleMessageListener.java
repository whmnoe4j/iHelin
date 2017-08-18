package me.ianhe.listener;

import me.ianhe.dao.CommonRedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author iHelin
 * @since 2017/8/18 09:04
 */
@Component
@EnableJms
public class ArticleMessageListener {

    @Autowired
    protected CommonRedisDao commonRedisDao;
    private static final String READ_COUNT_KEY = "article:readCount:";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @JmsListener(containerFactory = "jmsListenerContainerFactory", destination = "article")
    public void onMessage(Message message) {
        TextMessage textMsg = (TextMessage) message;
        logger.debug("接收到文章消息");
        try {
            Integer articleID = Integer.valueOf(textMsg.getText());
            long count = commonRedisDao.incr(READ_COUNT_KEY + articleID);
            logger.debug("{} 的阅读量现在是：{}", articleID, count);
        } catch (JMSException e) {
            logger.error("消息接收异常！", e);
        }
    }
}
