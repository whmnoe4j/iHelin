package me.ianhe.controller;

import me.ianhe.db.entity.Qrcode;
import me.ianhe.utils.Global;
import me.ianhe.utils.QRCodeUtil;
import me.ianhe.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * PackageName:   com.seven.ianhe.controller
 * ClassName:
 * Description:
 * Date           17/1/5
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Controller
public class QrcodeController extends BaseController {

    @GetMapping(value = "qrcode")
    public String qrcodePage() {
        return "qrcode";
    }

    @GetMapping(value = "qrcode/{id}")
    public String qrcodePage(@PathVariable Integer id, Model model) {
        Qrcode qrcode = qrcodeManager.getQrcode(id);
        if (qrcode == null) {
            model.addAttribute("msg", "二维码不存在！");
            return "h5/error";
        }
        model.addAttribute("qrcode", qrcode);
        return "h5/qrcode_view";
    }

    @ResponseBody
    @RequestMapping(value = "generate_qrcode", method = RequestMethod.POST)
    public String generateQRCode(String content, HttpServletRequest request) {
        String path = "zt/qrcode/";
        String format = "png";
        Qrcode qrcode = new Qrcode();
        qrcode.setContent(content);
        qrcode.setCreateTime(new Date());
        qrcode.setPath(path);
        qrcodeManager.insert(qrcode);
        String fileName = qrcode.getId() + "." + format;
        String qrCodeContent = global.getDomainUrl() + "/qrcode/" + qrcode.getId();
        path = QRCodeUtil.generateQRCode(path, qrCodeContent,
                fileName, format);
        logger.debug("Successing generate qrcode {},ip: {}", qrCodeContent, RequestUtil.getRealIp(request));
        return success(path);
    }
}
