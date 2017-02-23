package me.ianhe.test;

import me.ianhe.annotation.TestAnnotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author iHelin
 * @create 2017-02-08 13:30
 */
@TestAnnotation(description = "1111",createTime = "222222")
public class AnnotationTest {

    @TestAnnotation(createTime = "2017-02-08")
    public static String formatDate(Date date, String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(date);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Date now = new Date();//获取当前日期
        System.out.println(now);
        System.out.println(formatDate(now, "yyyy-MM-dd hh:mm:ss"));


        Class<AnnotationTest> annotationTestClass = AnnotationTest.class;
        Annotation[] annotations = annotationTestClass.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
        Method method = annotationTestClass.getMethod("formatDate", Date.class, String.class);
        TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
        Annotation annotation = annotationTestClass.getAnnotation( TestAnnotation.class );
        System.out.println(annotation);

        System.out.println("方法描述：" + testAnnotation.description());
        System.out.println("创建日期：" + testAnnotation.createTime());
        System.out.println(testAnnotation.annotationType());
    }

}
