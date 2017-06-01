package me.ianhe.utils;

import org.junit.Test;

import java.util.Random;

/**
 * Created by iHelin on 17/4/13.
 */
public class QRCodeTest {

    @Test
    public void generateQRCode() throws Exception {
        int width = 300;
        int height = 300;
        String format = "png";
        String content = "2";
        String fileName = new Random().nextInt(1000000) + "." + format;
        String name = QRCodeUtil.generateQRCode("", content, fileName, format, width, height);
        System.out.println(name);
    }

}