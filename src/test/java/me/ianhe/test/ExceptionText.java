package me.ianhe.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author iHelin
 * @create 2017-02-23 23:07
 */
public class ExceptionText {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionText.class);

    public static void main(String[] args) {
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
//            e.printStackTrace();
        }
    }

    private static void test() throws IOException {
        throw new IOException("哈哈！");
    }

}
