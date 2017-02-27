package me.ianhe.spring.helloworld;

/**
 * 使用构造器实例化Bean
 *
 * @author iHelin
 * @create 2017-02-27 19:21
 */
public class HelloImpl2 implements HelloApi {

    private String message;

    public HelloImpl2() {
        this.message = "Hello World!";
    }

    public HelloImpl2(String message) {
        this.message = message;
    }


    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
