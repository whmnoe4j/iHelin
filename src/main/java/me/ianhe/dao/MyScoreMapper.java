package me.ianhe.dao;

import me.ianhe.db.entity.MyScore;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface MyScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyScore record);

    int insertSelective(MyScore record);

    MyScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyScore record);

    int updateByPrimaryKeyWithBLOBs(MyScore record);

    int updateByPrimaryKey(MyScore record);

    long getTotalScore();

    List<MyScore> selectByCondition(RowBounds rowBounds);
}