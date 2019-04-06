package spring.road.beans.support;

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
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value, Class<?> requireType) {
        if (value instanceof RuntimeBeanNameReference) {
            RuntimeBeanNameReference reference = (RuntimeBeanNameReference) value;
            value = beanFactory.getBean(reference.getBeanName());
            reference.setSource(value);
        } else if (value instanceof TypedStringValue) {
            //如果是String 无需做操作 需要实现类型转换哦
            TypedStringValue typedStringValue = (TypedStringValue) value;
            value = typedStringValue.getValue();
        }
        //属性值是否需要被转换
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        value = typeConverter.convertIfNecessary(value, requireType);
        return value;
    }

}
