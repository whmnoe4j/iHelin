package me.ianhe.utils;

import com.qiniu.storage.model.FileInfo;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by iHelin on 17/3/16.
 */
public class FileUtilTest {

    @Test
    public void getFileList() throws Exception {
        List<Map<String, Object>> files = FileUtil.getFileList();
        for (Map<String, Object> file : files) {
            System.out.println(file.get("key"));
        }
    }

}