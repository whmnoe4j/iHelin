package com.seven.ihelin.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * PackageName:   com.seven.ihelin.test
 * ClassName:
 * Description:
 * Date           17/1/8
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String url = "http://music.163.com/#/discover/playlist/?order=hot&cat=%E5%85%A8%E9%83%A8&limit=35&offset=0";
        Connection.Response response = Jsoup.connect(url).timeout(3000).execute();
        System.out.println(response.body());
    }
}
