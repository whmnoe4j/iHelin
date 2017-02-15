package me.ianhe.junit.suite;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author iHelin
 * @create 2017-02-04 17:43
 */
public class TestJunit {

    String message = "Hello World";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void testPrintMessage() {
        assertEquals(message,messageUtil.printMessage());
    }

    @Test
    public void testErrPrintMessage() {
        assertEquals(message,messageUtil.printMessage());
    }

}
