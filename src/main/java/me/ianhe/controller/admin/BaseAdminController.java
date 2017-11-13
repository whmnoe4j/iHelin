package me.ianhe.controller.admin;

import me.ianhe.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseAdminController
 *
 * @author iHelin
 * @since 2017/10/17 15:27
 */
@RequestMapping("admin")
public abstract class BaseAdminController extends BaseController {

    protected static final String NAMESPACE = "admin";

    @Override
    protected String ftl(String ftlFileName) {
        return NAMESPACE + "/" + ftlFileName;
    }

}
