package me.ianhe.controller.admin;

import me.ianhe.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class BaseAdminController extends BaseController {

    protected static final String NAMESPACE = "admin";
    public static final String SESSION_KEY_ADMIN = "adminUser";

    @Override
    protected String ftl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
