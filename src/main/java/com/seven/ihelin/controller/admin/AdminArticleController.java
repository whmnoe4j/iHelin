package com.seven.ihelin.controller.admin;

import com.seven.ihelin.db.entity.Article;
import com.seven.ihelin.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * PackageName:   com.seven.ihelin.controller.admin
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
    public String articleAdminPage() {
        return ftl("article");
    }

    @RequestMapping(value = "article/add", method = RequestMethod.GET)
    public String articleAddPage() {
        return ftl("article_add");
    }

    @RequestMapping(value = "article", method = RequestMethod.POST)
    public void addArticle(Article article, HttpServletResponse response) {
        articleManager.addArticle(article);
        ResponseUtil.writeSuccessJSON(response);
    }

}
