package me.learn.web.architecture.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/14.
 */
public final class PropsUtils {

    /**
     * 加载配置文件
     *
     * @param filename
     * @return
     */
    public final static Properties loadProps(String filename) {

        Properties properties = null;
        InputStream inputStream = null;

        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);

            if (Objects.isNull(inputStream)) {
                throw new FileNotFoundException("文件未找到");
            }
            properties = new Properties();
            properties.load(inputStream);

        } catch (Exception e) {

        } finally {

            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    /**
     * @param properties
     * @param key
     * @param defaultValue
     * @return
     */
    public final static String getString(Properties properties, String key, String defaultValue) {

        String value = defaultValue;

        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;

    }

    /**
     *
     * @param properties
     * @param key
     * @return
     */
    public final static String getString(Properties properties, String key) {
        return getString(properties, key, "");

    }
}
