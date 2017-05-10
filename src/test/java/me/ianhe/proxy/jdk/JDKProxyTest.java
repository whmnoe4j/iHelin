package me.ianhe.proxy.jdk;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理测试
 *
 * @author iHelin
 * @since 2017-01-03 16:35
 */
public class JDKProxyTest {

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            InstantiationException {
        Class<?> proxyClass = Proxy.getProxyClass(JDKProxyTest.class.getClassLoader(), Speaker.class);
        final Constructor<?> cons = proxyClass.getConstructor(InvocationHandler.class);
        final InvocationHandler ih = new MyInvocationHandler(new SpeakerImpl());
        Speaker speaker = (Speaker) cons.newInstance(ih);
        speaker.sayHello();
    }
}
