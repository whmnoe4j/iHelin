package me.ianhe.dao;

import me.ianhe.db.entity.Staff;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StaffMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Staff record);

    int insertSelective(Staff record);

    Staff selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Staff record);

    int updateByPrimaryKey(Staff record);

    List<Staff> listByCondition(Map<String, Object> res);

    List<Staff> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listCount(Map<String, Object> res);
}