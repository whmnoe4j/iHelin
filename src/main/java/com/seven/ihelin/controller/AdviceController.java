package com.seven.ihelin.controller;

import com.seven.ihelin.db.entity.Advice;
import com.seven.ihelin.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class AdviceController extends BaseController {

    @RequestMapping(value = "advice", method = RequestMethod.POST)
    public void advice(String name, String email, String message, HttpServletResponse response) {
        Advice advice = new Advice();
        advice.setName(name);
        advice.setEmail(email);
        advice.setMessage(message);
        advice.setCreateTime(new Date());
        adviceManager.insertMessage(advice);
        ResponseUtil.writeSuccessJSON(response);
    }
}
