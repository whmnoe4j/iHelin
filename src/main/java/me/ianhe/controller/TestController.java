package me.ianhe.controller;

import me.ianhe.utils.RequestUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author iHelin
 * @since 2017/12/20 15:12
 */
@RestController
public class TestController extends BaseController {

    @GetMapping("forum.php")
    public String handleX(HttpServletRequest request, @RequestHeader("User-Agent") String userAgent) {
        logger.warn("ip是：{},userAgent:{}", RequestUtil.getRealIp(request), userAgent);
        return "我有一句妈卖批不值当讲不当讲";
    }
}
