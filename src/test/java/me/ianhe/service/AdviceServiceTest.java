package me.ianhe.service;

import me.ianhe.db.entity.Advice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by iHelin on 17/4/15.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class AdviceServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

    @Autowired
    private AdviceService adviceService;

    @Test
    @Rollback(true)
    public void insertMessage() throws Exception {
        Advice advice = new Advice();
        advice.setName("test");
        advice.setEmail("ihelin@outlook.com");
        advice.setMessage("hhhhhhhhh");
        advice.setCreateTime(new Date());
        adviceService.insertMessage(advice);
        Assert.assertNotNull(advice.getId());
    }

    @Test
    public void selectAdviceByCondition() throws Exception {
        List<Advice> advices = adviceService.selectAdviceByCondition();
        for (Advice advice : advices) {
            System.out.println(advice.getMessage());
        }
    }

}