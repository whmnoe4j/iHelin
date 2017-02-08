package me.ianhe.test;

import me.ianhe.annotation.MethodNote;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author iHelin
 * @create 2017-02-08 13:30
 */
public class AnnotationTest {

    @MethodNote(createTime = "2017-02-08")
    public static String formatDate(Date date, String formatPattern) {
        return new SimpleDateFormat(formatPattern).format(date);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Date now = new Date();//获取当前日期
        System.out.println(now);
        System.out.println(formatDate(now, "yyyy-MM-dd hh:mm:ss"));


        Class classOfDateUtil = AnnotationTest.class;
        Method formatDate = classOfDateUtil.getMethod("formatDate", Date.class, String.class);
        MethodNote methodNote = formatDate.getAnnotation(MethodNote.class);

        System.out.println("方法描述：" + methodNote.description());
        System.out.println("创建日期：" + methodNote.createTime());
        System.out.println(methodNote.annotationType());
    }

}
