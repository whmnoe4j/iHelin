package me.ianhe.db.mapper;

import me.ianhe.db.entity.Article;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listCount(Map<String, Object> res);
}