package me.learn.web.architecture.proxy;

/**
 * web
 * me.learn.web.architecture.proxy
 * Created by kukuxiahuni on 2018/3/13.
 */
public interface Proxy {

    //执行链式代理
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
