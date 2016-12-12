package com.seven.ihelin.db.mapper;

import java.util.List;

import com.seven.ihelin.db.entity.Advice;
import org.springframework.stereotype.Service;

public interface AdviceMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Advice record);

    int insertSelective(Advice record);

    Advice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Advice record);

    int updateByPrimaryKey(Advice record);

    List<Advice> selectAdviceByCondition();
}