package me.ianhe.utils;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import me.ianhe.config.CommonConfig;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

/**
 * freemarker模板方法
 * 用模板中返回HTML字符串
 */
public class TemplateUtil {

    private static final String TEMPLATE_DIR = Global.getMailTpl();
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtil.class);

    public static String applyTemplate(String templatePath) {
        Map<String, Object> res = Maps.newHashMap();
        String result = applyTemplate(templatePath, res);
        return result;
    }

    public static String applyTemplate(String templatePath, Map<String, Object> propMap) {
        try {
            propMap.put("contextPath", CommonConfig.getContextPath());
            Configuration config = new Configuration(Configuration.VERSION_2_3_23);
            File dir = new File(Global.getClassPath(), TEMPLATE_DIR);
            config.setDirectoryForTemplateLoading(dir);
            Template template = config.getTemplate(templatePath, CharEncoding.UTF_8);
            StringWriter writer = new StringWriter();
            template.process(propMap, writer);
            return writer.toString();
        } catch (Exception e) {
            LOGGER.warn("Error while process template: " + templatePath, e);
            return "";
        }
    }

    private TemplateUtil() {
        //工具类不允许实例化
    }
}
