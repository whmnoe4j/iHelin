package me.ianhe.service;

import me.ianhe.db.entity.Qrcode;
import me.ianhe.dao.QrcodeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * PackageName:   com.seven.ianhe.manager
 * ClassName:
 * Description:
 * Date           17/1/5
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Service
public class QrcodeService {

    @Resource
    private QrcodeMapper qrcodeMapper;

    public Qrcode getQrcode(Integer id) {
        return qrcodeMapper.selectByPrimaryKey(id);
    }

    public int insert(Qrcode qrcode) {
        return qrcodeMapper.insert(qrcode);
    }
}
