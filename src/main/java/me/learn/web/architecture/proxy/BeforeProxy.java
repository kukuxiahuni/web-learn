package me.learn.web.architecture.proxy;

import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.proxy
 * Created by kukuxiahuni on 2018/4/30.
 */
public class BeforeProxy extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("before");
    }
}
