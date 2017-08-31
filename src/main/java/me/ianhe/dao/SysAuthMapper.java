package me.ianhe.dao;

import me.ianhe.db.entity.SysAuth;

import java.util.List;

public interface SysAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAuth record);

    int insertSelective(SysAuth record);

    SysAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAuth record);

    int updateByPrimaryKey(SysAuth record);

    /**
     * 获取系统所有权限
     *
     * @author iHelin
     * @since 2017/8/31 22:15
     */
    List<SysAuth> selectSysAuth();
}