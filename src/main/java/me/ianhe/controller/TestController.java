package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import com.google.code.kaptcha.Constants;
import me.ianhe.model.Result;
import me.ianhe.utils.CryptUtil;
import me.ianhe.utils.JSON;
import me.ianhe.utils.MailUtil;
import me.ianhe.utils.ResponseUtil;
import me.ianhe.utils.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

@Controller
public class TestController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "tpl", method = RequestMethod.GET)
    public String mail() throws MessagingException {
        String template = TemplateUtil.applyTemplate("mail_content.ftl");
//        mimeMail.sendMail("ihelin@outlook.com", "哈哈", template);
        MailUtil.sendMail("ihelin@outlook.com", "iHelin", "哈哈", template);
        return template;
    }

    @ResponseBody
    @RequestMapping(value = "console", method = RequestMethod.GET)
    public String getProperties() {
        Properties props = System.getProperties();
        return JSON.toJson(props);
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        Result result = new Result();
        result.setData("<h1>三个人请问abc123</h1>");
        return JSON.toJson(result);
    }

    @RequestMapping(value = "test1", method = RequestMethod.GET)
    public void test1(HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, Object> data = Maps.newHashMap();
        data.put("data", "<h1>三个人请问abc123</h1>");
        ResponseUtil.writeSuccessJSON(response, data);
    }

}
