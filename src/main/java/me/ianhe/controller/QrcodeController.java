package me.ianhe.controller;

import me.ianhe.db.entity.Qrcode;
import me.ianhe.utils.Global;
import me.ianhe.utils.QrCodeUtil;
import me.ianhe.utils.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 二维码
 *
 * @author iHelin
 * @since 2017/10/17 15:25
 */
@Controller
public class QrcodeController extends BaseController {

    @GetMapping(value = "qrcode")
    public String qrcodePage() {
        return "qrcode";
    }

    @GetMapping(value = "qrcode/{id}")
    public String qrcodePage(@PathVariable Integer id, Model model) {
        Qrcode qrcode = qrcodeService.getQrcode(id);
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
        qrcodeService.insert(qrcode);
        String fileName = qrcode.getId() + "." + format;
        String qrCodeContent = Global.getDomainUrl() + "/qrcode/" + qrcode.getId();
        path = QrCodeUtil.generateQRCode(path, qrCodeContent,
                fileName, format);
        logger.debug("Successing generate qrcode {},ip: {}", qrCodeContent, RequestUtil.getRealIp(request));
        return success(path);
    }
}
