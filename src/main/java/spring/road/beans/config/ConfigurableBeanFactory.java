package spring.road.beans.config;

import spring.road.beans.factory.AutowireCapableBeanFactory;
import spring.road.beans.postProcessor.BeanPostProcessor;

import java.util.List;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();

    /**
     * 添加bean 生命周期处理器
     * @param postProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    /**
     * 获取bean生命周期处理器
     * @return
     */
    List<BeanPostProcessor> getBeanPostProcessors();
}
