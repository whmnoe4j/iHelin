package com.seven.ihelin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "admin")
public class AdminIndexController extends BaseAdminController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String indexPage() {
        return ftl("index");
    }
}
