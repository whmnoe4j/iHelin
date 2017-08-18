package me.ianhe.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created by iHelin on 17/4/11.
 */
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class CommonRedisDaoTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private CommonRedisDao commonRedisDao;

    @Test
    public void incr() throws Exception {
        long a = commonRedisDao.incr("a");
        System.out.println(a);
    }

    @Test
    public void getMap() throws Exception {
    }

}