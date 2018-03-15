package me.learn.web.architecture.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
     * 1. .class文件
     * 2. JAR包中的文件
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName) {

        Set<Class<?>> classSet = new LinkedHashSet<>();


        try {
            /**
             * 注意 replace和replaceAll的区别：
             *  replaceAll直接使用 正则表达式， 如.需要写成 \\.
             *  而replace则使用字符的字面意思
             */
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();

                if (url != null) {
                    String protocol = url.getProtocol();
                    if (StringUtils.equalsIgnoreCase(protocol, "file")) {

                        String packagePath = url.getPath().replaceAll("%20", "");
                        addClass(classSet, packagePath, packageName);

                    } else if (StringUtils.equalsIgnoreCase(protocol, "jar")) {
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (Objects.nonNull(jarURLConnection)) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (Objects.nonNull(jarFile)) {
                                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();

                                while (jarEntryEnumeration.hasMoreElements()) {
                                    JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                    String jarEntryname = jarEntry.getName();
                                    if (jarEntryname.endsWith(".class")) {
                                        String className = jarEntryname.substring(0, jarEntryname.lastIndexOf(".")).replaceAll("/", ".");
                                        doAddClass(classSet, className);

                                    }
                                }

                            }
                        }
                    }

                }
            }

            return classSet;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptySet();

    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = Arrays.stream(new File(packagePath).listFiles()).filter(file ->
                (file.isFile() && file.getName().endsWith(".class") || file.isDirectory())
        ).toArray(File[]::new);

        Arrays.stream(files).forEach(file -> {
            String fileName = file.getName();
            if (file.isFile()) {
                String classname = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotBlank(packageName)) {
                    classname = packageName + "." + classname;
                }

                doAddClass(classSet, classname);

            } else {

                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(subPackagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }

                String subPackagename = fileName;
                if (StringUtils.isNotBlank(subPackagename)) {
                    subPackagename = packageName + "." + subPackagename;
                }

                addClass(classSet, subPackagePath, subPackagename);

            }
        });
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }

    public static void main(String[] args) {
        Set<Class<?>> classSet = getClassSet("me.learn.web.architecture");

        classSet.stream().forEach( aClass -> {
            System.out.println(aClass.getName());
        });
    }

}
