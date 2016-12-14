package com.seven.ihelin.controller;

import com.seven.ihelin.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String defaultPage() {
        return "redirect:/home";
    }

    @RequestMapping(value = {"index"}, method = RequestMethod.GET)
    public String indexPage() {
        return "index";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public String postDoPage() {
        return "post";
    }

    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String aboutPage() {
        return "about";
    }

    @RequestMapping(value = "contact", method = RequestMethod.GET)
    public String contactPage() {
        return "contact";
    }

    @RequestMapping(value = "music", method = RequestMethod.GET)
    public String musicPage(HttpServletRequest request) {
        LOGGER.info("Browser's user-agent: " + request.getHeader("User-Agent"));
        LOGGER.info("remote ip:" + RequestUtil.getRealIp(request));
        LOGGER.info("music...");
        return "music";
    }

}
