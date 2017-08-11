package me.ianhe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * @author iHelin
 * @since 2017/8/11 09:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JMSProducerServiceTest {

    @Autowired
    private JMSProducerService producerService;

    @Autowired
    @Qualifier("sevenQueue")
    private Destination destination;

    @Test
    public void testSendMessage() throws Exception {
        producerService.sendMessage(destination, "Hello World!");
    }

}