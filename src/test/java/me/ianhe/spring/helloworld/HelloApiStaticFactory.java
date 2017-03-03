package me.ianhe.spring.helloworld;

/**
 * 使用静态工厂方式实例化Bean
 *
 * @author iHelin
 * @create 2017-02-27 19:30
 */
public class HelloApiStaticFactory {

    public static HelloApi newInstance(String message) {
        return new HelloImpl2(message);
    }

}
