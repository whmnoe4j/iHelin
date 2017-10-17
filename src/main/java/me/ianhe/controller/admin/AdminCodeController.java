package me.ianhe.controller.admin;

import me.ianhe.utils.QrCodeUtil;
import me.ianhe.utils.RequestUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * 二维码图片生成
 * Date           16/12/20
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@RestController
@RequestMapping("admin/qrcode")
public class AdminCodeController extends BaseAdminController {

    /**
     * 生成二维码
     *
     * @author iHelin
     * @since 2017/8/31 22:24
     */
    @PostMapping(value = "generate")
    public String generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
        String path = "qrcode/";
        String format = "png";
        String fileName = new Random().nextInt(1000000) + "." + format;
        path = QrCodeUtil.generateQRCode(path, content, fileName, format, 300, 300);
        logger.info("Success generate qrcode {},ip is {}", content, RequestUtil.getRealIp(request));
        return success(path);
    }

}
