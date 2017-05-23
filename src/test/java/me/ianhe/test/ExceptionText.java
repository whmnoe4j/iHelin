package me.ianhe.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author iHelin
 * @create 2017-02-23 23:07
 */
public class ExceptionText {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        try {
            test();
        } catch (IOException e) {
            e.printStackTrace();
            Logger logger = LoggerFactory.getLogger(ExceptionText.class);
            logger.error(e.getMessage());
        }
    }

    private static void test() throws IOException {
        throw new IOException("哈哈！");
    }

    @Test
    public void testLog() {
        logger.debug("12345678");
        logger.trace("Hello World!");
        logger.debug("How are you today?");
        logger.info("I am fine.");
        logger.warn("I love programming.");
        logger.error("I am programming.");
    }

}
