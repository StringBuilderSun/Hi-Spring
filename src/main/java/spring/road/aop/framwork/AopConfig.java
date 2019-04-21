package spring.road.aop.framwork;

import spring.road.aop.Advice;

import java.lang.reflect.Method;
import java.util.List;

/**
 * AOP配置类
 * 可以通过此接口获取切入点的advises
 * 增加advise 获取切入点目标类等
 * User: lijinpeng
 * Created by Shanghai on 2019/4/21.
 */
public interface AopConfig {
    /**
     * 获取切入点所在类class对象
     * 即是被拦截的方法所在的类对象
     *
     * @return
     */
    Class<?> getTargetClass();

    /**
     * 获取要增强的类对象
     *
     * @return
     */
    Object getTargetObject();

    /**
     * 获取该类方法所有的advise
     *
     * @return
     */
    List<Advice> getAdvices();

    /**
     * 通过切入点方法获取该方法的所有advise
     *
     * @param method
     * @return
     */
    List<Advice> getAdvices(Method method);

    /**
     * 添加切点方法
     *
     * @param advice
     */
    void addAdvice(Advice advice);

    /**
     * 设置切入类对象
     *
     * @param target
     */
    void setTargetObject(Object target);

    boolean isProxyTargetClass();

    Class<?>[] getProxiedInterfaces();

    boolean isInterfaceProxied(Class<?> intf);
}
