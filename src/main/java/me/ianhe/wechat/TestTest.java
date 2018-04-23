package me.ianhe.wechat;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author iHelin
 * @since 2018/2/9 17:42
 */
public class TestTest {

    //    (\..*)
    public static void main(String[] args) {
        Pattern r = Pattern.compile("(.*)-(.*)(\\..*)");

        File file = new File("/Users/iHelin/OneDrive/音乐");
        File[] files = file.listFiles();
        for (File f : files) {
            Matcher m = r.matcher(f.getName());
            if (m.matches()) {
                System.out.println(m.group(1));
                System.out.println(m.group(2));
                System.out.println(m.group(3));
                f.renameTo(new File(file, m.group(1).trim() + " - " + m.group(2).trim() + m.group(3).trim()));
            }
        }
    }
}
