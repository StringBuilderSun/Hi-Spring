package spring.road.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;
import spring.road.aop.config.AspectInstanceFactory;

import java.lang.reflect.Method;

/**
 * 方法执行之前的切入
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJBeforeAdvise extends AbstractAspectJAdvice {


    public AspectJBeforeAdvise(Method adviceMethod, AspectJExpressionPointcut pointcut, AspectInstanceFactory adviceObjectFactory) {
        super(adviceMethod, pointcut, adviceObjectFactory);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        //执行切入方法
        this.invokeAdviseMethod();
        //目标方法
        Object o = invocation.proceed();
        return o;
    }
}
