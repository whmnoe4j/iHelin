package me.ianhe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author iHelin
 * @since 2017/8/31 21:48
 */
@Controller
public class AdminPageController extends BaseAdminController {

    /**
     * 控制台首页
     *
     * @author iHelin
     * @since 2017/8/31 22:23
     */
    @GetMapping("index")
    public String indexPage(Model model) {
        int userCount = userManager.listUserCount(null, null);
        model.addAttribute("userCount", userCount);
        return ftl("index");
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
