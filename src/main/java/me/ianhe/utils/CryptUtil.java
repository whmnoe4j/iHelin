
package me.ianhe.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5, sha1加密算法
 *
 * @author Ian He
 * @since 1.0
 */
public class CryptUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CryptUtil.class);
    private static final int RIGHT_MOVE_STEP = 4;
    private static final int ARR_LEN_MUL = 2;
    private static final String SHA1 = "SHA1";
    private static final String MD5 = "MD5";

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String digest(String s, String algorithm) {
        if (s == null || s.length() <= 0)
            return s;
        try {
            byte[] strTemp = s.getBytes(CharEncoding.UTF_8);
            MessageDigest mdTemp = MessageDigest.getInstance(algorithm);
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * ARR_LEN_MUL];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> RIGHT_MOVE_STEP & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String sha1(String s) {
        return digest(s, SHA1);
    }

    public static String md5(String s) {
        return digest(s, MD5);
    }

    /**
     * 文件md5加密
     *
     * @param f
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String md5(File f) {
        try (FileInputStream fis = new FileInputStream(f);
             ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            IOUtils.copy(fis, bout);
            return md5(bout.toByteArray());
        } catch (IOException e) {
            LOGGER.error("md5 error!", e);
            return null;
        }
    }

    /**
     * 字节数组加密
     *
     * @param content
     * @return
     */
    public static String md5(byte[] content) {
        return DigestUtils.md5DigestAsHex(content);
    }
}
