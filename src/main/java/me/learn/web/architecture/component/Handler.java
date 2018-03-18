package me.learn.web.architecture.component;

import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.component
 * Created by kukuxiahuni on 2018/3/16.
 */
public class Handler {

    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
