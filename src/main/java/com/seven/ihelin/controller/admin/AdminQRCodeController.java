package com.seven.ihelin.controller.admin;

import com.google.common.collect.Maps;
import com.seven.ihelin.controller.BaseController;
import com.seven.ihelin.utils.QRCode;
import com.seven.ihelin.utils.RequestUtil;
import com.seven.ihelin.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 二维码图片生成
 * <p>
 * Date           16/12/20
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
@Controller
@RequestMapping(value = "admin")
public class AdminQRCodeController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "qrcode", method = RequestMethod.GET)
    public String qRCode() {
        return "admin/qrcode";
    }

    @RequestMapping(value = "generate_img", method = RequestMethod.POST)
    public void generateQRCode(String content, HttpServletRequest request, HttpServletResponse response) {
        String path = "img/";
        path = path + QRCode.generateQRCode(path, content, "png", 300, 300);
        Map<String, Object> res = Maps.newHashMap();
        res.put("url", path);
        LOGGER.info("Success generate qrcode {},ip is {}", content, RequestUtil.getRealIp(request));
        ResponseUtil.writeSuccessJSON(response, res);
    }

}
