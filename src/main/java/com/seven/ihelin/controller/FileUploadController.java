package com.seven.ihelin.controller;

import com.qiniu.storage.model.FileInfo;
import com.seven.ihelin.utils.DateTimeUtil;
import com.seven.ihelin.utils.FileUtils;
import com.seven.ihelin.utils.JSON;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
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

    @RequestMapping(value = "imgupload", method = RequestMethod.POST)
    @ResponseBody
    public String imgUpload(@RequestParam("file") MultipartFile file) {
        ResponseJSON resJson = new ResponseJSON();
        if (file.isEmpty()) {
            resJson.setSuccess(false);
            resJson.setMsg("文件不存在！");
            return JSON.toJson(resJson);
        }
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        String fileName = "article/" + DateTimeUtil.formatSecond(new Date()) + "." + fileExt;
        try {
            byte[] bytes = file.getBytes();
            String result = FileUtils.uploadFile(bytes, fileName);
            FileInfo fileInfo = JSON.parseObject(result, FileInfo.class);
            logger.info(JSON.toJson(fileInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileName = "http://source.520lyx.cn/" + fileName;
        resJson.setSuccess(true);
        resJson.setFile_path(fileName);
        String res = JSON.toJson(resJson);
        return res;
    }

    class ResponseJSON {
        private boolean success;
        private String msg;
        private String file_path;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }
    }

}
