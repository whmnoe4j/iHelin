package me.ianhe.test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author iHelin
 * @create 2017-02-06 21:59
 */
public class FileTest {

    public static void main(String[] args) throws IOException {
        test1();
//        test2();
    }

    public static void test1() throws FileNotFoundException {
        PrintWriter out = new PrintWriter("employee.txt");
        String name = "iHelin";
        double salary = 75000D;
        out.print(name);
        out.print(' ');
        out.println(salary);
        out.flush();
        out.print('.');
//        out.close();
    }

    public static void test2() throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("employee.txt"), true);
        String name = "Seven";
        double salary = 6000D;
        out.print(name);
        out.print(' ');
        out.println(salary);
    }

}
