package me.ianhe.transaction;

import me.ianhe.db.entity.Advice;
import me.ianhe.service.AdviceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.Date;

/**
 * 事务测试
 *
 * @author iHelin
 * @create 2017-04-22 09:35
 */
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class TransactionTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AdviceService adviceService;

    @Test
    public void testTransaction() {
        Advice advice = new Advice();
        advice.setEmail("email");
        advice.setCreateTime(new Date());
        advice.setMessage("msg");
        advice.setName("name");
        adviceService.insertTest(advice);
    }

    @Test
    @Rollback
    public void testRollback() {
        Advice advice = new Advice();
        advice.setEmail("email");
        advice.setCreateTime(new Date());
        advice.setMessage("msg");
        advice.setName("name");
        adviceService.insertMessage(advice);
    }

}
