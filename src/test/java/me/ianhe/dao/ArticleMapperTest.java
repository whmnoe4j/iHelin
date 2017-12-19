package me.ianhe.dao;

import me.ianhe.entity.Article;
import me.ianhe.utils.JsonUtil;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/10 15:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext.xml"})
public class ArticleMapperTest {

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void deleteByPrimaryKey() throws Exception {
    }

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void insertSelective() throws Exception {
    }

    @Test
    public void selectByPrimaryKey() throws Exception {
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {
    }

    @Test
    public void updateByPrimaryKey() throws Exception {
    }

    @Test
    public void listByCondition() throws Exception {
        List<Article> articles = articleMapper.listByCondition(null, new RowBounds(1, 10));
        System.out.println(JsonUtil.toJson(articles));
    }

    @Test
    public void listCount() throws Exception {
    }

}