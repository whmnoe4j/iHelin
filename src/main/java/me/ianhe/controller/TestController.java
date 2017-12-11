package me.ianhe.controller;

import me.ianhe.wechat.core.Core;
import me.ianhe.wechat.core.MsgCenter;
import me.ianhe.wechat.utils.WeChatTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author iHelin
 * @since 2017/12/11 19:46
 */
@RestController
@RequestMapping("wechat")
public class TestController extends BaseController {

    private static Core core = Core.getInstance();

    /**
     * 登录
     *
     * @param response
     * @throws IOException
     */
    @GetMapping("login")
    public void weChatLogin(HttpServletResponse response) throws IOException {
        if (!core.isAlive()) {
            weChatLoginService.login(response.getOutputStream());
            logger.info("+++++++++++++++++++开始消息处理+++++++++++++++++++++");
            new Thread(() -> MsgCenter.handleMsg(messageHandler), "msg-thread").start();
        } else {
            response.getWriter().print("微信已在线，请注销后重试");
        }
    }

    /**
     * 注销
     *
     * @return
     */
    @GetMapping("logout")
    public Boolean weChatLogout() {
        WeChatTools.logout();
        return core.isAlive();
    }

    @GetMapping("status")
    public Boolean getStatus() {
        return core.isAlive();
    }

}
