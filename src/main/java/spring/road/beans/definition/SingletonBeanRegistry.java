package spring.road.beans.definition;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public interface SingletonBeanRegistry {
    /**
     * 单例注册
     *
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 获取单例对象
     *
     * @param beanName
     * @return
     */

    Object getSingleton(String beanName);
}
