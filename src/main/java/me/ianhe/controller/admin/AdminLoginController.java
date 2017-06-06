package me.ianhe.controller.admin;

import com.google.code.kaptcha.Constants;
import me.ianhe.model.AdminUser;
import me.ianhe.utils.Global;
import me.ianhe.utils.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class AdminLoginController extends BaseAdminController {

    private static final String ERROR_MSG = "error";
    private static final String FROM = "from";

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage(Model model, String from) {
        model.addAttribute("from", from);
        return "admin/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String username, String password, String captcha, String from, HttpServletRequest request,
                        HttpSession session, Model model) {
        if (StringUtils.isEmpty(captcha)) {
            model.addAttribute(ERROR_MSG, "请填写验证码！");
            model.addAttribute(FROM, from);
            return ftl("login");
        }
        String sessionCaptcha = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (!captcha.equalsIgnoreCase(sessionCaptcha)) {
            model.addAttribute(ERROR_MSG, "验证码不正确！");
            model.addAttribute("from", from);
            return ftl("login");
        }
        if (username.equals(Global.getAdminUser()) && password.equals(Global.getAdminPassword())) {
            AdminUser adminUser = new AdminUser();
            adminUser.setAdminId(username);
            adminUser.setNickName("Ian He");
            adminUser.setLastLoginTime(new Date());
            String rip = RequestUtil.getRealIp(request);
            adminUser.setLastLoginIp(rip);
            session.setAttribute(SESSION_KEY_ADMIN, adminUser);
            logger.info("Admin user: {} login success,ip : {}.", username, rip);
            if (StringUtils.isNotEmpty(from))
                return "redirect:" + from;
            return "redirect:/admin/index";
        }
        model.addAttribute(ERROR_MSG, "用户名或密码不正确！");
        model.addAttribute("from", from);
        return ftl("login");
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute(SESSION_KEY_ADMIN);
        return "redirect:login";
    }

}
