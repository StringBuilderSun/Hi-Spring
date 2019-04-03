package spring.road.beans.support;

import lombok.extern.slf4j.Slf4j;
import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.config.PropertyValue;
import spring.road.beans.core.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
@Slf4j
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements BeanDefinitionRegistry, ConfigurableBeanFactory, BeanFactory {
    /**
     * beans 声明集合
     */
    private Map<String, BeanDefinition> beanDefinitionsMap = new ConcurrentHashMap<String, BeanDefinition>();
    /**
     * 类加载器
     */
    private ClassLoader classLoader;

    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionsMap.get(beanId);
    }

    public void beanDefinationRegister(String beanName, BeanDefinition definition) {
        beanDefinitionsMap.put(beanName, definition);
    }

    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = beanDefinitionsMap.get(beanId);
        if (beanDefinition.isSingleScope()) {
            //如果是单例模式 先去查找单利容器里面是否存在
            Object target = this.getSingleton(beanId);
            if (target == null) {
                target = createBean(beanDefinition);
                this.registerSingleton(beanId, target);
            }
            return target;
        }
        return createBean(beanDefinition);
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Object bean = initBean(beanDefinition);
        return populateBean(beanDefinition, bean);

    }

    /**
     * 通过反射为bean实现注入
     *
     * @param beanDefinition
     * @param bean
     * @return
     */
    public Object populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getpropertyValueList();
        if (propertyValues.size() == 0) {
            return bean;
        }
        try {
            BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
            //通过反反射获取类的所有属性
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] propertyDesciptors = beanInfo.getPropertyDescriptors();
            //双循环匹配beanDefinition的类定义  和 通过反射获取的类  然后注入属性值
            for (PropertyValue pv : propertyValues) {
                for (PropertyDescriptor pd : propertyDesciptors) {
                    if (pd.getName().equals(pv.getName())) {
                        pd.getWriteMethod().invoke(bean, valueResolver.resolveValueIfNecessary(pv, pd.getPropertyType()));
                    }
                }
            }
        } catch (Exception ex) {
            throw new BeanCreateException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", ex);
        }
        return bean;
    }

    /**
     * 初始化bean 通过反射加载出一个bean实例
     *
     * @param beanDefinition
     * @return
     */
    public Object initBean(BeanDefinition beanDefinition) {
        if (beanDefinition == null) {
            return null;
        }
        String beanName = beanDefinition.getBeanClassName();
        try {
            Class<?> objectBean = getClassLoader().loadClass(beanName);
            return objectBean.newInstance();
        } catch (Exception e) {
            throw new BeanCreateException(beanName, e.getMessage(), e);
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }
}
