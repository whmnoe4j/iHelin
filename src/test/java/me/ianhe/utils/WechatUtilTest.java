package me.ianhe.utils;

import org.junit.Test;

/**
 * Created by iHelin on 17/4/23.
 */
public class WechatUtilTest {
    @Test
    public void doGetStr() throws Exception {
        String res = WechatUtil.doGetStr("https://www.ianhe.me/");
        System.out.println(res);
    }

    @Test
    public void doPostStr() throws Exception {
        String res = WechatUtil.doPostStr("http://127.0.0.1:8080/iHelin/test_post", "hello world");
        System.out.println(res);
    }

}