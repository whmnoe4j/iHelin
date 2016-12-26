package com.seven.ihelin.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String ArticleAdminPage() {
        return ftl("article");
    }

    @RequestMapping(value = "article/add", method = RequestMethod.GET)
    public String ArticleAddPage() {
        return ftl("article_add");
    }

}
