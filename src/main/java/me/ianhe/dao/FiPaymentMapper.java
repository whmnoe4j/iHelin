package me.ianhe.dao;

import me.ianhe.db.entity.FiClear;
import me.ianhe.db.entity.FiPayment;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface FiPaymentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FiPayment record);

    int insertSelective(FiPayment record);

    FiPayment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FiPayment record);

    int updateByPrimaryKey(FiPayment record);

    List<FiClear> listByCondition(Map<String, Object> params);

    List<FiClear> listByCondition(Map<String, Object> params, RowBounds rowBounds);
}