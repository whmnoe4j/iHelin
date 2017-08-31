package me.ianhe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminIndexController extends BaseAdminController {

    @GetMapping("index")
    public String indexPage(Model model) {
        int userCount = userManager.listUserCount(null, null);
        model.addAttribute("userCount", userCount);
        return ftl("index");
    }

    @GetMapping("login")
    public String loginPage() {
        return ftl("login");
    }
}
