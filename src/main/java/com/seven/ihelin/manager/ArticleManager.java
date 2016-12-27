package com.seven.ihelin.manager;

import com.seven.ihelin.db.entity.Article;
import com.seven.ihelin.db.mapper.ArticleMapper;
import com.seven.ihelin.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 文章管理
 * PackageName:   com.seven.ihelin.manager
 * ClassName:     ArticleManager
 * Description:
 * Date           16/12/27
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Service
public class ArticleManager {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ArticleMapper articleMapper;

    public int addArticle(Article article) {
        article.setAuthor("Ian He");
        article.setCreateTime(new Date());
        logger.info("create a new article:{}", JSON.toJson(article));
        return articleMapper.insert(article);
    }
}
