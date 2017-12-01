package me.ianhe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author iHelin
 * @since 2017/12/1 15:11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class DingServiceTest {

    @Autowired
    private DingService dingService;

    @Test
    public void say() {

    }

    @Test
    public void doSay() {
    }
}