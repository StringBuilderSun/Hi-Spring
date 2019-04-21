package spring.road.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 目标方法执行之后 执行切入方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJAfterReturningAdvise extends AbstractAspectJAdvice {
    public AspectJAfterReturningAdvise(Object adviseObject, Method adviseMethod, AspectJExpressionPointcut pointcut) {
        super(adviseObject, adviseMethod, pointcut);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object o = invocation.proceed();
        this.invokeAdviseMethod();
        return o;
    }
}
