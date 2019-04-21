package spring.road.aop.framwork;

import spring.road.aop.Advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * User: lijinpeng
 * Created by Shanghai on 2019/4/21.
 */
public class AopSupport implements AopConfig {


    private boolean proxyTargetClass = false;


    private Object targetObject = null;

    private List<Advice> advices = new ArrayList<Advice>();

    private List<Class> interfaces = new ArrayList<Class>();

    public Class<?> getTargetClass() {
        return this.targetObject.getClass();
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public List<Advice> getAdvices() {
        return this.advices;
    }

    public List<Advice> getAdvices(Method method) {
        List<Advice> advices = new ArrayList<Advice>();
        for (Advice advice : getAdvices()) {
            if (advice.getPointcut().getMethodMatcher().matches(method)) {
                advices.add(advice);
            }
        }
        return advices;
    }

    public void addAdvice(Advice advice) {
        advices.add(advice);
    }

    public void setTargetObject(Object target) {
        this.targetObject = target;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public Class<?>[] getProxiedInterfaces() {
        return this.interfaces.toArray(new Class[this.interfaces.size()]);
    }

    public boolean isInterfaceProxied(Class<?> intf) {
        for (Class proxyIntf : this.interfaces) {
            if (intf.isAssignableFrom(proxyIntf)) {
                return true;
            }
        }
        return false;
    }

    public void addInterface(Class<?> intf) {
        if (!intf.isInterface()) {
            throw new IllegalArgumentException("[" + intf.getName() + "] is not an interface");
        }
        if (!this.interfaces.contains(intf)) {
            this.interfaces.add(intf);

        }
    }
}
