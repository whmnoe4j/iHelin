package me.ianhe.redis;

import me.ianhe.dao.TUser;
import me.ianhe.dao.UserDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.ArrayList;
import java.util.List;

/**
 * @author iHelin
 * @create 2017-04-11 15:12
 */
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class RedisTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserDao userDao;

    /**
     * 新增
     */
    @Test
    public void testAddUser() {
        TUser user = new TUser();
        user.setId("user1");
        user.setName("seven");
        boolean result = userDao.add(user);
        Assert.assertTrue(result);
    }

    /**
     * 批量新增 普通方式
     */
    @Test
    public void testAddUsers1() {
        List<TUser> list = new ArrayList<TUser>();
        for (int i = 10; i < 500; i++) {
            TUser user = new TUser();
            user.setId("user" + i);
            user.setName("java500_wl" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        for (TUser user : list) {
            userDao.add(user);
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    /**
     * 批量新增 pipeline方式
     */
    @Test
    public void testAddUserTests2() {
        List<TUser> list = new ArrayList<TUser>();
        for (int i = 10; i < 1500; i++) {
            TUser user = new TUser();
            user.setId("user" + i);
            user.setName("javaee2000_wl" + i);
            list.add(user);
        }
        long begin = System.currentTimeMillis();
        boolean result = userDao.add(list);
        System.out.println("*************************");
        System.out.println(System.currentTimeMillis() - begin);
        System.out.println("*************************");
        Assert.assertTrue(result);
    }

    /**
     * 修改
     */
    @Test
    public void testUpdate() {
        TUser user = new TUser();
        user.setId("user1");
        user.setName("new_password");
        boolean result = userDao.update(user);
        Assert.assertTrue(result);
    }

    /**
     * 通过key删除单个
     */
    @Test
    public void testDelete() {
        String key = "user1";
        userDao.delete(key);
    }

    /**
     * 批量删除
     */
    @Test
    public void testDeletes() {
        List<String> list = new ArrayList<String>();
        for (int i = 10; i < 1500; i++) {
            list.add("user" + i);
        }
        userDao.delete(list);
    }

    /**
     * 获取
     */
    @Test
    public void testGetUserTest() {
        String id = "user1";
        TUser user = userDao.get(id);
        System.out.println(user.getName());
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), "seven");
    }

}
