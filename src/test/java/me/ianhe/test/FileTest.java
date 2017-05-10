package me.ianhe.test;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author iHelin
 * @create 2017-02-06 21:59
 */
public class FileTest {

    /**
     * 向文件写入信息
     * 注意最后一定要flush或close
     *
     * @throws FileNotFoundException
     */
    @Test
    public void test() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("employee.txt");
        String name = "iHelin";
        double salary = 75000D;
        out.print(name);
        out.print(' ');
        out.println(salary);
        out.flush();
        out.print('。');
        out.close();
    }


}
