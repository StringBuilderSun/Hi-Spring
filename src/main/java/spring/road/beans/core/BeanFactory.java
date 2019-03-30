package spring.road.beans.core;

import spring.road.beans.definition.BeanDefinition;

/**
 * Created by Administrator on 2019/3/29.
 */
public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);
}
