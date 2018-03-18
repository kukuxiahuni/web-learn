package me.learn.web.architecture.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/16.
 */
public final class CodecUtil {

    /**
     *
     * @param source
     * @return
     */
    public final static String encodeURL(String source) {
        String target = null;

        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return target;
    }

    /**
     *
     * @param source
     * @return
     */
    public final static String decodeURL(String source) {
        String target = null;

        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }

}
