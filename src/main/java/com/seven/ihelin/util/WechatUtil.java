package com.seven.ihelin.util;

import com.google.common.collect.Lists;
import com.seven.ihelin.model.*;
import com.seven.ihelin.req.LocationMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by iHelin on 16/11/4.
 */
public class WechatUtil {

    private static final String MEDIA_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
    private static final String GET_MEDIA_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
    private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    // get请求，返回String
    public static String doGetStr(String url) {
        HttpGet httpGet = new HttpGet(url);
        String result = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpResponse httpResponse = client.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    // post请求
    public static String doPostStr(String url, String outStr) {
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpEntity reqEntity = new StringEntity(outStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(reqEntity);
            HttpResponse response = client.execute(httpPost);
            HttpEntity respEntity = response.getEntity();
            result = EntityUtils.toString(respEntity, "UTF-8");
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    // 获取微信access_token
    public WXAccessToken getAccessToken(String appid, String secret) {
        String url = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", secret);
        String res = doGetStr(url);
        WXAccessToken token = JSON.parseObject(res, WXAccessToken.class);
        LOGGER.info("从微信服务器获取token成功，有效期为" + token.getExpires_in() + "秒");
        return token;
    }


    // 组装菜单
    public static Menu initMenu() {
        Menu menu = new Menu();
        List<Button> buttons = Lists.newArrayList();
        ClickButton button11 = new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");
        buttons.add(button11);

        ViewButton button2 = new ViewButton();
        button2.setName("官方网站");
        button2.setType("view");
        button2.setUrl("http://www.tcqcw.cn");
        buttons.add(button2);

        List<Button> subButtons = Lists.newArrayList();
        ClickButton button31 = new ClickButton();
        button31.setName("扫码");
        button31.setType("scancode_push");
        button31.setKey("31");
        subButtons.add(button31);

        ClickButton button32 = new ClickButton();
        button32.setName("地理位置");
        button32.setType("location_select");
        button32.setKey("32");
        subButtons.add(button32);

        Button button = new Button();
        button.setName("新菜单");
        button.setSub_button(subButtons);
        buttons.add(button);

        menu.setButton(buttons);
        return menu;
    }

    // 创建菜单
    public static String createMenu(String accessToken, String menu) {
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
        return doPostStr(url, menu);
    }

    // 查询菜单
    public static String queryMenu(String token) {
        String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
        return doGetStr(url);
    }

    // 删除菜单
    public static String deleteMenu(String token) {
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
        return doGetStr(url);
    }

    // 组装地理位置消息
    public static LocationMessage MapToLocation(Map<String, String> map) {
        LocationMessage location = new LocationMessage();
        location.setLabel(map.get("Label"));
        location.setLocation_X(map.get("Location_X"));
        location.setLocation_Y(map.get("Location_Y"));
        location.setScale(Integer.parseInt(map.get("Scale")));
        location.setMsgId(Long.valueOf(map.get("MsgId")));
        return location;
    }

}
