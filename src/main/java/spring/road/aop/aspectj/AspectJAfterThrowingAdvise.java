package spring.road.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 目标方法抛出异常之后 执行切入方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJAfterThrowingAdvise extends AbstractAspectJAdvice {
    public AspectJAfterThrowingAdvise(Object adviseObject, Method adviseMethod, AspectJExpressionPointcut pointcut) {
        super(adviseObject, adviseMethod, pointcut);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable e) {
            this.invokeAdviseMethod();
            throw e;
        }
    }
}
