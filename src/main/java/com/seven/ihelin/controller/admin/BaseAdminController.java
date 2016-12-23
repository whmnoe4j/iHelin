package com.seven.ihelin.controller.admin;

import com.seven.ihelin.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class BaseAdminController extends BaseController {
    protected static final String NAMESPACE = "admin";
    public static final String SESSION_KEY_ADMIN = "adminUser";

    protected String ftl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
