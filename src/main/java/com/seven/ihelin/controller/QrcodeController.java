package com.seven.ihelin.controller;

import com.google.common.collect.Maps;
import com.seven.ihelin.config.CommonConfig;
import com.seven.ihelin.db.entity.Qrcode;
import com.seven.ihelin.utils.JSON;
import com.seven.ihelin.utils.QRCode;
import com.seven.ihelin.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * PackageName:   com.seven.ihelin.controller
 * ClassName:
 * Description:
 * Date           17/1/5
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Controller
public class QrcodeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qrcodePage() {
        return "qrcode";
    }

    @RequestMapping(value = "qrcode/{id}", method = RequestMethod.GET)
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
    public String generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
        String path = "/zt/qrcode/";
        String format = "png";
        Qrcode qrcode = new Qrcode();
        qrcode.setContent(content);
        qrcode.setCreateTime(new Date());
        qrcode.setPath(path);
        qrcodeManager.insert(qrcode);
        String fileName = qrcode.getId() + "." + format;
        path = path + QRCode.generateQRCode(path, CommonConfig.getContextUrl() + "qrcode/" + +qrcode.getId(), fileName, format, 300, 300);
        Map<String, Object> res = Maps.newHashMap();
        res.put("status", "success");
        res.put("url", path);
        res.put("qrcode", qrcode);
        logger.info("Success generate qrcode {},ip: {}", content, RequestUtil.getRealIp(request));
        return JSON.toJson(res);
    }
}
