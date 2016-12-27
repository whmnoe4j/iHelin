package com.seven.ihelin.manager;

import com.google.common.collect.Maps;
import com.seven.ihelin.db.entity.Article;
import com.seven.ihelin.db.mapper.ArticleMapper;
import com.seven.ihelin.utils.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        Date now = new Date();
        article.setCreateTime(now);
        article.setUpdateTime(now);
        logger.info("create a new article:{}", JSON.toJson(article));
        return articleMapper.insert(article);
    }

    public int editArticle(Article article) {
        article.setUpdateTime(new Date());
        logger.info("update article:{}", JSON.toJson(article));
        return articleMapper.updateByPrimaryKey(article);
    }

    public Article selectArticleById(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    public List<Article> listByCondition(String title, int offset, int size) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(title))
            res.put("title", title);
        return articleMapper.listByCondition(res, new RowBounds(offset, size));
    }

    public int listCount(String title) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(title))
            res.put("title", title);
        return articleMapper.listCount(res);
    }

    public int deleteById(Integer id) {
        return articleMapper.deleteByPrimaryKey(id);
    }
}
