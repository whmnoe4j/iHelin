package me.ianhe.controller.admin;

import com.google.common.collect.Maps;
import me.ianhe.utils.QRCode;
import me.ianhe.utils.RequestUtil;
import me.ianhe.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Random;

/**
 * 二维码图片生成
 * <p>
 * Date           16/12/20
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Controller
public class AdminQRCodeController extends BaseAdminController {

    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qRCode() {
        return ftl("qrcode");
    }

    @ResponseBody
    @RequestMapping(value = "qrcode/generate", method = RequestMethod.POST)
    public String generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
        String path = "qrcode/";
        String format = "png";
        String fileName = new Random().nextInt(1000000) + "." + format;
        path = QRCode.generateQRCode(path, content, fileName, format, 300, 300);
        logger.info("Success generate qrcode {},ip is {}", content, RequestUtil.getRealIp(request));
        return success(path);
    }

}
