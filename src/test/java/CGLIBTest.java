import me.learn.web.architecture.model.Greeting;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Test;

/**
 * web
 * PACKAGE_NAME
 * Created by kukuxiahuni on 2018/4/30.
 */
public class CGLIBTest {

    @Test
    public void testEnhancer() {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Greeting.class);
        enhancer.setCallback(NoOp.INSTANCE);
        Greeting greeting = (Greeting) enhancer.create();
        greeting.sayHello("test");
    }
}
