package me.ianhe.controller;

import me.ianhe.db.entity.Article;
import me.ianhe.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/")
    public String defaultPage() {
        return "forward:/home";
    }

    @Transactional
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String indexPage(Model model) {
        int PAGE_LENGTH = 5;
        int pageNum = 1;
        List<Article> articles = articleManager.listByCondition(null, (pageNum - 1) * PAGE_LENGTH, PAGE_LENGTH);
        model.addAttribute("articles", articles);
        return "index";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public String postPage(@PathVariable Integer id, Model model) {
        Article article = null;
        if (id == null || id == 0) {
            List<Article> articles = articleManager.listByCondition(null, 0, 1);
            article = articles.get(0);

        } else {
            article = articleManager.selectArticleById(id);
        }
        if(article==null){
            return "post";
        }
        articleManager.addReadCount(article);
        model.addAttribute("article", article);
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
