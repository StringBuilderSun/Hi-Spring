package spring.road.beans.postProcessor;

import spring.road.beans.exception.BeansException;

/**
 * Bean的生命周期 可以分为 类的初始化   类的实例化  类的销毁
 *这个接口定义了类的初始化前和初始化之后应该执行的操作+
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public interface BeanPostProcessor {
    /**
     * 初始化之前调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 初始化之后调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
