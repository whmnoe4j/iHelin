package me.ianhe.db.mapper;

import me.ianhe.db.entity.Activity;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ActivityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> listByCondition(Map<String, Object> res, RowBounds rowBounds);

    int listCount(Map<String, Object> res);
}