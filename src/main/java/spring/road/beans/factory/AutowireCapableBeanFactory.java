package spring.road.beans.factory;

import spring.road.beans.config.DependencyDescriptor;

/**
 * 提供可以自动完成将使用注解的方法或者字段的注入
 * Created by lijinpeng on 2019/4/10.
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 根据属性依赖描述符 从ICO中查找对应的bean完成注入
     *
     * @param descriptor
     * @return
     */
    Object resolveDependency(DependencyDescriptor descriptor) throws ClassNotFoundException;
}
