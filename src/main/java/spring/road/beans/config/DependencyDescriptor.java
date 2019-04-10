package spring.road.beans.config;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 字段依赖描述符
 * Created by lijinpeng on 2019/4/10.
 */
public class DependencyDescriptor {
    /**
     * 是否要求必须赋值
     */
    private boolean required;
    /**
     * 类属性字段
     */
    private Field field;

    public DependencyDescriptor(Field field, boolean required) {
        this.required = required;
        this.field = field;
    }

    /**
     * 获取属性字段类型  方便根据类型从IOC中查找合适的类注入
     *
     * @return
     */
    public Class<?> getDependencyType() {
        if (field != null) {
            return field.getType();
        }
        throw new RuntimeException("仅支持字段类型解析，当前属性字段为null:");
    }

    public boolean isRequired() {
        return required;
    }
}
