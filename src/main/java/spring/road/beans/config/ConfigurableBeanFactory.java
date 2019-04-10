package spring.road.beans.config;

import spring.road.beans.factory.AutowireCapableBeanFactory;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface ConfigurableBeanFactory extends AutowireCapableBeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();
}
