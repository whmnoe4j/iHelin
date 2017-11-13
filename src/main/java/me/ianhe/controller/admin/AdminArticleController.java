package me.ianhe.controller.admin;

import me.ianhe.db.entity.Article;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 文章管理
 *
 * @author iHelin
 * @since 2017/8/28 13:26
 */
@RestController
public class AdminArticleController extends BaseAdminController {

    /**
     * 新增文章
     *
     * @author iHelin
     * @since 2017/8/28 13:26
     */
    @PostMapping("article")
    public String addArticle(Article article) {
        articleService.addArticle(article);
        return success();
    }

    @GetMapping("articleList")
    public List<Article> getArticles(Integer pageNum, Integer pageSize) {
        List<Article> articles = articleService.listByCondition(null, pageNum, pageSize);
        return articles;
    }

    /**
     * 编辑文章
     *
     * @author iHelin
     * @since 2017/8/28 13:28
     */
    @PutMapping(value = "article")
    public String editArticle(Article article) {
        if (article == null || article.getId() == null) {
            return error("文章不存在");
        }
        Article newArticle = articleService.selectArticleById(article.getId());
        newArticle.setTitle(HtmlUtils.htmlEscape(article.getTitle(), CharEncoding.UTF_8));
        newArticle.setSummary(HtmlUtils.htmlEscape(article.getSummary(), CharEncoding.UTF_8));
        newArticle.setContent(article.getContent());
        articleService.editArticle(newArticle);
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
        articleService.deleteById(articleId);
        return success();
    }

}
