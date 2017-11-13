package me.ianhe.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

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
    private static final Logger logger = LoggerFactory.getLogger(Global.class);
    private static Properties properties;

    static {
        properties = new Properties();
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(CONFIG_NAME);
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            logger.error("读取配置文件：" + CONFIG_NAME + "出错！", e);
        }
    }

    public static String getDomainUrl() {
        return get("domainUrl");
    }

    public static String getSystemName() {
        return get("systemName");
    }

    public static String getMailTpl() {
        return get("mail.tpl");
    }

    public static File getClassPath() {
        return new File(Global.class.getResource("/").getFile());
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public String getValue(String key) {
        return get(key);
    }

}
