package me.ianhe.test;

import me.ianhe.service.JMSProducerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;

/**
 * @author iHelin
 * @since 2017/6/23 09:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class JMSTest {

    @Autowired
    private JMSProducerService producerService;

    @Autowired
    @Qualifier("sevenQueue")
    private Destination destination;

    @Test
    public void testSend() {
        for (int i = 0; i < 5; i++) {
            producerService.sendMessage(destination, "Hello World！这是消息：" + (i + 1));
        }
    }
}
