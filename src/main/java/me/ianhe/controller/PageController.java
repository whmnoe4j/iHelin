package me.ianhe.controller;

import me.ianhe.entity.Article;
import me.ianhe.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author iHelin
 * @since 2017/11/14 16:47
 */
@Controller
public class PageController extends BaseController {

    @Autowired
    @Qualifier("articleQueue")
    private Destination destination;

    @GetMapping(value = {"", "index"})
    public String indexPage(Model model, @RequestHeader("User-Agent") String userAgent) {
        int pageLength = 5;
        int pageNum = 1;
        List<Article> articles = articleService.listByCondition(null, pageNum, pageLength);
        model.addAttribute("articles", articles);
        logger.debug("userAgent:{}", userAgent);
        return "index";
    }

    @GetMapping("article/{id}")
    public String articlePage(@PathVariable Integer id, Model model) {
        Article article;
        if (id == null || id == 0) {
            List<Article> articles = articleService.listByCondition(null, 0, 1);
            article = articles.get(0);
        } else {
            article = articleService.selectArticleById(id);
        }
        if (article != null) {
            producerService.sendMessage(destination, String.valueOf(article.getId()));
            model.addAttribute("article", article);
        }
        return "article";
    }

    @GetMapping("about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("music")
    public String musicPage() {
        return "music";
    }

    @GetMapping("ws")
    public String webSocketPage(HttpServletRequest request, Model model) {
        model.addAttribute("serverName", RequestUtil.getDomain(request));
        return "webSocket";
    }

    @ResponseBody
    @PostMapping("ws")
    public String sendMessage(@RequestBody Map<String, String> data) {
        System.out.println(data.get("data"));
        webSocket.sendMessage(data.get("data"));
        return success();
    }

}
