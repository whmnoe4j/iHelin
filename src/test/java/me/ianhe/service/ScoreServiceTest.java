package me.ianhe.service;

import me.ianhe.db.entity.MyScore;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author iHelin
 * @since 2017/5/31 16:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class ScoreServiceTest {
    @Autowired
    private ScoreService scoreService;

    @Test
    public void addRecord() throws Exception {
        MyScore myScore = new MyScore();
        myScore.setAddDate(new Date());
        myScore.setAddWriter(1);
        myScore.setReason("test");
        myScore.setScore(1);
        int res = scoreService.addRecord(myScore);
        Assert.assertNotNull(res);
    }

}