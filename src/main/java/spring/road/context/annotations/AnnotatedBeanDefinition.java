package spring.road.context.annotations;

import spring.road.beans.definition.BeanDefinition;
import spring.road.core.type.AnnotationMetadata;

/**
 * 带注解修饰的bean声明 可以获取ban的注解属性
 * Created by lijinpeng on 2019/4/9.
 */
public interface AnnotatedBeanDefinition extends BeanDefinition {
    AnnotationMetadata getAnnotationMetadata();
}
