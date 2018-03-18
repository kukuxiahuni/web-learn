package me.learn.web.architecture.util;

import me.learn.web.architecture.annotation.Action;
import me.learn.web.architecture.component.Handler;
import me.learn.web.architecture.component.Request;

import java.lang.reflect.Method;
import java.util.*;

/**
 * web
 * me.learn.web.architecture.util
 * Created by kukuxiahuni on 2018/3/16.
 */
public final class ControllerHelper {

    private final static Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static {
        Set<Class<?>> controllerSet = ClassHelper.getControllerClassSet();

        if (Objects.nonNull(controllerSet) && !controllerSet.isEmpty()) {

            //遍历controller，获取其中的方法定义
            controllerSet.stream().forEach(
                    aClass -> {
                        Method[] methods = aClass.getDeclaredMethods();

                        if (Objects.nonNull(methods) && methods.length > 0) {

                            Arrays.stream(methods).forEach(
                                    method -> {
                                        if (method.isAnnotationPresent(Action.class)) {
                                            Action action = method.getAnnotation(Action.class);
                                            String mapping = action.value();

                                            if (mapping.matches("\\w+:/\\w*")) {
                                                String[] arrays = mapping.split(":");
                                                if (Objects.nonNull(arrays) && arrays.length == 2) {
                                                    String requestMethod = arrays[0];
                                                    String requestPath = arrays[1];

                                                    Request request = new Request(requestMethod, requestPath);
                                                    Handler handler = new Handler(aClass, method);
                                                    //初始化Action Map

                                                    ACTION_MAP.put(request, handler);

                                                }
                                            }
                                        }
                                    }
                            );

                        }
                    }
            );
        }
    }

    /**
     * 获取路径的处理方法
     *
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public final static Handler getHandler(String requestMethod, String requestPath) {

        Request request = new Request(requestMethod, requestPath);

        return ACTION_MAP.get(request);

    }

}
