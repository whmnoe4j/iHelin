package me.ianhe.controller.admin;

import me.ianhe.controller.BaseController;
import me.ianhe.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class BaseAdminController extends BaseController {

    protected static final String NAMESPACE = "admin";
    public static final String SESSION_KEY_ADMIN = "adminUser";

    @Autowired
    protected FinanceService financeService;

    @Override
    protected String ftl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
