package me.ianhe.dao;

import me.ianhe.db.entity.FiClear;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface FiClearMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FiClear record);

    int insertSelective(FiClear record);

    FiClear selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FiClear record);

    int updateByPrimaryKey(FiClear record);

    List<FiClear> listByCondition(Map<String, Object> params);

    List<FiClear> listByCondition(Map<String, Object> params, RowBounds rowBounds);

    int listCount(Map<String, Object> res);
}