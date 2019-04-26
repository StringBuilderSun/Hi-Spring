package spring.road.beans.factory;

import spring.road.beans.exception.NoSuchBeanDefinitionException;

import java.util.List;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public interface BeanFactory {
    /**
     * 通过beanId 获取一个实例bean
     *
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    /**
     * 通过beanName 获取 bean的Class对象
     *
     * @param beanName
     * @return
     * @throws NoSuchBeanDefinitionException
     * @throws ClassNotFoundException
     */
    Class<?> getType(String beanName) throws NoSuchBeanDefinitionException, ClassNotFoundException;

    /**
     * 通过bean的type获取IOC中所有type实例
     *
     * @param type
     * @return
     */
    List<Object> getBeansByType(Class<?> type);
}
