package com.seven.ihelin.controller;

import com.seven.ihelin.util.JSON;
import com.seven.ihelin.util.MailUtil;
import com.seven.ihelin.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Controller
public class TestController extends BaseController {

    @RequestMapping("git")
    public void git(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inStream = request.getInputStream();
        InputStreamReader inStreamReader = new InputStreamReader(inStream);
        BufferedReader br = new BufferedReader(inStreamReader);
        StringBuffer sb = new StringBuffer();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        String res = sb.toString();
        Object obj = JSON.parseObject(res, Object.class);
        ResponseUtil.writeSuccessJSON(response);
    }

    @RequestMapping("mail")
    public String error() {
        MailUtil.sendMail("ihelin@outlook.com", "iHelin", "哈哈", "这里是内容");
        return "error";
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

}
