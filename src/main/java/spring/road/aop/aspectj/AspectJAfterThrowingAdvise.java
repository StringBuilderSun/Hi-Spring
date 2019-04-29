package spring.road.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import spring.road.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * 目标方法抛出异常之后 执行切入方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJAfterThrowingAdvise extends AbstractAspectJAdvice {
    public AspectJAfterThrowingAdvise(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
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
