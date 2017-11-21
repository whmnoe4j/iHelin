package me.ianhe.test;

import me.ianhe.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author iHelin
 * @since 2017/11/16 12:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class PersonTest {

    @Autowired
    private Person person;

    @Test
    public void test(){
        Assert.assertNotNull(person);
        System.out.println(person.getName());
    }


}
