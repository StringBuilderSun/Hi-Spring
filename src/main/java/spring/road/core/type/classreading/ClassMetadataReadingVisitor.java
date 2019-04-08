package spring.road.core.type.classreading;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import org.springframework.asm.Opcodes;
import spring.road.beans.utils.ClassUtils;
import spring.road.core.type.ClassMetadata;

/**
 * 当asm解析类的字节码文件时候,解析过程会调用ClassVisitor的visit一系列方法
 * 此类只重写visit方法获取 类的基本信息
 * 类的字段 或者注解获取 参见AnnotationMetadataReadingVisitor实现
 * User: lijinpeng
 * Created by Shanghai on 2019/4/8.
 */
public class ClassMetadataReadingVisitor extends ClassVisitor implements ClassMetadata {
    private String className;

    private boolean isInterface;

    private boolean isAbstract;

    private boolean isFinal;

    private String superClassName;

    private String[] interfaces;

    public ClassMetadataReadingVisitor() {
        super(SpringAsmInfo.ASM_VERSION);
    }

    public String getClassName() {
        return className;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public String[] getInterfaceNames() {
        return interfaces;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String supername, String[] interfaces) {
        this.className = ClassUtils.convertResourcePathToClassName(name);
        this.isInterface = ((access & Opcodes.ACC_INTERFACE) != 0);
        this.isAbstract = ((access & Opcodes.ACC_ABSTRACT) != 0);
        this.isFinal = ((access & Opcodes.ACC_FINAL) != 0);
        if (supername != null) {
            this.superClassName = ClassUtils.convertResourcePathToClassName(supername);
        }
        this.interfaces = new String[interfaces.length];
        for (int i = 0; i < interfaces.length; i++) {
            this.interfaces[i] = ClassUtils.convertResourcePathToClassName(interfaces[i]);
        }
    }
}
