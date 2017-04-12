package me.ianhe.dao;

import java.util.List;

import me.ianhe.db.entity.Advice;

public interface AdviceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Advice record);

    int insertSelective(Advice record);

    Advice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advice record);

    int updateByPrimaryKey(Advice record);

    List<Advice> selectAdviceByCondition();
}