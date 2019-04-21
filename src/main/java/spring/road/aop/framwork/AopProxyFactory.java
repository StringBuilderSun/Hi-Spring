package spring.road.aop.framwork;

/**
 * 获取切入类的代理对象
 * User: lijinpeng
 * Created by Shanghai on 2019/4/21.
 */
public interface AopProxyFactory {

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
