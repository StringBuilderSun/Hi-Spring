package spring.road.beans.support;

import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.factory.BeanFactory;

/**
 * Created by lijinpeng on 2019/4/26.
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanFactory {
    /**
     * 创建bean
     * 这个方法可以完成bean的实例化（注入）
     *
     * @param beanDefinition
     * @return
     */
    protected abstract Object createBean(BeanDefinition beanDefinition);
}
