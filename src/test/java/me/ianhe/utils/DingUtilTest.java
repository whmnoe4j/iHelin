package me.ianhe.utils;

import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by iHelin on 17/4/23.
 */
public class DingUtilTest {

    @Test
    public void say() throws Exception {
        String res = DingUtil.say("测试，勿回！");
        Assert.assertNotNull(res);
    }

    @Test
    public void atTest() throws Exception {
        Map<String, Object> contentMap = Maps.newHashMap();
        contentMap.put("content", "根本无心上班，一心想为祖国母亲庆生！");
        Map<String, Object> data = Maps.newHashMap();
        data.put("msgtype", "text");
        data.put("text", contentMap);
        Map<String, Object> atMobiles = Maps.newHashMap();
        String[] mobiles = new String[]{"15395551819"};
        atMobiles.put("atMobiles", mobiles);
        data.put("at", atMobiles);
        System.out.println(DingUtil.doSay(JsonUtil.toJson(data)));
    }

}