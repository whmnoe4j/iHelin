package me.ianhe.controller.admin;

import com.qiniu.storage.model.FileInfo;
import me.ianhe.utils.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * FileUploadController
 *
 * @author iHelin
 * @since 2017/10/17 15:29
 */
@RestController
public class AdminFileController extends BaseAdminController {

    /**
     * 上传到七牛服务器
     *
     * @param file 待上传的文件
     * @return
     */
    @PostMapping("upload")
    public String handleUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return error("请选择文件...");
        }
        return FileUtil.uploadFile(file, "test/" + UUID.randomUUID().toString());
    }

    @GetMapping("files")
    public List<FileInfo> getFileInfoList() {
        return fileService.getFileInfoList();
    }

}
