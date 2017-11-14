package me.ianhe.service;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import me.ianhe.utils.RequestUtil;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringWriter;
import java.util.Map;

/**
 * freemarker模板方法
 * 用模板中返回HTML字符串
 *
 * @author iHelin
 * @since 2017/11/14 17:35
 */
@Service
public class TemplateService {

    @Autowired
    private Global global;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String applyTemplate(String templateName) {
        Map<String, Object> res = Maps.newHashMap();
        return applyTemplate(templateName, res);
    }

    public String applyTemplate(String templateName, Map<String, Object> propMap) {
        propMap.put("contextPath", RequestUtil.getRequest().getContextPath());
        Configuration config = new Configuration(Configuration.VERSION_2_3_23);
        File dir = new File(Global.getClassPath(), global.getValue("mail.tpl"));
        try {
            config.setDirectoryForTemplateLoading(dir);
            Template template = config.getTemplate(templateName, CharEncoding.UTF_8);
            StringWriter writer = new StringWriter();
            template.process(propMap, writer);
            return writer.toString();
        } catch (Exception e) {
            logger.warn("Error while process template：{} ", templateName, e);
            return "";
        }
    }

}
