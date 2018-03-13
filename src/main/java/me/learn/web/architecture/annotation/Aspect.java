package me.learn.web.architecture.annotation;

import java.lang.annotation.*;

/**
 * web
 * me.learn.web.architecture.annotation
 * Created by kukuxiahuni on 2018/3/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    //注解类，用于定义@Controller这种注解
    Class<? extends Annotation> value();
}
