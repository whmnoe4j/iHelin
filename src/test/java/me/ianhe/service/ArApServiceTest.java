package me.ianhe.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author iHelin
 * @since 2017/8/25 09:22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class ArApServiceTest {

    @Autowired
    private FinanceService financeService;

    @Test
    public void listClearCount() throws Exception {
        System.out.println(financeService.listClearCount());
    }

}