package me.ianhe.controller;

import me.ianhe.db.entity.Article;
import me.ianhe.service.JMSProducerService;
import me.ianhe.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController extends BaseController {

    @Autowired
    private JMSProducerService producerService;

    @Autowired
    @Qualifier("articleQueue")
    private Destination destination;

    @RequestMapping(value = "post/{id}", method = RequestMethod.GET)
    public String articlePage(@PathVariable Integer id, Model model) {
        Article article;
        if (id == null || id == 0) {
            List<Article> articles = articleManager.listByCondition(null, 0, 1);
            article = articles.get(0);
        } else {
            article = articleManager.selectArticleById(id);
        }
        if (article != null) {
            producerService.sendMessage(destination, String.valueOf(article.getId()));
            model.addAttribute("article", article);
        }
        return "article";
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
        logger.info("Browser's user-agent: " + request.getHeader("User-Agent"));
        logger.info("remote ip:" + RequestUtil.getRealIp(request));
        logger.info("music...");
        return "music";
    }

    @GetMapping("admin/fi/summary")
    public String summary() {
        return "admin/fi_summary";
    }

    @GetMapping("admin/fi/customer")
    public String customer() {
        return "admin/fi_customer";
    }

    @GetMapping("admin/fi/analysis")
    public String analysis() {
        return "admin/fi_analysis";
    }

}
