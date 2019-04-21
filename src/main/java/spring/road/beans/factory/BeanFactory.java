package spring.road.beans.factory;

import spring.road.beans.exception.NoSuchBeanDefinitionException;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public interface BeanFactory {
    Object getBean(String beanId);

    Class<?> getType(String name) throws NoSuchBeanDefinitionException, ClassNotFoundException;
}
