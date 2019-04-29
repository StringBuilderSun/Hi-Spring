package spring.road.aop.aspectj;

import lombok.Setter;
import spring.road.aop.Advice;
import spring.road.aop.MethodMatcher;
import spring.road.aop.Pointcut;
import spring.road.aop.framwork.AopConfig;
import spring.road.aop.framwork.AopProxyFactory;
import spring.road.aop.framwork.AopSupport;
import spring.road.aop.framwork.CglibProxyFactory;
import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.exception.BeansException;
import spring.road.beans.postProcessor.BeanPostProcessor;
import spring.road.beans.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * beanPost 处理器
 * 在bean初始化之后 在后置方法里为符合ex表达式条件的类创建代理
 * Created by lijinpeng on 2019/4/28.
 */
public class AspectJAutoProxyCreator implements BeanPostProcessor {
    @Setter
    private ConfigurableBeanFactory beanFactory;

    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * AOP具体拦截点为符合条件的bean创建代理
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        //如果这个Bean本身就是Advice及其子类，那就不要再生成动态代理了。
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        //获取实例bean上的advise,如果有创建代理 否则 不用创建代理
        List<Advice> advices = getCandidateAdvices(bean);
        if (advices.isEmpty()) {
            return bean;
        }
        return createProxy(advices, bean);
    }

    private Object createProxy(List<Advice> advices, Object bean) {
        AopSupport config = new AopSupport();
        for (Advice advice : advices) {
            config.addAdvice(advice);
        }
        Set<Class> targetInterfaces = ClassUtils.getAllInterfacesForClassAsSet(bean.getClass());
        for (Class<?> targetInterface : targetInterfaces) {
            config.addInterface(targetInterface);
        }
        config.setTargetObject(bean);
        AopProxyFactory proxyFactory = null;
        if (config.getProxiedInterfaces().length == 0) {
            proxyFactory = new CglibProxyFactory(config);
        } else {
            //需要实现JDK代理
//            proxyFactory = new JdkAopProxyFactory(config);
        }
        return proxyFactory.getProxy();
    }

    private List<Advice> getCandidateAdvices(Object bean) {
        List<Object> advises = this.beanFactory.getBeansByType(Advice.class);
        List<Advice> result = new ArrayList<Advice>();
        for (Object advise : advises) {
            Pointcut pointcut = ((Advice) advise).getPointcut();
            //bean是否符合express拦截条件
            if (canApply(pointcut, bean.getClass())) {
                result.add((Advice) advise);
            }
        }
        return result;
    }

    private boolean isInfrastructureClass(Class<?> aClass) {
        return Advice.class.isAssignableFrom(aClass);
    }

    /**
     * 检查目标类的方法是否 符合 切入点的条件
     * 如果符合将切入点 加入到目标类的切面集合中
     *
     * @param pc
     * @param targetClass
     * @return
     */
    public static boolean canApply(Pointcut pc, Class<?> targetClass) {
        MethodMatcher methodMatcher = pc.getMethodMatcher();

        LinkedHashSet<Class> classes = new LinkedHashSet<Class>(ClassUtils.getAllInterfacesAsSet(targetClass));
        classes.add(targetClass);
        for (Class<?> clazz : classes) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (methodMatcher.matches(method)) {
                    return true;
                }
            }
        }
        return false;
    }
}
