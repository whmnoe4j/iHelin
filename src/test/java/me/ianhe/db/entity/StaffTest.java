package me.ianhe.db.entity;

import me.ianhe.utils.SerializationUtil;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by iHelin on 17/4/12.
 */
public class StaffTest {
    @Test
    public void equals() throws Exception {
        Staff staff = new Staff();
        staff.setName("seven");
        staff.setId(1);
        staff.setAccumulationFund(new BigDecimal("100.0"));
        System.out.println(staff);
        byte[] objectData = SerializationUtil.write(staff);
        Staff staff2 = SerializationUtil.read(objectData);
        System.out.println(staff2);
        Assert.assertEquals(staff, staff2);
    }

}