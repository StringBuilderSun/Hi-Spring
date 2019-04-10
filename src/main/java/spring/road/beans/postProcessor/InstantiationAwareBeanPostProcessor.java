package spring.road.beans.postProcessor;

import spring.road.beans.exception.BeansException;

/**
 * 这个接口用于执行 类实例化 时候 所要对类执行的操作
 * 在这里是为属性自动注入时调用的方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    /**
     * 实例化之前调用
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 实例化之后调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    boolean afterInstantiation(Object bean, String beanName) throws BeansException;

    /**
     * 实例化调用
     * @param bean
     * @param beanName
     * @throws BeansException
     */
    void postProcessPropertyValues(Object bean, String beanName)
            throws BeansException;
}
