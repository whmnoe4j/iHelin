package me.ianhe.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片工具类（使用七牛云存储服务）
 *
 * @author Ian He
 */
public class FileUtil {

    private static final String QINIU_PREFIX = "http://resource.ianhe.me/";

    private static final String ACCESS_KEY = "XRWyYeG0mx7jS_DRCj08bOHDweU44WeyjlbZxPFC";
    private static final String SECRET_KEY = "oD7T8X-h_vJaywxy8llLR4jKKE7BKwpIW3whlQoF";
    // 要上传的空间bucket
    private static final String BUCKET_NAME = "ihelin";

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 字节数组方式上传
     * 上传成功返回文件访问路径
     *
     * @param bytes
     * @param key
     * @return
     */
    public static String uploadFile(byte[] bytes, String key) {
        UploadManager uploadManager = getUploadManager();
        String uploadToken = getUpLoadToken();
        try {
            Response res = uploadManager.put(bytes, key, uploadToken);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return QINIU_PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    /**
     * 以文件形式上传
     * 上传成功返回文件访问路径
     *
     * @param bytes
     * @param key
     * @return
     */
    public static String uploadFile(File file, String key) {
        UploadManager uploadManager = getUploadManager();
        String uploadToken = getUpLoadToken();
        try {
            Response res = uploadManager.put(file, key, uploadToken);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return QINIU_PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    public static String uploadFile(String filePath, String key) {
        String uploadToken = getUpLoadToken();
        UploadManager uploadManager = getUploadManager();
        try {
            Response res = uploadManager.put(filePath, key, uploadToken);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return QINIU_PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    public static void main(String args[]) throws IOException {
//        File file = new File("/Users/iHelin/Documents/IdeaProjects/iHelin/target/favicon.ico");
        String key = UUID.randomUUID().toString();
        System.out.println(uploadFile("/Users/iHelin/Documents/IdeaProjects/iHelin/target/favicon.ico",key));
//        System.out.println(uploadFile(FileUtils.readFileToByteArray(file), UUID.randomUUID().toString()));
    }

    /**
     * 获得token
     * 简单上传，使用默认策略，只需要设置上传的空间名就可以了
     *
     * @return
     */
    private static String getUpLoadToken() {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(BUCKET_NAME);
    }

    /**
     * 获得上传对象
     *
     * @return
     */
    private static UploadManager getUploadManager() {
        Zone zone = Zone.autoZone();
        Configuration config = new Configuration(zone);
        UploadManager uploadManager = new UploadManager(config);
        return uploadManager;
    }
}
