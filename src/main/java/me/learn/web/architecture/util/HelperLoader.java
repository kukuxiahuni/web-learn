package me.learn.web.architecture.util;

import java.util.Arrays;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/16.
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class, BeanHelper.class, AopHelper.class, IocHelper.class, ControllerHelper.class
        };

        Arrays.stream(classList).forEach(ClassUtil::loadClass);
    }
}
