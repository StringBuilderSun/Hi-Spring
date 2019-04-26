package spring.road.beans.support;

import lombok.extern.slf4j.Slf4j;
import spring.road.beans.config.ConfigurableBeanFactory;
import spring.road.beans.config.DependencyDescriptor;
import spring.road.beans.config.PropertyValue;
import spring.road.beans.exception.NoSuchBeanDefinitionException;
import spring.road.beans.factory.BeanFactory;
import spring.road.beans.definition.BeanDefinition;
import spring.road.beans.definition.BeanDefinitionRegistry;
import spring.road.beans.exception.BeanCreateException;
import spring.road.beans.postProcessor.BeanPostProcessor;
import spring.road.beans.postProcessor.InstantiationAwareBeanPostProcessor;
import spring.road.beans.utils.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: StringBuilderSun
 * Created by Shanghai on 2019/3/31.
 */
@Slf4j
public class DefaultBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {
    /**
     * beans 声明集合
     */
    private Map<String, BeanDefinition> beanDefinitionsMap = new ConcurrentHashMap<String, BeanDefinition>();
    /**
     * 类加载器
     */
    private ClassLoader classLoader;
    /**
     * bean生命周期处理器
     */
    private LinkedList<BeanPostProcessor> beanPostProcessors = new LinkedList<BeanPostProcessor>();

    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionsMap.get(beanName);
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

    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            throw new NoSuchBeanDefinitionException(name);
        }
        bd.resolveBeanClass(getClassLoader());
        return bd.getBeanClass();
    }

    /**
     * 通过class查询IOC中符合条件的beanId
     *
     * @param type
     * @return
     * @throws ClassNotFoundException
     */
    private List<String> getBeanIDsByType(Class<?> type) {
        List<String> result = new ArrayList<String>();
        for (String beanName : this.beanDefinitionsMap.keySet()) {
            //如果参数type 能够被赋值 进IOC的 bean中 则证明符合条件
            if (type.isAssignableFrom(this.getType(beanName))) {
                result.add(beanName);
            }
        }
        return result;
    }

    public List<Object> getBeansByType(Class<?> type) {
        List<Object> beanInstances = new ArrayList();
        List<String> beanIDsByType = this.getBeanIDsByType(type);
        for (String beanId : beanIDsByType) {
            beanInstances.add(this.getBean(beanId));
        }
        return beanInstances;
    }

    public Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
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
        //使用扫描包的方式获取实例化bean 通过ScannedGenericBeanDefinition 初始化bean
        for (BeanPostProcessor postProcessor : beanPostProcessors) {
            if (postProcessor instanceof InstantiationAwareBeanPostProcessor) {
                //通过注解的字段最终通过这个方法 被注入值
                ((InstantiationAwareBeanPostProcessor) postProcessor).postProcessPropertyValues(bean, beanDefinition.getBeanName());
            }
        }
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
                        Object value = pv.getValue();
                        pd.getWriteMethod().invoke(bean, valueResolver.resolveValueIfNecessary(value, pd.getPropertyType()));
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
    public Object instantiateBean(BeanDefinition beanDefinition) {
        if (beanDefinition == null) {
            return null;
        }
        //初始化一个构造函数解析器 用来返回初始化后的bean实例
        if (beanDefinition.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(beanDefinition);
        } else {
            String beanName = beanDefinition.getBeanClassName();
            try {
                Class<?> objectBean = getClassLoader().loadClass(beanName);
                return objectBean.newInstance();
            } catch (Exception e) {
                throw new BeanCreateException(beanName, e.getMessage(), e);
            }
        }
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public ClassLoader getClassLoader() {
        return classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader();
    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    /**
     * 根据属性依赖描述符 从ICO中查找对应的bean完成注入
     *
     * @param descriptor
     * @return
     */
    public Object resolveDependency(DependencyDescriptor descriptor) throws ClassNotFoundException {
        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : this.beanDefinitionsMap.values()) {
            //确保类被加载过
            if (!bd.hasBeanClass()) {
                bd.resolveBeanClass(getClassLoader());
            }
            //在IOC里匹配的bean声明 能够被属性字段接收 （符合注入条件）
            if (bd.getBeanClass().isAssignableFrom(typeToMatch)) {
                return this.getBean(bd.getBeanName());
            }
        }
        return null;
    }
}
