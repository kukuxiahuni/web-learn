package me.learn.web.architecture.util;

import me.learn.web.architecture.annotation.Inject;
import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/16.
 */
public final class IocHelper {

    //依赖注入
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();

        if (Objects.nonNull(beanMap) && !beanMap.isEmpty()) {

            beanMap.entrySet().forEach(entry -> {

                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();

                Field[] beanFields = beanClass.getDeclaredFields();

                if (Objects.nonNull(beanFields) && beanFields.length > 0) {
                    Arrays.stream(beanFields).forEach(
                            field -> {
                                if (field.isAnnotationPresent(Inject.class)) {
                                    Class<?> beanFieldClass = field.getClass();
                                    Object beanFieldInstance = beanMap.get(beanFieldClass);

                                    if (Objects.nonNull(beanFieldInstance)) {
                                        ReflectionUtil.setField(entry, field, beanFieldInstance);
                                    }
                                }
                            }
                    );
                }


            });
        }
    }
}
