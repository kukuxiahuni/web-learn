package me.learn.web.architecture.annotation;

import java.lang.annotation.*;

/**
 * web
 * me.learn.web.architecture.annotation
 * Created by kukuxiahuni on 2018/3/13.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    String value();
}
