import me.learn.web.architecture.model.Greeting;
import me.learn.web.architecture.proxy.AfterProxy;
import me.learn.web.architecture.proxy.BeforeProxy;
import me.learn.web.architecture.proxy.Proxy;
import me.learn.web.architecture.util.ProxyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * web
 * PACKAGE_NAME
 * Created by kukuxiahuni on 2018/4/30.
 */
public class ProxyTest {

    public static void main(String[] args) {
        List<Proxy> proxyList = new ArrayList<Proxy>();
        proxyList.add(new BeforeProxy());
        proxyList.add(new AfterProxy());
        Greeting greeting = ProxyManager.createProxy(Greeting.class, proxyList);
        greeting.sayHello("黄传聪");
        greeting.echo("hahh");
    }
}
