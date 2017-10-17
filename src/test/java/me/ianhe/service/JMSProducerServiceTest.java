package me.ianhe.service;

import me.ianhe.model.MailModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * JMS邮件发送
 *
 * @author iHelin
 * @since 2017/8/11 09:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JMSProducerServiceTest {

    @Autowired
    private JmsProducerService producerService;

    @Autowired
    @Qualifier("mailQueue")
    private Destination destination;

    /**
     * 测试邮件发送
     *
     * @author iHelin
     * @since 2017/9/15 13:49
     */
    @Test
    public void testSendMessage() throws Exception {
        MailModel mail = new MailModel("ahaqhelin@163.com;", "何霖", "测试一下", "啦啦啦");
        producerService.sendMessage(destination, mail);
    }

}