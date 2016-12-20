package com.seven.ihelin.controller;

import com.qiniu.storage.model.FileInfo;
import com.seven.ihelin.utils.FileUtils;
import com.seven.ihelin.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Controller
public class FileUploadController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadPage() {
        return "upload";
    }

    /**
     * 上传到七牛
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "请选择文件……");
            return "upload";
        }
        try {
            byte[] bytes = file.getBytes();
            String res = FileUtils.uploadFile(bytes, UUID.randomUUID().toString());
            FileInfo fileInfo = JSON.parseObject(res, FileInfo.class);
            logger.info(JSON.toJson(fileInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg", "上传成功！");
        return "upload";
    }

}
