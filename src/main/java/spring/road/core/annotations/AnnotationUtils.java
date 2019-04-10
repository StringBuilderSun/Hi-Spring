package spring.road.core.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * User: lijinpeng
 * Created by Shanghai on 2019/4/10.
 */
public class AnnotationUtils {
    //判断元素类型是否使用指定注解，获取该注解元素
    public static <T extends Annotation> T getAnnotation(AnnotatedElement ae, Class<T> annotationType) {
        T ann = ae.getAnnotation(annotationType);
        if (ann == null) {
            for (Annotation metaAnn : ae.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(annotationType);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann;
    }
}
