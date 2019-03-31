package spring.road.beans.definition;

import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import spring.road.beans.config.BeanScopConstant;
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
    private String beanClass;
    private String scope = BeanScopConstant.SINGLETON_SCOPE;
    List<PropertyValue> propertyValueList;
    /**
     * 默认是单例模式
     */
    private boolean singleton = true;
    /**
     * 原型模式
     */
    private boolean prototype = false;

    public GenericBeanDefinition(String beanName, String beanClass) {
        this.beanClass = beanClass;
        this.beanName = beanName;
        propertyValueList = new ArrayList<PropertyValue>();
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanClassName() {
        return beanClass;
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

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = BeanScopConstant.SINGLETON_SCOPE.equals(scope) || BeanScopConstant.SCOPE_DEFAULT.equals(scope);
        this.prototype = StringUtils.equals(BeanScopConstant.PROTOTYPE_SCOPE, scope);
    }
}
