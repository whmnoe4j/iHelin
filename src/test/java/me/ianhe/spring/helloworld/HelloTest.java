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

    @Test
    public void testHelloWorld() {
        //1、读取配置文件实例化一个IoC容器
        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld.xml");
        //2、从容器中获取Bean，注意此处完全“面向接口编程，而不是面向实现”
        HelloApi helloApi = context.getBean("hello", HelloApi.class);
//        HelloApi helloApi = context.getBean(HelloApi.class);
        //3、执行业务逻辑
        helloApi.sayHello();
    }

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
