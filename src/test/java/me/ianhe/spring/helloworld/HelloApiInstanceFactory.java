package me.ianhe.spring.helloworld;

/**
 * 使用实例工厂方法实例化Bean
 *
 * @author iHelin
 * @create 2017-02-27 19:34
 */
public class HelloApiInstanceFactory {

    public HelloApi newInstance(String message) {
        return new HelloImpl2(message);
    }

}
