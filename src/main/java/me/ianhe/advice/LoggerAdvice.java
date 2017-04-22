package me.ianhe.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 前置增强，在目标方法前执行
 *
 * @author iHelin
 * @create 2017-04-15 23:05
 */
public class LoggerAdvice implements MethodBeforeAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        logger.debug("aop测试，访问了方法：{}", method.getName());
    }

}