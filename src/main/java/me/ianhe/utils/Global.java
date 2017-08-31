package me.ianhe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author iHelin
 * @create 2017-04-01 16:56
 */
@Component
public class Global {

    private static final String CONFIG_NAME = "config.properties";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Properties properties;

    @PostConstruct
    private void init() {
        logger.debug("init properties method");
        properties = new Properties();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(CONFIG_NAME);
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            logger.error("读取配置文件：" + CONFIG_NAME + "错误！", e);
        }
    }

    public String getAppId() {
        return getValue("wx.appId");
    }

    public String getAppSecret() {
        return getValue("wx.appSecret");
    }

    public String getDomainUrl() {
        return getValue("domainUrl");
    }

    public String getSystemName() {
        return getValue("systemName");
    }

    public String getWXToken() {
        return getValue("wx.token");
    }

    public String getMailTpl() {
        return getValue("mail.tpl");
    }

    public static File getClassPath() {
        return new File(Global.class.getResource("/").getFile());
    }

    /**
     * 根据key得到value的值
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key) {
        Properties properties = new Properties();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(CONFIG_NAME);
        try {
            properties.load(resource.getInputStream());
            return properties.getProperty(key);
        } catch (IOException e) {
            return null;
        }
    }

}
