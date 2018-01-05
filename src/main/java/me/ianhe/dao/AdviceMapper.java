package me.ianhe.dao;

import me.ianhe.entity.Advice;

public interface AdviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Advice record);

    int insertSelective(Advice record);

    Advice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advice record);

    int updateByPrimaryKey(Advice record);
}