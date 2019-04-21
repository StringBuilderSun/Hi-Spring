package spring.road.aop.aspectj;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * 方法执行之前的切入
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class AspectJBeforeAdvise extends AbstractAspectJAdvice {


    public AspectJBeforeAdvise(Object adviseObject, Method adviseMethod, AspectJExpressionPointcut pointcut) {
        super(adviseObject, adviseMethod, pointcut);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        //执行切入方法
        this.invokeAdviseMethod();
        //目标方法
        Object o = invocation.proceed();
        return o;
    }
}
