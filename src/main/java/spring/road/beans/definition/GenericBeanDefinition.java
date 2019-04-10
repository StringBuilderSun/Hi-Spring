package spring.road.beans.definition;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import spring.road.beans.config.BeanScopConstant;
import spring.road.beans.config.ConstructorArgument;
import spring.road.beans.config.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
@Setter
public class GenericBeanDefinition implements BeanDefinition {
    private String beanName;
    private String beanClassName;
    private Class<?> beanClass;
    private String scope = BeanScopConstant.SINGLETON_SCOPE;
    //属性集合
    List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();
    //构造器
    ConstructorArgument constructorArgument = new ConstructorArgument();
    /**
     * 默认是单例模式
     */
    private boolean singleton = true;
    /**
     * 原型模式
     */
    private boolean prototype = false;

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(String beanName, String beanClass) {
        this.beanClassName = beanClass;
        this.beanName = beanName;
        propertyValueList = new ArrayList<PropertyValue>();
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setBeanClassName(String className) {
        this.beanClassName = className;
    }

    public boolean isSingleScope() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public String getScope() {
        return scope;
    }

    public List<PropertyValue> getpropertyValueList() {
        return propertyValueList;
    }

    public ConstructorArgument getConstructorArgument() {
        return constructorArgument;
    }

    public boolean hasConstructorArgumentValues() {
        return !(constructorArgument.isEmpty());
    }

    public Class<?> getBeanClass() throws IllegalStateException {
        if(this.beanClass == null){
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }


    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String beanClassName = this.getBeanClassName();
        this.beanClass = classLoader.loadClass(beanClassName);
        return this.beanClass;
    }

    public boolean hasBeanClass() {
        return this.beanClass != null;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = BeanScopConstant.SINGLETON_SCOPE.equals(scope) || BeanScopConstant.SCOPE_DEFAULT.equals(scope);
        this.prototype = StringUtils.equals(BeanScopConstant.PROTOTYPE_SCOPE, scope);
    }
}
