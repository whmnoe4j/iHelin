package me.ianhe.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author iHelin
 * @since 2017/9/15 14:21
 */
public class MailUtilTest {

    @Test
    public void sendHTMLMail() throws Exception {
        MailUtil.sendHTMLMail("ahaqhelin@163.com", "iHelin", "测试一下",
                "哈哈哈<h1>234trce</h1>");
    }

    @Test
    public void sendTextMail() throws Exception {
        MailUtil.sendTextMail("ahaqhelin@163.com", "iHelin", "测试一下",
                "哈哈哈<h1>234trce</h1>");
    }

}