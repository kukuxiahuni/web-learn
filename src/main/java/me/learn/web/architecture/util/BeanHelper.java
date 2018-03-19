package me.learn.web.architecture.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/15.
 */
public final class BeanHelper {

    private final static Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {

        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        beanClassSet.stream().forEach(aClass -> {
            BEAN_MAP.put(aClass, ReflectionUtil.newInstance(aClass));
        });
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取bean
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public final static <T> T getBean(Class<T> tClass) {

        return (T) BEAN_MAP.getOrDefault(tClass, null);
    }

    public static void setBean(Class<?> cls, Object object) {

        BEAN_MAP.put(cls, object);

    }
}
