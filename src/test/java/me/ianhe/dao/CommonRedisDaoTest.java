package me.ianhe.dao;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by iHelin on 17/4/11.
 */
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class CommonRedisDaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private RedisDao commonRedisDao;

    @Test
    public void saveMap() throws Exception {
        Map<String, String> param = Maps.newHashMap();
        param.put("h1", "h1");
        param.put("h2", "h2");
        commonRedisDao.saveMap("map", param);
    }

    @Test
    public void getMap() throws Exception {
        Map<String, String> res = commonRedisDao.getMap("map");
        System.out.println("************");
        System.out.println(res.get("h1"));
    }

    @Test
    public void delHashKeyField() throws Exception {
        commonRedisDao.delHashKeyField("map", "h1");
    }

    @Test
    public void hasKey() throws Exception {
        boolean has = commonRedisDao.hasKey("map");
        Assert.assertTrue(has);
    }

    @Test
    public void delKey() throws Exception {
        commonRedisDao.delKey("map");
    }

    @Test
    public void saveString() throws Exception {
        commonRedisDao.saveString("string", "好的");
    }

    @Test
    public void getString() {
        String s = commonRedisDao.getString("string");
        System.out.println(s);
        Assert.assertEquals(s, "好的");
    }

    /**
     * 十秒后过期
     */
    @Test
    public void saveExpireString() {
        commonRedisDao.saveExpireString("time", "qwert", 10L);
    }

    @Test
    public void incrementLong() throws InterruptedException {
//        commonRedisDao.saveExpireString("time", "qwert", 10L);
//        Thread.currentThread().sleep(5);
//        commonRedisDao.incrementLong("time", 5L);
    }

    @Test
    public void saveSet() throws Exception {
        Set<String> s = Sets.newHashSet();
        s.add("qqq");
        s.add("www");
        s.add("eee");
        s.add("rrr");
        commonRedisDao.saveSet("set", s);
    }

    @Test
    public void addSetValue() throws Exception {
        commonRedisDao.addSetValue("set", "ttt");
    }

    @Test
    public void getSet() throws Exception {
        Set<String> set = commonRedisDao.getSet("set");
        for (String s : set) {
            System.out.println(s);
        }
    }

    @Test
    public void saveList() throws Exception {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        commonRedisDao.saveList("list", list);
    }

    @Test
    public void getList() throws Exception {
        List<String> list = commonRedisDao.getList("list", 0, 10);
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void addListValue() throws Exception {
        commonRedisDao.addListValue("list", "4");
    }

    @Test
    public void getListPop() throws Exception {
        String s = commonRedisDao.getListPop("list");
        System.out.println(s);
    }

}