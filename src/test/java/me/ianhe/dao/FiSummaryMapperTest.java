package me.ianhe.dao;

import me.ianhe.utils.JsonUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/8/26 22:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class FiSummaryMapperTest {
    @Autowired
    private FiSummaryMapper fiSummaryMapper;

    @Test
    public void getIntervalSummary() throws Exception {
        List<HashMap> hashMapList = fiSummaryMapper.getIntervalSummary(null);
        Assert.assertNotNull(hashMapList);
        System.out.println(JsonUtil.toJson(hashMapList));
    }

}