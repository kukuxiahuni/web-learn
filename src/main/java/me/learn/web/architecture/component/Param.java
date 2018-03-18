package me.learn.web.architecture.component;

import java.util.Map;

/**
 * web
 * me.learn.web.architecture.component
 * Created by kukuxiahuni on 2018/3/16.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
