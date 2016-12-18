package com.seven.ihelin.db.mapper;

import com.seven.ihelin.db.entity.Tip;

public interface TipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Tip record);

    int insertSelective(Tip record);

    Tip selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tip record);

    int updateByPrimaryKey(Tip record);
}