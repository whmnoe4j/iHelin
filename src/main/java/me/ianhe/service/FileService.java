package me.ianhe.service;

import com.google.common.collect.Lists;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import me.ianhe.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/13 18:02
 */
@Service
public class FileService {

    private static final String BUCKET_NAME = "ihelin";

    @Autowired
    private Global global;

    /**
     * 获取七牛文件列表
     *
     * @author iHelin
     * @since 2017/11/13 18:25
     */
    public List<FileInfo> getFileInfoList() {
        Zone zone = Zone.autoZone();
        Configuration config = new Configuration(zone);
        Auth auth = Auth.create(global.getValue("qiniu.access.key"), global.getValue("qiniu.secret.key"));
        BucketManager bucketManager = new BucketManager(auth, config);
        BucketManager.FileListIterator fileListIterator = bucketManager
                .createFileListIterator(BUCKET_NAME, "", 100, "");
        List<FileInfo> fileInfoList = Lists.newArrayList();
        while (fileListIterator.hasNext()) {
            fileInfoList.addAll(Arrays.asList(fileListIterator.next()));
        }
        return fileInfoList;
    }

}
