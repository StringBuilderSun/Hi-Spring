package spring.road.aop.framwork;

import net.sf.cglib.proxy.*;
import spring.road.aop.Advice;
import spring.road.aop.ReflectiveMethodInvocation;
import spring.road.aop.config.SpringNamingPolicy;


import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * CJLIB代理工厂
 * User: lijinpeng
 * Created by Shanghai on 2019/4/21.
 */
public class CglibProxyFactory implements AopProxyFactory {

    private AopConfig aopConfig;

    /**
     * AOP代理callback 索引
     */
    private static final int AOP_PROXY = 0;

    public CglibProxyFactory(AopConfig aopConfig) {
        this.aopConfig = aopConfig;
    }

    public Object getProxy() {
        return getProxy(null);
    }

    public Object getProxy(ClassLoader classLoader) {
        //CGLIB代理 1、设置回调函数 2 设置过滤器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(aopConfig.getTargetClass());
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE); //"BySpringCGLIB"
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setCallbacks(getCallbacks());
        enhancer.setCallbackFilter(new ProxyCallbackFilter());
        return enhancer.create();
    }

    private Callback[] getCallbacks() {
        DynamicAdvisedInterceptor advisedInterceptor = new DynamicAdvisedInterceptor(aopConfig);
        Callback[] callbacks = new Callback[]{advisedInterceptor};
        return callbacks;
    }

    /**
     * 静态内部类 用于获取Callbacks
     */
    public static class DynamicAdvisedInterceptor implements MethodInterceptor, Serializable {
        private final AopConfig config;

        public DynamicAdvisedInterceptor(AopConfig config) {
            this.config = config;
        }

        /**
         * CGLIB 会拦截每个方法的执行， 最终执行切入方法
         *
         * @param o
         * @param method
         * @param objects
         * @param methodProxy
         * @return
         * @throws Throwable
         */
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            Object target = this.config.getTargetObject();
            List<Advice> advices = config.getAdvices(method);
            if (advices.size() == 0) {
                return methodProxy.invoke(target, objects);
            } else {
                List<org.aopalliance.intercept.MethodInterceptor> interceptors = new ArrayList<org.aopalliance.intercept.MethodInterceptor>();
                interceptors.addAll(advices);
                return new ReflectiveMethodInvocation(target, method, objects, interceptors).proceed();
            }
        }
    }

    public static class ProxyCallbackFilter implements CallbackFilter {
        /**
         * 拦截所有方法 所有方法都会调用AOP过滤器
         * 具体是否曾强通过 pointcut 判断
         * @param method
         * @return
         */
        public int accept(Method method) {
            return AOP_PROXY;
        }
    }
}
