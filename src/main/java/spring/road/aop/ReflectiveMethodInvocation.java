package spring.road.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/20.
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    /**
     * 切入的目标类对象 （即是被拦截的类对象实例）
     */
    private Object targetObject;
    /**
     * 切入点方法(即是要增强的方法)
     */
    private Method targetMethod;
    /**
     * 方法参数
     */
    protected Object[] arguments;


    /**
     * 为切入点方法要增加的方法执行器
     * 切入点方法可以执行前置、后置、异常等多个方法
     */
    private List<MethodInterceptor> interceptors;
    /**
     * 当前执行的advise 集合中的位置
     */
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(Object targetObject, Method targetMethod, Object[] arguments, List<MethodInterceptor> interceptors) {
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
        this.interceptors = interceptors;
    }

    public Method getMethod() {
        return this.targetMethod;
    }

    public Object[] getArguments() {
        return (this.arguments != null ? this.arguments : new Object[0]);
    }

    /**
     * 核心方法 控制链式调用
     * 由于advise都最终调用方法MethodInterceptor 的 invoke(MethodInvocation invocation)
     * 所以可以将该类作为参数传入advise中 从而达到循环调用proceed方法的目的
     *
     * @return
     * @throws Throwable
     */
    public Object proceed() throws Throwable {
        if (this.currentInterceptorIndex == interceptors.size() - 1) {
            return this.invokeJoinpoint();
        }
        this.currentInterceptorIndex++;
        MethodInterceptor interceptor = interceptors.get(this.currentInterceptorIndex);
        return interceptor.invoke(this);
    }

    public Object getThis() {
        return this.targetObject;
    }

    public AccessibleObject getStaticPart() {
        return this.targetMethod;
    }

    /**
     * 执行切入点方法
     */
    protected Object invokeJoinpoint() throws Throwable {
        return this.targetMethod.invoke(this.targetObject, this.arguments);
    }

}
