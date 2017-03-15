package me.ianhe.controller.admin;

import me.ianhe.db.entity.Article;
import me.ianhe.db.plugin.Pagination;
import me.ianhe.utils.JSON;
import me.ianhe.utils.ResponseUtil;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文章管理Controller
 * PackageName:   com.seven.ianhe.controller.admin
 * ClassName:     AdminArticleController
 * Description:
 * Date           16/12/26
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Controller
public class AdminArticleController extends BaseAdminController {

    @RequestMapping(value = "article", method = RequestMethod.GET)
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
     * @return
     */
    @RequestMapping(value = "article/add", method = RequestMethod.GET)
    public String articleAddPage() {
        return ftl("article_add");
    }

    /**
     * 编辑文章页面
     *
     * @param articleId
     * @param model
     * @return
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
     * @param article
     * @param response
     */
    @RequestMapping(value = "article", method = RequestMethod.POST)
    public void addArticle(Article article, HttpServletResponse response) {
        articleManager.addArticle(article);
        ResponseUtil.writeSuccessJSON(response);
    }

    /**
     * 编辑文章
     *
     * @param article
     * @param response
     */
    @RequestMapping(value = "article/edit", method = RequestMethod.POST)
    public void editArticle(Article article, HttpServletResponse response) {
        logger.info("article is {}", JSON.toJson(article));
        if (article == null || article.getId() == null) {
            logger.error("no article found {}", article);
            ResponseUtil.writeFailedJSON(response, "文章不存在！");
            return;
        }
        Article newArticle = articleManager.selectArticleById(article.getId());
        newArticle.setTitle(HtmlUtils.htmlEscape(article.getTitle(), CharEncoding.UTF_8));
        newArticle.setSummary(HtmlUtils.htmlEscape(article.getSummary(), CharEncoding.UTF_8));
        newArticle.setContent(article.getContent());
        articleManager.editArticle(newArticle);
        ResponseUtil.writeSuccessJSON(response);
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @param response
     */
    @RequestMapping(value = "article/{articleId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable Integer articleId, HttpServletResponse response) {
        articleManager.deleteById(articleId);
        ResponseUtil.writeSuccessJSON(response);
    }

}
