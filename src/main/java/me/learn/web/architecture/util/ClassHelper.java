package me.learn.web.architecture.util;

import me.learn.web.architecture.annotation.Controller;
import me.learn.web.architecture.annotation.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/15.
 */
public final class ClassHelper {

    private final static Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用下包名下的所有类
     *
     * @return
     */
    public final static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    //获取service类
    public final static Set<Class<?>> getServiceClassSet() {
        return CLASS_SET.stream().filter(aClass -> aClass.isAnnotationPresent(Service.class)).collect(Collectors.toSet());
    }

    //获取controller类
    public final static Set<Class<?>> getControllerClassSet() {
        return CLASS_SET.stream().filter(aClass -> aClass.isAnnotationPresent(Controller.class)).collect(Collectors.toSet());
    }

    //获取所有bean类
    public final static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }
}
