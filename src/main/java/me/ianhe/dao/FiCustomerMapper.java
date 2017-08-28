package me.ianhe.dao;

import me.ianhe.db.entity.FiCustomer;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface FiCustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FiCustomer record);

    int insertSelective(FiCustomer record);

    FiCustomer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FiCustomer record);

    int updateByPrimaryKey(FiCustomer record);

    List<FiCustomer> getAllCustomer();

    int listCount(Map<String, Object> res);

    /**
     * 根据名称查询客户数量
     *
     * @author iHelin
     * @since 2017/8/25 09:27
     */
    int getCountByCustomerName(String customerName);

    List<FiCustomer> listByCondition(Map<String, Object> params);

    List<FiCustomer> listByCondition(Map<String, Object> params, RowBounds rowBounds);
}