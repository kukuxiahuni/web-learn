package me.learn.web.architecture.util;

import me.learn.web.architecture.annotation.Aspect;
import me.learn.web.architecture.proxy.AspectProxy;
import me.learn.web.architecture.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/19.
 */
public final class AopHelper {


    static {
        Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
        Map<Class<?>, List<Proxy>> targetProxyMap = createTargetProxyMap(proxyMap);

        targetProxyMap.entrySet().forEach(entry->{
            Class<?> targetClass = entry.getKey();
            List<Proxy> proxyList = entry.getValue();

            Object proxy = ProxyManager.createProxy(targetClass, proxyList);
            //设置目标类与代理对象
            BeanHelper.setBean(targetClass, proxy);

        });
    }


    /**
     * 创建
     *
     * @param aspect
     * @return
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {

        Class<? extends Annotation> annotationClass = aspect.value();

        if (Objects.nonNull(annotationClass) && annotationClass != aspect.getClass()) {
            return ClassHelper.getClassSetByAnnotation(annotationClass);
        }
        return Collections.emptySet();
    }

    //创建代理类和目标类之间的映射关系
    private final static Map<Class<?>, Set<Class<?>>> createProxyMap() {

        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        //获取所有的代理类
        Set<Class<?>> proxySet = ClassHelper.getClassSetBySuper(AspectProxy.class);

        proxySet.stream().forEach(aClass -> {
            if (aClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = aClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(aClass, targetClassSet);
            }
        });

        return proxyMap;
    }

    /**
     * 创建目标类与代理对象之间关系
     *
     * @return
     */
    private final static Map<Class<?>, List<Proxy>> createTargetProxyMap(Map<Class<?>, Set<Class<?>>> proxyMap) {

        Map<Class<?>, List<Proxy>> targetProxyMap = new HashMap<>();

        proxyMap.entrySet().forEach(proxyEntry -> {

            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

            targetClassSet.forEach(
                    targetClass -> {

                        try {
                            Proxy proxy = (Proxy) proxyClass.newInstance();

                            if (targetProxyMap.containsKey(targetClass)) {

                                targetProxyMap.get(targetClass).add(proxy);

                            } else {
                                List<Proxy> proxyList = Arrays.asList(proxy);
                                targetProxyMap.put(targetClass, proxyList);
                            }

                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
            );


        });

        return targetProxyMap;
    }
}
