package me.ianhe.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 图片工具类（使用七牛云存储服务）
 *
 * @author Ian He
 */
public class FileUtil {

    /**
     * 要上传的空间bucket
     */
    private static final String BUCKET_NAME = "ihelin";
    private static final String PREFIX = "http://resource.ianhe.me/";
    private static final String ACCESS_KEY = "XRWyYeG0mx7jS_DRCj08bOHDweU44WeyjlbZxPFC";
    private static final String SECRET_KEY = "oD7T8X-h_vJaywxy8llLR4jKKE7BKwpIW3whlQoF";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private static final String TOKEN;
    private static final UploadManager UPLOAD_MANAGER;
    private static final BucketManager BUCKET_MANAGER;

    static {
        Zone zone = Zone.autoZone();
        Configuration config = new Configuration(zone);
        UPLOAD_MANAGER = new UploadManager(config);
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        TOKEN = auth.uploadToken(BUCKET_NAME);
        BUCKET_MANAGER = new BucketManager(auth, config);
    }

    /**
     * 以字节数组上传
     * 上传成功返回文件访问路径
     *
     * @param bytes
     * @param key
     * @return
     */
    public static String uploadFile(byte[] bytes, String key) {
        try {
            Response res = UPLOAD_MANAGER.put(bytes, key, TOKEN);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    /**
     * 以文件形式上传
     * 上传成功返回文件访问路径
     *
     * @param file
     * @param key
     * @return
     */
    public static String uploadFile(File file, String key) {
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
        return uploadFile(inputStream, key);
    }

    /**
     * 以字节流上传
     * 上传成功返回文件访问路径
     *
     * @param inputStream
     * @param key
     * @return
     */
    public static String uploadFile(InputStream inputStream, String key) {
        try {
            Response res = UPLOAD_MANAGER.put(inputStream, key, TOKEN, null, null);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    /**
     * 以MultipartFile文件形式上传，适用于spring mvc
     * 上传成功返回文件访问路径
     *
     * @param multipartFile
     * @param key
     * @return
     */
    public static String uploadFile(MultipartFile multipartFile, String key) {
        try {
            return uploadFile(multipartFile.getInputStream(), key);
        } catch (IOException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    /**
     * 以绝对路径上传
     * 上传成功返回文件访问路径
     *
     * @param pathToFile
     * @param key
     * @return
     */
    public static String uploadFile(String pathToFile, String key) {
        File file = new File(pathToFile);
        return uploadFile(file, key);
    }

    private FileUtil() {
        //工具类不允许实例化
    }
}
