package me.ianhe.utils;

import me.ianhe.service.Global;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by iHelin on 17/4/13.
 */
public class CryptUtilTest {

    private static final String ORIGIN = "246260";

    @Test
    public void sha1() throws Exception {
        System.out.println(CryptUtil.sha1(ORIGIN));
    }

    @Test
    public void md5() throws Exception {
        Assert.assertEquals(CryptUtil.md5(ORIGIN), CryptUtil.md5(ORIGIN.getBytes()));
    }

    @Test
    public void md52() throws Exception {
        System.out.println(CryptUtil.md5(new File(Global.getClassPath() + "/helloworld.xml")));
    }

    @Test
    public void base64() throws Exception {
        Assert.assertEquals(CryptUtil.decodeBase64(CryptUtil.encodeBase64(ORIGIN)), ORIGIN);
    }

}