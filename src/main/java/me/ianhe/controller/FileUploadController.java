package me.ianhe.controller;

import com.beust.jcommander.internal.Maps;
import me.ianhe.utils.FileUtil;
import me.ianhe.utils.JsonUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

/**
 * FileUploadController
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
@Controller
public class FileUploadController extends BaseController {

    /**
     * 文件上传页面
     *
     * @return
     */
    @GetMapping("upload")
    public String uploadPage(String src, Model model) {
        if (StringUtils.isNotBlank(src)) {
            model.addAttribute("msg", "上传成功！");
            model.addAttribute("src", src);
        }
        return "upload";
    }

    /**
     * 上传到七牛服务器
     *
     * @param file  待上传的文件
     * @param model
     * @return
     */
    @PostMapping(value = "upload")
    public String handleUpload(MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("msg", "请选择文件……");
            return "upload";
        }
        String res = FileUtil.uploadFile(file, "test-" + UUID.randomUUID().toString());
        return "redirect:/upload?src=" + res;
    }

    /**
     * 上传到七牛服务器
     *
     * @param file  待上传的文件
     * @return
     */
    @ResponseBody
    @PostMapping("upload2")
    public String handleUpload2(MultipartFile file) {
        if (file.isEmpty()) {
            return "请选择文件……";
        }
        String res = FileUtil.uploadFile(file, "test-" + UUID.randomUUID().toString());
        return res;
    }

    /**
     * simditor
     * 富文本编辑器图片文件上传接口
     *
     * @param file
     * @return
     */
    @ResponseBody
    @PostMapping(value = "img_upload")
    public String imgUpload(@RequestParam("file") MultipartFile file) {
        Map<String, Object> res = Maps.newHashMap();
        if (file.isEmpty()) {
            res.put("success", false);
            res.put("msg", "文件不存在！");
            return JsonUtil.toJson(res);
        }
        //扩展名
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        //文件相对路径（粘贴板文件无扩展名）
        String fileName = null;
        if (StringUtils.isBlank(fileExt)) {
            fileName = "article/" + UUID.randomUUID().toString() + ".png";
        } else {
            fileName = "article/" + UUID.randomUUID().toString() + "." + fileExt;
        }
        //保存到七牛对象存储
        String realFullPath = FileUtil.uploadFile(file, fileName);
        res.put("success", true);
        res.put("file_path", realFullPath);
        return JsonUtil.toJson(res);
    }

}
