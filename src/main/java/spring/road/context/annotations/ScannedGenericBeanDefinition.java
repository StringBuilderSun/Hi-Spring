package spring.road.context.annotations;

import spring.road.beans.definition.GenericBeanDefinition;
import spring.road.core.type.AnnotationMetadata;

/**
 * 扫描类注解声明
 * Created by lijinpeng on 2019/4/9.
 */
public class ScannedGenericBeanDefinition extends GenericBeanDefinition implements AnnotatedBeanDefinition {
    /**
     * 注解类的元信息
     */
    private AnnotationMetadata annotationMetadata;

    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }

    public ScannedGenericBeanDefinition(AnnotationMetadata annotationMetadata) {
        super();
        this.annotationMetadata = annotationMetadata;
        setBeanClassName(annotationMetadata.getClassName());
    }
}
