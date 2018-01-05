package me.ianhe.service;

import com.google.common.collect.Maps;
import me.ianhe.dao.AdviceMapper;
import me.ianhe.dao.ArticleMapper;
import me.ianhe.entity.Advice;
import me.ianhe.entity.Article;
import me.ianhe.model.Pagination;
import me.ianhe.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文章管理
 * PackageName:   com.seven.ianhe.manager
 * ClassName:     ArticleManager
 * Description:
 * Date           16/12/27
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Service
public class ArticleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ArticleMapper articleMapper;
    @Autowired
    private AdviceMapper adviceMapper;

    public int addArticle(Article article) {
        article.setAuthor("Ian He");
        Date now = new Date();
        article.setCreateTime(now);
        article.setUpdateTime(now);
        article.setReadNum(0L);
        logger.debug("create a new article:{}", article.getTitle());
        return articleMapper.insert(article);
    }

    public int editArticle(Article article) {
        article.setUpdateTime(new Date());
        logger.info("update article:{}", JsonUtil.toJson(article));
        return articleMapper.updateByPrimaryKey(article);
    }

    public Article selectArticleById(Integer id) {
        return articleMapper.selectByPrimaryKey(id);
    }

    /**
     * 分页查询
     *
     * @author iHelin
     * @since 2017/12/19 15:17
     */
    public Pagination findByPage(String title, int currentPage, int pageLength) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(title)) {
            res.put("title", title);
        }
        List<Article> data = articleMapper.listByCondition(res, new RowBounds(currentPage, pageLength));
        long totalCount = articleMapper.listCount(res);
        return new Pagination(data, totalCount, currentPage, pageLength);
    }

    public int deleteById(Integer id) {
        return articleMapper.deleteByPrimaryKey(id);
    }

    public List<Article> listByCondition(String title, int currentPage, int pageLength) {
        Map<String, Object> res = Maps.newHashMap();
        if (StringUtils.isNotEmpty(title)) {
            res.put("title", title);
        }
        return articleMapper.listByCondition(res, new RowBounds(currentPage, pageLength));
    }

    public int addAdvice(Advice advice){
        return adviceMapper.insert(advice);
    }
}
