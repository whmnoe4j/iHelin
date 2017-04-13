package me.ianhe.utils;

import me.ianhe.db.entity.Staff;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对象序列化工具
 * 要序列化的类需实现Serializable接口
 *
 * @author iHelin
 * @create 2017-04-12 22:53
 */
public class SerializationUtil {

    public static byte[] write(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    public static <T> T read(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }

    public static void main(String[] args) {
        Staff staff = new Staff();
        staff.setName("seven");
        staff.setId(1);
        staff.setAccumulationFund(new BigDecimal("100.0"));
        System.out.println(staff);
        byte[] objectData = write(staff);
        Staff staff2 = read(objectData);
        System.out.println(staff2);
    }

    private SerializationUtil() {
        //工具类不允许实例化
    }

}
