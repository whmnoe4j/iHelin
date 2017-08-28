package me.ianhe.dao;

import me.ianhe.db.entity.FiSummary;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FiSummaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FiSummary record);

    int insertSelective(FiSummary record);

    FiSummary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FiSummary record);

    int updateByPrimaryKey(FiSummary record);

    List<FiSummary> listByCondition(Map<String, Object> res);

    List<FiSummary> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listCount(Map<String, Object> res);

    List<FiSummary> getSummaryByCustomerID(Integer customerID);

    List<HashMap> getIntervalSummary(Date curDate);

    List<HashMap> getIntervalSummary(Date curDate, RowBounds rowBounds);

    int getIntervalSummaryCount(Date curDate);

    List<HashMap> getSummaryByAcing(Map<String,Object> params);

    List<HashMap> getAlreadyClear(Integer customerID);
}