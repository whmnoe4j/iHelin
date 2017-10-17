package me.ianhe.controller.admin;

import me.ianhe.controller.BaseController;
import me.ianhe.service.FinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseAdminController
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
@RequestMapping("admin")
public class BaseAdminController extends BaseController {

    protected static final String NAMESPACE = "admin";

    @Autowired
    protected FinanceService financeService;

    @Override
    protected String ftl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
