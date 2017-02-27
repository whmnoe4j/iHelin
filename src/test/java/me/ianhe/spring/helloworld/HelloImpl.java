package me.ianhe.spring.helloworld;

/**
 * @author iHelin
 * @create 2017-02-27 18:25
 */
public class HelloImpl implements HelloApi {

    @Override
    public void sayHello() {
        System.out.println("Hello World!");
    }
}
