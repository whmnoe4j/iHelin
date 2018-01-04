package me.ianhe.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
