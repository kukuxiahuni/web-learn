package me.learn.web.architecture.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.dynamicproxy
 * Created by kukuxiahuni on 2018/3/12.
 */
public class CGLibDynamicProxy implements MethodInterceptor {

    private final static CGLibDynamicProxy instance = new CGLibDynamicProxy();

    private CGLibDynamicProxy() {

    }

    public final static CGLibDynamicProxy getInstance() {
        return instance;
    }

    /**
     * 获取cglib代理
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getProxy(Class<T> tClass) {
        return (T) Enhancer.create(tClass, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("after");
        return o1;
    }

    public static void main(String[] args) {
        Hello hello = CGLibDynamicProxy.getInstance().getProxy(HelloImpl.class);
        hello.say("小蛮猪");
    }

}
