package me.ianhe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author iHelin
 * @since 2017/11/14 17:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class MainServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void sendTextMail() throws Exception {
        mailService.sendTextMail("ahaqhelin@163.com", "测试一下",
                "哈哈哈<h1>234trce</h1>");
    }

    @Test
    public void sendHTMLMail() throws Exception {
        mailService.sendHTMLMail("ahaqhelin@163.com", "测试一下",
                "哈哈哈<h1>234trce</h1>");
    }

}