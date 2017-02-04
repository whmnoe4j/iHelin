package me.ianhe.db.mapper;

import me.ianhe.db.entity.ServiceMenu;

import java.util.List;
import java.util.Map;

public interface ServiceMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceMenu record);

    int insertSelective(ServiceMenu record);

    ServiceMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceMenu record);

    int updateByPrimaryKey(ServiceMenu record);

    List<ServiceMenu> getAllMenus();

    List<ServiceMenu> getMenuByCondition(Map<String, Object> res);

    List<ServiceMenu> getMenustByParentId(Integer parentId);
}