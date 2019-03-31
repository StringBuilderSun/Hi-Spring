package spring.road.beans.definition;

import spring.road.beans.config.PropertyValue;
import spring.road.beans.config.RuntimeBeanNameReference;
import spring.road.beans.config.TypedStringValue;
import spring.road.beans.core.BeanFactory;

/**
 * 属性值转换解析器
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class BeanDefinitionValueResolver {
    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;

    }

    /**
     * 将声明阶段的beanDefinition里面的属性转换成对应真实属性
     *
     * @param propertyValue
     * @return
     */
    public Object resolveValueIfNecessary(PropertyValue propertyValue) {
        Object beanClass = propertyValue.getValue();
        if (beanClass instanceof RuntimeBeanNameReference) {
            RuntimeBeanNameReference reference = (RuntimeBeanNameReference) beanClass;
            beanClass = beanFactory.getBean(reference.getBeanName());
            reference.setSource(beanClass);
        } else if (beanClass instanceof TypedStringValue) {
            //如果是String 无需做操作 需要实现类型转换哦
            TypedStringValue typedStringValue = (TypedStringValue) beanClass;
            beanClass = typedStringValue.getValue();
        }
        propertyValue.setConvertedValue(beanClass);
        return beanClass;
    }

}
