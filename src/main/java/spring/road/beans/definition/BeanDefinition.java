package spring.road.beans.definition;

import spring.road.beans.config.ConstructorArgument;
import spring.road.beans.config.PropertyValue;

import java.util.List;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public interface BeanDefinition {
    /**
     * 获取beanName
     *
     * @return
     */
    String getBeanName();

    /**
     * 获取className
     *
     * @return
     */
    String getBeanClassName();

    void setBeanClassName(String className);

    /**
     * 设置类的作用域
     *
     * @param scope
     */
    void setScope(String scope);

    /**
     * 是否是单例模式
     *
     * @return
     */
    boolean isSingleScope();

    /**
     * 获取scope
     *
     * @return
     */
    String getScope();

    /**
     * 获取bean定义的属性文件
     *
     * @return
     */
    List<PropertyValue> getpropertyValueList();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();

}
