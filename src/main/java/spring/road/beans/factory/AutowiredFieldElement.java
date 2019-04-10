package spring.road.beans.factory;

import spring.road.beans.annation.InjectionElement;
import spring.road.beans.config.DependencyDescriptor;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.utils.ReflectionUtils;

import java.lang.reflect.Field;


/**
 * 字段注入实现类
 * Created by lijinpeng on 2019/4/10.
 */
public class AutowiredFieldElement extends InjectionElement {
    /**
     * 字段是否要求注入
     */
    private boolean required;

    public AutowiredFieldElement(Field field, boolean required, AutowireCapableBeanFactory factory) {
        super(field, factory);
        this.required = required;
    }



    public Field getFiled() {
        return (Field) this.member;
    }

    /**
     * 根据字段类型从bean工厂获取bean实例 注入
     *
     * @param target
     */
    public void inject(Object target) {
        Field field = this.getFiled();
        try {
            DependencyDescriptor descriptor = new DependencyDescriptor(field, required);
            //通过工厂根据字段类型查找对应可注入的bean
            Object value = this.factory.resolveDependency(descriptor);
            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                field.set(target, value);
            }

        } catch (Throwable ex) {
            throw new BeanCreateException("无法创建注解字段: " + field, ex);
        }

    }
}
