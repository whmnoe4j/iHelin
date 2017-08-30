package me.ianhe.dao;

import me.ianhe.db.entity.Authorities;

import java.util.List;

public interface AuthoritiesMapper {
    int insert(Authorities record);

    int insertSelective(Authorities record);

    List<Authorities> getUserAuthoritys(String username);
}