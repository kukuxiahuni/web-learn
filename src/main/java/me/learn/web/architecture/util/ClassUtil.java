package me.learn.web.architecture.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/2/13.
 */
public class ClassUtil {

    /**
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 根据类名加载类，
     *
     * @param classname
     * @param isInitialized：是否加载类中的静态方法
     * @return
     */
    public static Class<?> loadClass(String classname, boolean isInitialized) {

        Class<?> cls = null;
        try {
            cls = Class.forName(classname, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return cls;
    }

    /**
     * 获取指定包名下的所有类
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {

        Set<Class<?>> classSet = new LinkedHashSet<>();

        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                if (url != null) {
                    String protocol = url.getProtocol();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptySet();

    }



}
