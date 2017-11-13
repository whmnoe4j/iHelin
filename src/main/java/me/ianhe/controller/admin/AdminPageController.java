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
    @GetMapping(value = {"", "index"})
    public String adminIndex() {
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

    @GetMapping(value = "users")
    public String users() {
        return ftl("users");
    }

    @GetMapping(value = "articles")
    public String articles() {
        return ftl("articles");
    }

    @GetMapping("image")
    public String imagePage() {
        return ftl("image");
    }

    /**
     * 文件上传页面
     *
     * @author iHelin
     * @since 2017/11/13 17:48
     */
    @GetMapping(value = "upload")
    public String upload() {
        return ftl("upload");
    }

}
