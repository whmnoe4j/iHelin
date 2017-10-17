package me.ianhe.controller.admin;

import me.ianhe.security.MyInvocationSecurityMetadataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iHelin
 * @since 2017/8/31 22:26
 */
@RestController
@RequestMapping("admin/system")
public class SystemController extends BaseAdminController {

    @Autowired
    private MyInvocationSecurityMetadataSource securityMetadataSource;

    @PostMapping("authRole")
    public String loadAuthRole() {
        return success();
    }

}
