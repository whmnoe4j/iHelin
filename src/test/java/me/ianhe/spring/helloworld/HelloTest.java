package me.ianhe.spring.helloworld;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author iHelin
 * @create 2017-02-27 18:27
 */
public class HelloTest {

    /**
     * 使用构造器实例化bean
     */
    @Test
    public void testInstantiatingBeanByConstructor() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("instantiatingBean.xml");
        HelloApi bean1 = beanFactory.getBean("bean1", HelloApi.class);
        bean1.sayHello();
        HelloApi bean2 = beanFactory.getBean("bean2", HelloApi.class);
        bean2.sayHello();
    }

    /**
     * 使用静态工厂方法
     */
    @Test
    public void testInstantiatingBeanByStaticFactory() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("instantiatingBean.xml");
        HelloApi bean3 = beanFactory.getBean("bean3", HelloApi.class);
        bean3.sayHello();
    }

    /**
     * 使用实例工厂方法
     */
    @Test
    public void testInstantiatingBeanByInstanceFactory() {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("instantiatingBean.xml");
        HelloApi bean4 = beanFactory.getBean("bean4", HelloApi.class);
        bean4.sayHello();
    }

}
