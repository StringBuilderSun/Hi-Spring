package spring.road.beans.support;

import spring.road.beans.config.RuntimeBeanNameReference;
import spring.road.beans.config.TypedStringValue;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.factory.BeanFactory;
import spring.road.beans.factory.FactoryBean;

import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * 属性值转换解析器
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
public class BeanDefinitionValueResolver {
    private AbstractBeanFactory beanFactory;

    public BeanDefinitionValueResolver(AbstractBeanFactory beanFactory) {
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
        } else if (value instanceof BeanDefinition) {
            //如果参数是bean定义 需要从IOC中获取bean实例
            BeanDefinition bd = (BeanDefinition) value;
            String innnerBeanName = "(inner bean)" + bd.getBeanClassName() + "#" +
                    Integer.toHexString(System.identityHashCode(bd));
            value = resolveInnerBean(innnerBeanName, bd);
        }
        //属性值是否需要被转换
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        value = typeConverter.convertIfNecessary(value, requireType);
        return value;
    }

    /**
     * 解析为BeanDefinition的属性 并转换成具体类对象
     * 如果bean实现了FactoryBean 通过getObject获取
     * @param innnerBeanName
     * @param innerBd
     * @return
     */
    private Object resolveInnerBean(String innnerBeanName, BeanDefinition innerBd) {
        Object bean = this.beanFactory.createBean(innerBd);
        if (bean instanceof FactoryBean) {
            try {
                //通过自定义工厂获取bean对象
                return ((FactoryBean<?>) bean).getObject();
            } catch (Exception ex) {
                throw new BeanCreateException(innnerBeanName, "FactoryBean throw exception on object creation", ex);
            }

        } else {
            return bean;
        }
    }

}
