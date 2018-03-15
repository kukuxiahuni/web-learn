package me.learn.web.architecture.proxy;

import java.lang.reflect.Method;

/**
 * web
 * me.learn.web.architecture.proxy
 * Created by kukuxiahuni on 2018/3/14.
 */
public abstract class AspectProxy implements Proxy {

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {

        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();

        if (intercept(cls, method, params)) {
            before(cls, method, params);
            result = proxyChain.doProxyChain();
            after(cls, method, params);
        } else {
            result = proxyChain.doProxyChain();
        }
        end();

        return result;
    }


    public void begin() {

    }

    public void end() {

    }


    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

}
