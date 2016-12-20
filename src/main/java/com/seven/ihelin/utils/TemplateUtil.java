package com.seven.ihelin.utils;

import com.google.common.collect.Maps;
import com.seven.ihelin.config.CommonConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateUtil.class);

    public static String applyTemplate(String templatePath) {
        Map<String, Object> res = Maps.newHashMap();
        String result = null;
        try {
            result = applyTemplate(templatePath, res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String applyTemplate(String templatePath, Map<String, Object> propMap) throws IOException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        File dir = new File(CommonConfig.getWebInfDir(), "templates");
        config.setDirectoryForTemplateLoading(dir);
        Template template = config.getTemplate(templatePath, "UTF-8");
        StringWriter writer = new StringWriter();
        try {
            template.process(propMap, writer);
        } catch (TemplateException e) {
            LOGGER.warn("Error while process template: " + templatePath, e);
        }
        return writer.toString();
    }

    public static String applyTemplateSimple(String templatePath, Map<String, Object> propMap) {
        propMap.put("contextUrl", CommonConfig.getContextUrl());
        try {
            return applyTemplate(templatePath, propMap);
        } catch (IOException e) {
            LOGGER.warn("Error while process template: " + templatePath, e);
        }
        return null;
    }
}
