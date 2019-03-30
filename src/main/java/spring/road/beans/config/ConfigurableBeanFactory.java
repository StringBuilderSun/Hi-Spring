package spring.road.beans.config;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/30.
 */
public interface ConfigurableBeanFactory {
    void setClassLoader(ClassLoader classLoader);

    ClassLoader getClassLoader();
}
