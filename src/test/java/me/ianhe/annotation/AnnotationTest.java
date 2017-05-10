package me.ianhe.annotation;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author iHelin
 * @since 2017-02-08 13:30
 */
@FooBar(description = "1111", createTime = "222222")
public class AnnotationTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @FooBar(createTime = "2017-02-08",description = "哈哈哈哈")
    public String formatDate(Date date, String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(date);
    }

    @Test
    public void test() throws NoSuchMethodException {
        Date now = new Date();//获取当前日期
        logger.debug(formatDate(now, "yyyy-MM-dd HH:mm:ss"));

        Class<AnnotationTest> annotationTestClass = AnnotationTest.class;
        Annotation[] annotations = annotationTestClass.getAnnotations();
        for (Annotation annotation : annotations) {
            logger.debug("annotation:{}", annotation);
            logger.debug("type:{}", annotation.annotationType());
        }
        Method method = annotationTestClass.getMethod("formatDate", Date.class, String.class);
        FooBar fooBarAnnotation = method.getAnnotation(FooBar.class);
        Annotation annotation = annotationTestClass.getAnnotation(FooBar.class);
        logger.debug("annotation:{}", annotation);
        logger.debug("type:{}", annotation.annotationType());

        logger.debug("方法描述：" + fooBarAnnotation.description());
        logger.debug("创建日期：" + fooBarAnnotation.createTime());
        logger.debug(fooBarAnnotation.annotationType().toString());
    }

}
