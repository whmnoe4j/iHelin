package me.ianhe.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author iHelin
 * @create 2017-04-15 21:03
 */
public class GreetingInterceptor implements MethodInterceptor {

    /**
     * 截获目标方法的执行，并在前后添加横切逻辑
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] args = invocation.getArguments();//目标方法入参
        String clientName = (String) args[0];
        //在目标方法执行前调用
        System.out.println("around......How are you! Mr. " + clientName + ".");
        //通过反射机制调用目标方法
        Object obj = invocation.proceed();
        //在目标方法执行后调用
        System.out.println("around......Please enjoy yourself!");
        return obj;
    }
}
