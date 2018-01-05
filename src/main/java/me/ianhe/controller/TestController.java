package me.ianhe.controller;

import me.ianhe.entity.Advice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/12/20 15:12
 */
@RestController
public class TestController extends BaseController {

    @PostMapping("ws")
    public String sendMessage(@RequestBody Map<String, String> data) {
        webSocket.sendMessage(data.get("data"));
        return success();
    }

    @PostMapping("advice")
    public String sendAdvice(String name, String phone, String email, String message) {
        Advice advice = new Advice();
        advice.setName(name);
        advice.setPhone(phone);
        advice.setEmail(email);
        advice.setMessage(message);
        advice.setCreateTime(new Date());
        articleService.addAdvice(advice);
        return success();
    }
}
