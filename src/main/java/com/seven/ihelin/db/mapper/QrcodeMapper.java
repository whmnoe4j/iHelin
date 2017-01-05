package com.seven.ihelin.db.mapper;

import com.seven.ihelin.db.entity.Qrcode;

public interface QrcodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qrcode record);

    int insertSelective(Qrcode record);

    Qrcode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qrcode record);

    int updateByPrimaryKey(Qrcode record);
}