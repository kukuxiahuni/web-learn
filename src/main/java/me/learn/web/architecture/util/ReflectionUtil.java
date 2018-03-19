package me.learn.web.architecture.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/15.
 */
public class ReflectionUtil {

    /**
     * 创建实例
     *
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<?> cls) {

        T instance = null;

        try {
            instance = (T) cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * @param obj
     * @param method
     * @param params
     * @return
     */
    public final static Object invokeMethod(Object obj, Method method, Object... params) {

        Object result = null;

        method.setAccessible(true);
        try {
            result = method.invoke(obj, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param object
     * @param field
     * @param value
     */
    public final static void setField(Object object, Field field, Object value) {

        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        ReflectionUtil reflectionUtil = newInstance(ReflectionUtil.class);
        System.out.println(reflectionUtil);
    }
}
