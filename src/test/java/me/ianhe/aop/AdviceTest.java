package me.ianhe.aop;

import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author iHelin
 * @create 2017-04-15 20:32
 */
public class AdviceTest {

    private Waiter target;
    private BeforeAdvice advice;
    private ProxyFactory proxyFactory;

    @Before
    public void init() {
        target = new NaiveWaiter();
        advice = new GreetingBeforeAdvice();
        proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(target);
        proxyFactory.addAdvice(advice);
    }

    @Test
    public void beforeAdvice() {
        Waiter waiter = (NaiveWaiter) proxyFactory.getProxy();
        waiter.greetTo("John");
        waiter.serveTo("Tom");
    }

    @Test
    public void testAdvice() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Waiter waiter = (Waiter) context.getBean("waiter");
        waiter.greetTo("John");
    }

    @Test
    public void deleteViewSpace() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        ViewSpaceService viewSpaceService = (ViewSpaceService) context.getBean("viewSpaceService");
        viewSpaceService.deleteViewSpace(1);
    }

    @Test
    public void updateViewSpace() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        ViewSpaceService viewSpaceService = (ViewSpaceService) context.getBean("viewSpaceService");
        viewSpaceService.updateViewSpace();
    }

    @Test
    public void pointCut() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Waiter waiter = (NaiveWaiter) context.getBean("waiter1");
        Seller seller = (Seller) context.getBean("seller");
        waiter.greetTo("John");//这里实现了前置增强
        waiter.serveTo("John");
        seller.greetTo("John");
    }

    @Test
    public void regexp() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Waiter waiter = (NaiveWaiter) context.getBean("waiter2");
        waiter.greetTo("John");//这里实现了前置增强
        waiter.serveTo("John");
    }

    @Test
    public void autoCreator() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        Waiter waiter = (NaiveWaiter) context.getBean("waiterTarget");
        Seller seller = (Seller) context.getBean("sellerTarget");
        waiter.greetTo("John");//这里实现了前置增强
        seller.greetTo("Tom");
    }

}
