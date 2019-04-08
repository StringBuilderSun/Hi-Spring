package spring.road.core.type;

import spring.road.core.annotations.AnnotationAttributes;

import java.util.Set;

/**
 * 类注解属性元数据
 * 继承了类的元数据信息
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public interface AnnotationMetadata extends ClassMetadata {
    /**
     * 获取这个类的上的所有注解类名
     *
     * @return
     */
    Set<String> getAnnotationTypes();

    /**
     * 通过注解类判断这个类是否被该注解类注解
     *
     * @param annotationType
     * @return
     */
    boolean hasAnnotation(String annotationType);

    /**
     * 通过注解类名 获取这个注解类的所有属性
     *
     * @param annotationType
     * @return
     */
    AnnotationAttributes getAnnotationAttributes(String annotationType);
}
