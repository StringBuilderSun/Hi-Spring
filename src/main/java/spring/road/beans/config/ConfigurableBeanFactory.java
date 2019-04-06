package spring.road.beans.config;

import spring.road.beans.core.BeanFactory;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface ConfigurableBeanFactory extends BeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();
}
