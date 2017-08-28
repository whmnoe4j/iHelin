package me.ianhe.controller.admin;

import me.ianhe.db.entity.Article;
import me.ianhe.db.plugin.Pagination;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 文章管理
 *
 * @author iHelin
 * @since 2017/8/28 13:26
 */
@Controller
public class AdminArticleController extends BaseAdminController {

    /**
     * 文章管理页
     *
     * @author iHelin
     * @since 2017/8/28 13:24
     */
    @GetMapping(value = "article")
    public String articleAdminPage(Model model, String title, Integer pageNum) {
        if (pageNum == null)
            pageNum = 1;
        List<Article> articles = articleManager.listByCondition(title,
                (pageNum - 1) * DEFAULT_PAGE_LENGTH, DEFAULT_PAGE_LENGTH);
        int totalCount = articleManager.listCount(title);
        model.addAttribute("title", title);
        model.addAttribute("articles", articles);
        model.addAttribute("pagination", new Pagination(totalCount, pageNum, DEFAULT_PAGE_LENGTH));
        return ftl("article");
    }

    /**
     * 新增文章页面
     *
     * @author iHelin
     * @since 2017/8/28 13:25
     */
    @RequestMapping(value = "article/add", method = RequestMethod.GET)
    public String articleAddPage() {
        return ftl("article_add");
    }

    /**
     * 编辑文章页面
     *
     * @author iHelin
     * @since 2017/8/28 13:26
     */
    @RequestMapping(value = "article/edit/{articleId}", method = RequestMethod.GET)
    public String articleEditPage(@PathVariable Integer articleId, Model model) {
        Article article = articleManager.selectArticleById(articleId);
        model.addAttribute("article", article);
        return ftl("article_edit");
    }

    /**
     * 新增文章
     *
     * @author iHelin
     * @since 2017/8/28 13:26
     */
    @ResponseBody
    @PostMapping("article")
    public String addArticle(Article article) {
        articleManager.addArticle(article);
        return success();
    }

    /**
     * 编辑文章
     *
     * @author iHelin
     * @since 2017/8/28 13:28
     */
    @PutMapping(value = "article")
    public String editArticle(Article article) {
        logger.info("article is {}", article);
        if (article == null || article.getId() == null) {
            return error("文章不存在");
        }
        Article newArticle = articleManager.selectArticleById(article.getId());
        newArticle.setTitle(HtmlUtils.htmlEscape(article.getTitle(), CharEncoding.UTF_8));
        newArticle.setSummary(HtmlUtils.htmlEscape(article.getSummary(), CharEncoding.UTF_8));
        newArticle.setContent(article.getContent());
        articleManager.editArticle(newArticle);
        return success();
    }

    /**
     * 删除文章
     *
     * @author iHelin
     * @since 2017/8/28 13:29
     */
    @DeleteMapping(value = "article/{articleId}")
    public String deleteProduct(@PathVariable Integer articleId) {
        articleManager.deleteById(articleId);
        return success();
    }

}
