package me.ianhe.spring.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

/**
 * @author iHelin
 * @create 2017-04-15 21:45
 */
public class GreetingAdvisor extends StaticMethodMatcherPointcutAdvisor {

    /**
     * 切点方法匹配规则：方法名为greetTo
     *
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return "greetTo".equals(method.getName());
    }

    /**
     * 切点类匹配规则：为Waiter类或其子类
     *
     * @return
     */
    @Override
    public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
            public boolean matches(Class<?> clazz) {
                return Waiter.class.isAssignableFrom(clazz);
            }
        };
    }
}
