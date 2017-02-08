package me.ianhe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by iHelin on 17/2/8.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodNote {

    // 方法作用，有默认值
    String description() default "作者很懒，没有写本方法的作用。";

    // 方法创建时间
    String createTime();

}
