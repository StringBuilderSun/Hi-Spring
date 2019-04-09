package spring.road.beans.support;

import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;

/**
 * beanName 生成器 获取bean 定义的名字
 * Created by lijinpeng on 2019/4/9.
 */
public interface BeanNameGenerator {
    /**
     *根据 BeanDefinition类型获取beanName
     * @param definition
     * @param registry
     * @return
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);
}
