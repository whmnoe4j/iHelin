package me.ianhe.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riversoft.weixin.common.AccessToken;
import com.riversoft.weixin.common.util.JsonMapper;
import me.ianhe.db.entity.Staff;
import me.ianhe.utils.JSON;
import org.junit.Test;

import java.net.URL;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/6/21 17:44
 */
public class JacksonTest {

    @Test
    public void test() {
        Staff staff = new Staff();
        staff.setId(1);
        staff.setName("张三");
        System.out.println(JSON.toJson(staff));
    }
}
