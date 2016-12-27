package com.seven.ihelin.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片工具类（使用七牛云存储服务）
 *
 * @author Ian He
 */
public class FileUtil {

    public static final String ACCESS_KEY = "XRWyYeG0mx7jS_DRCj08bOHDweU44WeyjlbZxPFC";
    public static final String SECRET_KEY = "oD7T8X-h_vJaywxy8llLR4jKKE7BKwpIW3whlQoF";
    // 要上传的空间
    public static final String bucketname = "ihelin";

    public static void main(String args[]) throws IOException {
        File file = new File("/Users/iHelin/Pictures/001.png");
        System.out.println(uploadFile(org.apache.commons.io.FileUtils.readFileToByteArray(file), UUID.randomUUID().toString()));
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getUpToken() {
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        return auth.uploadToken(bucketname);
    }

    public static String uploadFile(byte[] bytes, String key) {
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        UploadManager uploadManager = new UploadManager(c);
        try {
            Response res = uploadManager.put(bytes, key, getUpToken());
            return res.bodyString();
        } catch (QiniuException e) {
            Response r = e.response;
            return r.toString();
        }
    }
}
