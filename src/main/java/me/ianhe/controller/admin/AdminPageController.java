package me.ianhe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author iHelin
 * @since 2017/8/31 21:48
 */
@Controller
public class AdminPageController extends BaseAdminController {

    /**
     * 前端项目
     *
     * @author iHelin
     * @since 2017/9/17 10:36
     */
    @GetMapping("")
    public String admin() {
        return ftl("index");
    }

    /**
     * 控制台首页
     *
     * @author iHelin
     * @since 2017/8/31 22:23
     */
    @GetMapping("index")
    public String indexPage() {
        return "redirect:/admin";
    }

    /**
     * 后台登录页
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping("login")
    public String loginPage() {
        return ftl("login");
    }

    /**
     * 二维码页面
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @GetMapping(value = "qrcode")
    public String qRCode() {
        return ftl("qrcode");
    }

}
