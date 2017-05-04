package me.ianhe.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * 基于javaConfig注解配置
 * Created by iHelin on 17/5/3 22:24.
 */
public class AnnotationApplicationContextTest {

    @Test
    public void getBean() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Beans.class);
        Car car = context.getBean("car", Car.class);
        assertNotNull(car);
        car.introduce();
    }
}
