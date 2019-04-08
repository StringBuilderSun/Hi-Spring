package spring.road.core.type.classreading;

import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import jdk.internal.org.objectweb.asm.Type;
import spring.road.core.annotations.AnnotationAttributes;
import spring.road.core.type.AnnotationMetadata;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 当asm解析类的字节码文件时候,解析过程会调用ClassVisitor的visit一系列方法
 * 此类只重写visitAnnotation方法 当读取到类上的注解时 会调用此类的visitAnnotation
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata {
    /**
     * 该类所包含的注解类的集合
     */
    private final Set<String> annotationTypes = new HashSet<String>(4);
    /**
     * 包含了每个注解类和该注解类所有属性的特性集合
     */
    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public Set<String> getAnnotationTypes() {
        return annotationTypes;
    }

    /**
     * 是否包含某个注解类
     *
     * @param annotationType
     * @return
     */
    public boolean hasAnnotation(String annotationType) {
        return this.annotationTypes.contains(annotationType);
    }

    /**
     * 根据注解类名 获取这个注解类下的所有属性值
     *
     * @param annotationType
     * @return
     */
    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributeMap.get(annotationType);
    }

    /**
     * 每次当asm读取到类的注解时 调用该方法
     * 通过此方法 将注解类 和注解类的属性放入到AnnotationAttributes中
     *
     * @param desc    注解的类名
     * @param visible 是否可见
     * @return
     */
    @Override
    public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
        String annotationType = Type.getType(desc).getClassName();
        this.annotationTypes.add(annotationType);
      return new AnnotationAttributesReadingVisitor(annotationType, attributeMap);
    }
}
