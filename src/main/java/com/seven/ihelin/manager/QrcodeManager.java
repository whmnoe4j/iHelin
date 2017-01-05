package com.seven.ihelin.manager;

import com.seven.ihelin.db.entity.Qrcode;
import com.seven.ihelin.db.mapper.QrcodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * PackageName:   com.seven.ihelin.manager
 * ClassName:
 * Description:
 * Date           17/1/5
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Service
public class QrcodeManager {

    @Resource
    private QrcodeMapper qrcodeMapper;

    public Qrcode getQrcode(Integer id) {
        return qrcodeMapper.selectByPrimaryKey(id);
    }

    public int insert(Qrcode qrcode) {
        return qrcodeMapper.insert(qrcode);
    }
}
