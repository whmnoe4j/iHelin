package me.ianhe.controller.admin;

import me.ianhe.config.CommonConfig;
import me.ianhe.model.AdminUser;
import me.ianhe.utils.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class AdminLoginController extends BaseAdminController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(Model model, String from) {
        model.addAttribute("from", from);
        return "admin/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, String from, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session, Model model) {
        if (username.equals(CommonConfig.getAdminUser()) && password.equals(CommonConfig.getAdminPassword())) {
            AdminUser adminUser = new AdminUser();
            adminUser.setAdminId(username);
            adminUser.setNickName("Ian He");
            adminUser.setLastLoginTime(new Date());
            String rip = RequestUtil.getRealIp(request);
            adminUser.setLastLoginIp(rip);
            session.setAttribute(SESSION_KEY_ADMIN, adminUser);
//            Cookie cookie = new Cookie("ihelin", UUID.randomUUID().toString());
//            cookie.setMaxAge(60 * 60 * 24 * 7);
//            response.addCookie(cookie);
            logger.info("Admin user: {} login success,ip : {}.", username, rip);
            if (StringUtils.isNotEmpty(from))
                return "redirect:" + from;
            return "redirect:/admin/index";
        }
        model.addAttribute("error", "用户名密码不正确！");
        model.addAttribute("from", from);
        return ftl("login");
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_KEY_ADMIN);
        return "redirect:login";
    }

}
