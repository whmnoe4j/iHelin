package me.ianhe.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环绕增强，在目标方法前后添加一行日志
 *
 * @author iHelin
 * @create 2017-04-16 22:17
 */
public class AroundAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        String methodName = invocation.getMethod().getName();
        logger.debug("{} 方法执行前。", methodName);
        Object obj = invocation.proceed();
        logger.debug("{} 方法执行后，结果：{}", methodName, obj);
        return obj;
    }
}