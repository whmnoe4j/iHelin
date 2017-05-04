package me.ianhe.ioc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Created by iHelin on 17/5/3 22:33.
 */
public class GroovyApplicationContextTest {

    @Test
    public void getBean() {
        ApplicationContext context = new GenericGroovyApplicationContext("classpath:me/ianhe/ioc/beans.groovy");
        Car car = context.getBean("car", Car.class);
        assertNotNull(car);
        car.introduce();
    }
}
