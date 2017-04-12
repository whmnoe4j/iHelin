package me.ianhe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author iHelin
 * @create 2017-04-01 16:56
 */
public class Global {

    private static final String CONFIG_NAME = "config.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(Global.class);
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(Global.class.getClassLoader().getResourceAsStream(CONFIG_NAME));
        } catch (IOException e) {
            LOGGER.error("读取配置文件：" + CONFIG_NAME + "错误！", e);
        }
    }

    public static String getAppId() {
        return getValue("wx.appId");
    }

    public static String getAppSecret() {
        return getValue("wx.appSecret");
    }

    public static String getAdminUser() {
        return getValue("adminUser");
    }

    public static String getAdminPassword() {
        return getValue("adminPassword");
    }

    public static String getDomainUrl() {
        return getValue("domainUrl");
    }

    public static String getSystemName() {
        return getValue("systemName");
    }

    public static String getWXToken() {
        return getValue("wx.token");
    }

    public static File getClassPath() {
        return new File(Global.class.getResource("/").getFile());
    }

    /**
     * 根据key得到value的值
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(getAdminPassword());
    }

}
