
package me.ianhe.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * md5, sha1加密算法
 *
 * @author Ian He
 * @since 1.0
 */
public class CryptUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptUtil.class);

    public static String sha1(String s) {
        return DigestUtils.sha1Hex(s);
    }

    public static String md5(byte[] content) {
        return DigestUtils.md5Hex(content);
    }

    public static String md5(String s) {
        return DigestUtils.md5Hex(s);
    }

    /**
     * md5文件加密
     *
     * @param f
     * @return
     */
    public static String md5(File f) {
        try (FileInputStream fis = new FileInputStream(f)) {
            byte[] bytes = DigestUtils.md5(fis);
            return new String(bytes);
        } catch (IOException e) {
            LOGGER.error("md5 error!", e);
            return null;
        }
    }

    public static String encodeBase64(String s) {
        byte[] bytes = Base64.getEncoder().encode(s.getBytes());
        return new String(bytes);
    }

    public static String decodeBase64(String s) {
        byte[] bytes = Base64.getDecoder().decode(s.getBytes());
        return new String(bytes);
    }

    public static void main(String[] args) {
        String s = "246260";
        System.out.println(md5(s));
        System.out.println(encodeBase64(s));
        System.out.println(decodeBase64(encodeBase64(s)));
    }

    private CryptUtil() {

    }

}
