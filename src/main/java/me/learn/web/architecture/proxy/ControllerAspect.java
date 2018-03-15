package me.learn.web.architecture.proxy;

import me.learn.web.architecture.annotation.Aspect;
import me.learn.web.architecture.annotation.Controller;

import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.proxy
 * Created by kukuxiahuni on 2018/3/14.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("before");

    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("after");
    }
}
