package me.ianhe.test;

import org.junit.Test;

/**
 * @author iHelin
 * @create 2017-02-06
 */
public class DirTest {

    /**
     * 显示项目绝对路径
     * 显示classpath路径
     */
    @Test
    public void dirTest() {
        //获取当前项目绝对路径
        System.out.println(System.getProperty("user.dir"));
        // /Users/iHelin/Documents/IdeaProjects/iHelin
        System.out.println(DirTest.class.getResource("/"));
        // file:/Users/iHelin/Documents/IdeaProjects/iHelin/target/test-classes/
    }

}
