package me.ianhe.controller;

import me.ianhe.db.entity.Article;
import me.ianhe.utils.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author iHelin
 * @create 2017-03-15 19:36
 */
@Controller
public class IndexController extends BaseController {

    @RequestMapping(value = "/")
    public String defaultPage() {
        logger.info("forward to index");
        return "forward:/index";
    }

    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String indexPage(Model model) {
        int PAGE_LENGTH = 5;
        int pageNum = 1;
        List<Article> articles = articleManager.listByCondition(null, (pageNum - 1)
                * PAGE_LENGTH, PAGE_LENGTH);
        model.addAttribute("articles", articles);
        return "index";
    }

    @RequestMapping(value = "config", method = RequestMethod.GET)
    public String configPage(Model model) {
        Properties props = System.getProperties();
        model.addAttribute("props", props);
        return "config";
    }

    @RequestMapping(value = "image", method = RequestMethod.GET)
    public String imagePage(Model model) {
        List<Map<String, Object>> fileInfos = FileUtil.getFileList();
        model.addAttribute("fileInfos", fileInfos);
        return "image";
    }

}
