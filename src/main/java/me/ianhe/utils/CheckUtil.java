package me.ianhe.utils;

import me.ianhe.exception.SystemException;

import java.util.Arrays;

/**
 * 微信签名验证
 *
 * @author iHelin
 * @since 2017/10/17 15:28
 */
public class CheckUtil {

    public static boolean checkSignature(String token,String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        // 排序
        Arrays.sort(arr);
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        // sha1加密
        String temp = CryptUtil.sha1(content.toString());
        return temp.equals(signature);
    }

    private CheckUtil() {
        throw new SystemException("工具类不允许实例化！");
    }

}
