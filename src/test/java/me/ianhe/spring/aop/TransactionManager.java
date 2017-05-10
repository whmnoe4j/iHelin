package me.ianhe.spring.aop;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * 异常抛出增强
 * @author iHelin
 * @create 2017-04-15 21:14
 */
public class TransactionManager implements ThrowsAdvice {

    /**
     * 定义增强逻辑
     *
     * @param method
     * @param objects
     * @param target
     * @param e
     * @throws Throwable
     */
    public void afterThrowing(Method method, Object[] objects, Object target, Exception e) throws Throwable {
        System.out.println("----------------");
        System.out.println("方法名称: " + method.getName());
        System.out.println("异常信息: " + e.getMessage());
        System.out.println("成功回滚事物。");
        System.out.println("----------------");
    }

}
