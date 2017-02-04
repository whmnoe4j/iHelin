package me.ianhe.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminIndexController extends BaseAdminController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String indexPage(Model model) {
        int userCount = userManager.listUserCount(null, null);
        model.addAttribute("userCount", userCount);
        return ftl("index");
    }
}
