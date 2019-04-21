package spring.road.aop.aspectj;

import spring.road.aop.Advice;
import spring.road.aop.Pointcut;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 切入抽象类
 * 传入切入方法和 方法对象 执行切入方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public abstract class AbstractAspectJAdvice implements Advice {

    /**
     * 切入方法的类对象
     */
    private Object adviseObject;
    /**
     *
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 切入要执行的方法
     */
    private Method adviseMethod;

    public AbstractAspectJAdvice(Object adviseObject, Method adviseMethod, AspectJExpressionPointcut pointcut) {
        this.adviseObject = adviseObject;
        this.pointcut = pointcut;
        this.adviseMethod = adviseMethod;
    }


    public Method getAdviseMethod() {
        return adviseMethod;
    }

    /**
     * 执行切入方法
     *
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void invokeAdviseMethod() throws InvocationTargetException, IllegalAccessException {
        adviseMethod.invoke(adviseObject);
    }

    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
