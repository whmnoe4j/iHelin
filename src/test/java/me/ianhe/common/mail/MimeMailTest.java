package me.ianhe.common.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by iHelin on 17/3/14.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class MimeMailTest {


    @Autowired
    private SimpleMail mimeMail;

    @Test
    public void sendMail() throws Exception {
        mimeMail.sendMail("ihelin@126.com",
                "ahaqhelin@163.com",
                "Testing123",
                "<h1>你好！</h1>");
    }

}