package spring.road.beans.support;

import org.apache.commons.lang3.StringUtils;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.utils.ClassUtils;
import spring.road.context.annotations.AnnotatedBeanDefinition;
import spring.road.core.annotations.AnnotationAttributes;
import spring.road.core.type.AnnotationMetadata;

import java.beans.Introspector;
import java.util.Set;

/**
 * beanName 获取 实现类
 * 根据BeanDefinition 类型获取
 * Created by lijinpeng on 2019/4/9.
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            String beanName = determineBeanNameFromAnnotation((AnnotatedBeanDefinition) definition);
            if (StringUtils.isNotEmpty(beanName)) {
                return beanName;
            }
        }
        return buildDefaultBeanName(definition);
    }

    /**
     * 获取注解bean的beanName
     *
     * @param definition
     * @return
     */
    private String determineBeanNameFromAnnotation(AnnotatedBeanDefinition definition) {
        AnnotationMetadata amd = definition.getAnnotationMetadata();
        Set<String> types = amd.getAnnotationTypes();
        String beanName = null;
        for (String type : types) {
            AnnotationAttributes attributes = amd.getAnnotationAttributes(type);
            if (attributes.get("value") != null) {
                Object value = attributes.get("value");
                if (value instanceof String) {
                    String strVal = (String) value;
                    if (StringUtils.isNotEmpty(strVal)) {
                        beanName = strVal;
                    }
                }
            }
        }
        return beanName;
    }

    /**
     * 如果注解未配置value属性  直接获取类的段名称
     *
     * @param definition
     * @return
     */
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
        return Introspector.decapitalize(shortClassName);
    }
}
