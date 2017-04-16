package me.ianhe.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author iHelin
 * @create 2017-04-16 22:17
 */
public class AroundAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
//        Object[] args = invocation.getArguments();
        String methodName = invocation.getMethod().getName();
        logger.debug(methodName + "方法执行前。");
        Object obj = invocation.proceed();
        logger.debug(methodName + "方法执行后");
        return obj;
    }
}
