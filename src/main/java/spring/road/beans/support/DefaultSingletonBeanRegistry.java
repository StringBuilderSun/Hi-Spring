package spring.road.beans.support;

import spring.road.beans.definition.SingletonBeanRegistry;
import spring.road.beans.exception.BeanCreateException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    /**
     * 容器
     */
    private Map<String, Object> singleBeansMaps = new ConcurrentHashMap<String, Object>(64);

    public void registerSingleton(String beanName, Object singletonObject) {
        //首先从容器中获取bean实例 如果获取到不允许注册直接抛异常   如果获取不到 执行注册操作
        Object target = singleBeansMaps.get(beanName);
        if (target != null) {
            throw new BeanCreateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + target + "] bound");
        }
        singleBeansMaps.put(beanName, singletonObject);
    }

    public Object getSingleton(String beanName) {
        return singleBeansMaps.get(beanName);
    }
}
