package me.ianhe.test;

/**
 * @author iHelin
 * @create 2017-02-06 21:47
 */
public class DirTest {

    /**
     * 显示项目绝对路径
     * 显示classpath路径
     *
     * @param args
     */
    public static void main(String[] args) {
        //获取当前项目绝对路径
        System.out.println(System.getProperty("user.dir"));
        System.out.println(DirTest.class.getResource("/"));
    }

}
