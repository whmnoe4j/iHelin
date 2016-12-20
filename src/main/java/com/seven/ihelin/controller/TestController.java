package com.seven.ihelin.controller;

import com.seven.ihelin.utils.CryptUtil;
import com.seven.ihelin.utils.JSON;
import com.seven.ihelin.utils.MailUtil;
import com.seven.ihelin.utils.TemplateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@Controller
public class TestController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "mail", method = RequestMethod.GET)
    public String error() {
        String template = TemplateUtil.applyTemplate("/mail/mail_content.ftl");
        MailUtil.sendMail("ihelin@outlook.com", "iHelin", "哈哈", template);
        return template;
    }

    @RequestMapping(value = "config", method = RequestMethod.GET)
    public String configPage(Model model) {
        Properties props = System.getProperties();
        model.addAttribute("props", props);
        return "config";
    }

    @ResponseBody
    @RequestMapping(value = "console", produces = {"application/json"}, method = RequestMethod.GET)
    public String getProperties() {
        Properties props = System.getProperties();
        return JSON.toJson(props);
    }

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return "中国三个人请问";
    }

    public static void main(String[] args) {
        String s = "123456";
        System.out.println(CryptUtil.md5(s));
        System.out.println(DigestUtils.md5DigestAsHex(s.getBytes()));
    }

}
