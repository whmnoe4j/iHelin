package me.ianhe.jdk.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Copyright: 2017 www.xsusong.com Inc. All rights reserved.
 * 注意：本内容仅限于上海鲜美信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * PackageName:   com.seven.ianhe
 * ClassName:
 * Description:
 * Date           17/1/3
 * lastModified:
 *
 * @author <href mailto="mailto:ihelin@outlook.com">iHelin</href>
 */
public class JdkProxyTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> proxyClass = Proxy.getProxyClass(JdkProxyTest.class.getClassLoader(), HelloWorld.class);
        final Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);
        final InvocationHandler ih = new MyInvocationHandler(new HelloWorldImpl());
        HelloWorld helloWorld = (HelloWorld) cons.newInstance(ih);
        helloWorld.sayHello();
    }
}
