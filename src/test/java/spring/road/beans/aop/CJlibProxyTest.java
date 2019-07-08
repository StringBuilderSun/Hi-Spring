package spring.road.beans.aop;

import net.sf.cglib.proxy.*;
import org.junit.Test;
import spring.road.beans.models.Person;
import spring.road.beans.models.UserDao;
import spring.road.beans.models.aop.TransactionManager;
import spring.road.beans.models.scan.GameService;

import java.lang.reflect.Method;

/**
 * CGLIB 动态代理验证
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class CJlibProxyTest {

    @Test
    public void testProxyCallBack() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GameService.class);
        enhancer.setCallback(new TransactionInterceptor());
        GameService gameService = (GameService) enhancer.create();
        Person person = new Person("lijinpeng", 26, false, new UserDao());
        gameService.setPerson(person);
        gameService.playGames();
    }

    //方法拦截器 会拦截 所有方法 ，所以需要加判断  cglib 还提供了filter过滤器 可以用于过滤指定方法
    public class TransactionInterceptor implements MethodInterceptor {
        TransactionManager tx = new TransactionManager();
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            tx.start();
            Object value = methodProxy.invokeSuper(o, objects);
            tx.commit();
            return value;
        }
    }

    @Test
    public void testProxyFilter() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GameService.class);
        Callback[] callbacks = {new TransactionInterceptor(), NoOp.INSTANCE};
        //设置回调函数集合
        enhancer.setCallbacks(callbacks);
        //根据回调过滤器返回指定回调函数索引
        enhancer.setCallbackFilter(new TransactionFilter());
        GameService gameService = (GameService) enhancer.create();
        Person person = new Person("lijinpeng", 26, false, new UserDao());
        gameService.setPerson(person);
        gameService.playGames();
    }

    public class TransactionFilter implements CallbackFilter {
        //返回回调函数在集合中的索引
        public int accept(Method method) {
            if ("playGames".equals(method.getName())) {
                return 0;
            } else {
                return 1;
            }
        }
    }
}
