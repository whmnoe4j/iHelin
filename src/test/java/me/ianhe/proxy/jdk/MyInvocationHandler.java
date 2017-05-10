package me.ianhe.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类
 *
 * @author iHelin
 * @since 2017-01-03 16:39
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("method :" + method.getName() + "() is invoked!");
        Object res = method.invoke(target, args);
        System.out.println("After...");
        return res;
    }
}
