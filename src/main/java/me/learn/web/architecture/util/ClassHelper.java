package me.learn.web.architecture.util;

import me.learn.web.architecture.annotation.Controller;
import me.learn.web.architecture.annotation.Service;

import java.lang.annotation.Annotation;
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

    /**
     * 根据父类查找其所有的子类
     * @param superClass
     * @return
     */
    public final static Set<Class<?>> getClassSetBySuper(Class<?> superClass) {

        return CLASS_SET.stream().filter(aClass -> {

            //判断superClass是否是aclass的超类或相同的类
            if (superClass.isAssignableFrom(aClass) && !superClass.equals(aClass)) {

                return true;
            }
            return false;

        }).collect(Collectors.toSet());

    }

    /**
     * 获取含有某个注解的set
     * @param annotation
     * @return
     */
    public final static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotation) {

        return CLASS_SET.stream().filter(aClass -> {

            return aClass.isAnnotationPresent(annotation);

        }).collect(Collectors.toSet());

    }
}
