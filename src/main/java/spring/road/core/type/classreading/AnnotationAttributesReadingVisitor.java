package spring.road.core.type.classreading;


import jdk.internal.org.objectweb.asm.AnnotationVisitor;
import spring.road.core.annotations.AnnotationAttributes;

import java.util.Map;

/**
 * 该类主要用户asm读取一个注解的时候，当获取到注解的属性值时 调用visit方法
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    /**
     * 注解类的类名
     */
    private final String annotationType;
    /**
     * 该注解类所有属性最终要放到 属于 asm解析类的包含所有注解类和对应注解属性的集合里
     */
    private final Map<String, AnnotationAttributes> attributesMap;

    AnnotationAttributes attributes = new AnnotationAttributes();

    public AnnotationAttributesReadingVisitor(String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);
        this.attributesMap = attributesMap;
        this.annotationType = annotationType;
    }

    public void visit(String attributeName, Object attributeValue) {
        attributes.put(attributeName, attributeValue);
    }

    /**
     * 注解所有属性读取完毕后 执行
     */
    public void visitEnd() {
        this.attributesMap.put(this.annotationType, attributes);
    }
}
