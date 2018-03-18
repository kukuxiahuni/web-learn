package me.learn.web.architecture.model;

import java.util.HashMap;
import java.util.Map;

/**
 * web
 * me.learn.web.architecture.model
 * Created by kukuxiahuni on 2018/3/16.
 */
public class View {
    //视图路径：也就是jsp或html的位置
    private String path;

    //模型数据
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<>();
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public View addModel(String key, Object value) {
        this.model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
