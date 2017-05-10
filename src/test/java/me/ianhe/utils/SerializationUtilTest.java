package me.ianhe.utils;

import me.ianhe.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 序列化测试
 * Created by iHelin on 17/5/10 16:01.
 */
public class SerializationUtilTest {

    private Person person1;

    @Before
    public void setUp() {
        person1 = new Person();
        person1.setName("Seven");
        person1.setAge(7);
    }

    @Test
    public void serializeTest() throws Exception {
        System.out.println(person1);
        byte[] objectData = SerializationUtil.write(person1);
        Person person2 = SerializationUtil.read(objectData);
        System.out.println(person2);
        Assert.assertEquals(person1, person2);
    }

}