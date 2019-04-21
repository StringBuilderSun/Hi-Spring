package spring.road.aop;


import org.aopalliance.intercept.MethodInterceptor;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public interface Advice extends MethodInterceptor {
    /**
     * 获取一个切入点
     *
     * @return
     */
    Pointcut getPointcut();
}
