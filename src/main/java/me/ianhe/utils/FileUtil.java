package me.ianhe.utils;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
     * 以字节数组上传
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
     * @param file
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

    /**
     * 以字节流上传
     * 上传成功返回文件访问路径
     *
     * @param inputStream
     * @param key
     * @return
     */
    public static String uploadFile(InputStream inputStream, String key) {
        UploadManager uploadManager = getUploadManager();
        String uploadToken = getUpLoadToken();
        try {
            Response res = uploadManager.put(inputStream, key, uploadToken, null, null);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return QINIU_PREFIX + key;
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
        String uploadToken = getUpLoadToken();
        UploadManager uploadManager = getUploadManager();
        try {
            Response res = uploadManager.put(pathToFile, key, uploadToken);
            LOGGER.info("upload file {} to qiniu service,result:{}", key, res.isOK());
            return QINIU_PREFIX + key;
        } catch (QiniuException e) {
            LOGGER.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    public static void main(String args[]) throws IOException {
        String key = UUID.randomUUID().toString();
        System.out.println(uploadFile("/Users/iHelin/Documents/IdeaProjects/iHelin/target/favicon.ico", key));
    }

    public static List<Map<String, Object>> getFileList() {
        //构造一个带指定Zone对象的配置类
        Zone zone = Zone.autoZone();
        Configuration cfg = new Configuration(zone);
        //...其他参数参考类注释
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        String delimiter = "";
        BucketManager.FileListIterator fileListIterator = bucketManager
                .createFileListIterator(BUCKET_NAME, prefix, limit, delimiter);
        List<Map<String, Object>> fileInfoList = Lists.newArrayList();
        while (fileListIterator.hasNext()) {
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("key", item.key);
                fileInfoList.add(map);
            }
        }
        return fileInfoList;
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

    private FileUtil() {
        //工具类不允许实例化
    }
}
