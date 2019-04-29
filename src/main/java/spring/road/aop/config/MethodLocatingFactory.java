package spring.road.aop.config;

import lombok.Setter;
import spring.road.beans.config.BeanUtils;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.factory.BeanFactory;
import spring.road.beans.factory.BeanFactoryAware;
import spring.road.beans.factory.FactoryBean;
import spring.road.beans.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * 方法定位类
 * 给定目标类和目标方法名 获取方法对象
 * User: lijinpeng
 * Created by Shanghai on 2019/4/19.
 */
public class MethodLocatingFactory implements FactoryBean<Method>, BeanFactoryAware {
    @Setter
    private String targetBeanName;
    @Setter
    private String methodName;

    private Method method;

    /**
     * 设置beanfactofy 并完成方法实例化
     */
    public void setBeanFactory(BeanFactory beanFactory) {
        if (!StringUtils.hasText(this.targetBeanName)) {
            throw new IllegalArgumentException("Property 'targetBeanName' is required");
        }
        if (!StringUtils.hasText(this.methodName)) {
            throw new IllegalArgumentException("Property 'methodName' is required");
        }

        Class<?> beanClass = null;
        try {
            beanClass = beanFactory.getType(this.targetBeanName);
        } catch (ClassNotFoundException e) {
            throw new BeanCreateException(this.targetBeanName, "can get the bean from IOC", e);
        }
        if (beanClass == null) {
            throw new IllegalArgumentException("Can't determine type of bean with name '" + this.targetBeanName + "'");
        }
        //支持方法具有多个参数的功能
        this.method = BeanUtils.resolveSignature(this.methodName, beanClass);
        if (method == null) {
            throw new IllegalArgumentException("Unable to locate method [" + this.methodName +
                    "] on bean [" + this.targetBeanName + "]");
        }
    }

    public Method getObject() {
        return this.method;
    }

    public Class<?> getObjectType() {
        return Method.class;
    }
}
