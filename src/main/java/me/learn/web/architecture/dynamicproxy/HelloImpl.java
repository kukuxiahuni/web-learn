package me.learn.web.architecture.dynamicproxy;

/**
 * web
 * me.learn.web.architecture.dynamicproxy
 * Created by kukuxiahuni on 2018/3/12.
 */
public class HelloImpl implements Hello {

    @Override
    public void say(String name) {
        System.out.println("hell0!" + name);
    }

}
